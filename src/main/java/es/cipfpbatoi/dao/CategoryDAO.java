package es.cipfpbatoi.dao;

import es.cipfpbatoi.modelo.Category;
import es.cipfpbatoi.modelo.Film;
import es.cipfpbatoi.modelo.Rating;

import java.io.File;
import java.io.PipedReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements GenericDAO<Category> {

    final String SQLSELECTALL = "SELECT * FROM category";
    final String SQLSELECTPK = "SELECT * FROM category WHERE category_id = ?";
    final String SQLINSERT = "INSERT into category (name) values(?)";
    final String SQLUPDATE = "UPDATE category set name = ? where category_id = ?";
    final String SQLDELETE = "DELETE FROM category WHERE category_id = ?";
    final String SQLADDCATEGORYFILM = "SELECT * from film_category fc WHERE film_id = ? and category_id = ?";
    final String SQLCATEGORYDELETE = "DELETE from film_category WHERE film_id = ? AND category_id = ?";


    private final PreparedStatement pstSelectPK;
    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstDelete;
    private final PreparedStatement pstCategoryDelete;
    private final PreparedStatement pstAddCategoryFilm;


    public CategoryDAO() throws SQLException{
        Connection con = ConexionBD.getConexionFile();
        pstSelectPK = con.prepareStatement(SQLSELECTPK);
        pstSelectAll = con.prepareStatement(SQLSELECTALL);
        pstInsert = con.prepareStatement(SQLINSERT, PreparedStatement.RETURN_GENERATED_KEYS);
        pstUpdate = con.prepareStatement(SQLUPDATE);
        pstDelete = con.prepareStatement(SQLDELETE);
        pstCategoryDelete = con.prepareStatement(SQLCATEGORYDELETE);
        pstAddCategoryFilm = con.prepareStatement(SQLADDCATEGORYFILM);
    }

    public void cerrar() throws SQLException {
        pstSelectPK.close();
        pstSelectAll.close();
        pstInsert.close();
        pstUpdate.close();
        pstDelete.close();
        pstCategoryDelete.close();
        pstAddCategoryFilm.close();
    }


    private Category build(int id,String category) {
        return new Category(id,category);
    }
    @Override
    public Category find(int id) throws SQLException {
        try {
            Category c = null;
            pstSelectPK.setInt(1, id);
            ResultSet rs = pstSelectPK.executeQuery();
            if (rs.next()) {
                c = build(id, rs.getString("name"));
            }
            rs.close();
            return c;
        }catch (SQLException s){
            s.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Category> findAll() {
        try {
            List<Category> listaCategory = new ArrayList<Category>();
            ResultSet rs = pstSelectAll.executeQuery();
            while (rs.next()) {
                listaCategory.add(build(rs.getInt("category_id"), rs.getString("name")));
            }
            rs.close();
            return listaCategory;
        }catch (SQLException s){
            s.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Category category) {
        try {
            pstInsert.setString(1, category.getName());
            int insertados = pstInsert.executeUpdate();
            if (insertados == 1) {
                ResultSet rs = pstInsert.getGeneratedKeys();
                rs.next();
                category.setCategory_id(rs.getInt(1));
                return true;
            }


        }catch (SQLException s){
            s.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Category category) {
        try {
            pstUpdate.setString(1, category.getName());
            pstUpdate.setInt(2,category.getCategory_id());
            int actualizados = pstUpdate.executeUpdate();
            return (actualizados == 1);
        }catch (SQLException s){
            s.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            pstDelete.setInt(1, id);
            int borrados = pstDelete.executeUpdate();
            return (borrados == 1);
        }catch (SQLException s){
            s.printStackTrace();
        }
        return  false;
    }

    @Override
    public boolean delete(Category category){
        return this.delete(category.getCategory_id());
    }

    public boolean deleteCategory(Film f, Category c){
        try {
            pstCategoryDelete.setInt(1,f.getFilm_id());
            pstCategoryDelete.setInt(2,c.getCategory_id());
            int borrados = pstCategoryDelete.executeUpdate();
            return (borrados == 1);
        }catch (SQLException s){
            s.printStackTrace();
        }
        return false;
    }


    public boolean anyadirCategoriaAPelicula(Film f, Category c){
        boolean existeAsiciacion = false;
        try {
            pstAddCategoryFilm.setInt(1,f.getFilm_id());
            pstAddCategoryFilm.setInt(2,c.getCategory_id());
            ResultSet rs = pstAddCategoryFilm.executeQuery();
            while (rs.next()){
                existeAsiciacion = true;
            }
            rs.close();
            return existeAsiciacion;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
