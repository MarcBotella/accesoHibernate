package es.cipfpbatoi.dao;

import es.cipfpbatoi.modelo.Category;
import es.cipfpbatoi.modelo.Film;
import es.cipfpbatoi.modelo.Rating;
import es.cipfpbatoi.modelo.Store;

import java.io.File;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO implements GenericDAO<Film> {

    final String SQLSELECTALL = "SELECT * from film";
    final String SQLSELECTPK = "SELECT * FROM film WHERE film_id = ?";
    final String SQLINSERT = "INSERT into film (title,language_id) values (?,?)";
    final String SQLUPDATE = "UPDATE film set title = ?, language_id = ?, rental_duration = ?, rental_rate = ?, length = ?, replacement_cost = ?, rating  = ?, special_features = ? WHERE film_id = ?";
    final String SQLDELETE = "DELETE FROM film WHERE film_id = ?";
    final String SQLFINDFILMCATEGORY = "SELECT f.* from film_category fc inner join film f  on fc.film_id = f.film_id WHERE fc.category_id  = ?";
    final String SQLFILMSTOCK = "{CALL film_in_stock(?,?,?)}";

    private final PreparedStatement pstSelectPK;
    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstDelete;
    private final PreparedStatement pstFindFilmCategory;
    private final CallableStatement pstFilmStock;

    public FilmDAO() throws SQLException{
        Connection con = ConexionBD.getConexionFile();
        pstSelectPK = con.prepareStatement(SQLSELECTPK);
        pstSelectAll = con.prepareStatement(SQLSELECTALL);
        pstInsert = con.prepareStatement(SQLINSERT, PreparedStatement.RETURN_GENERATED_KEYS);
        pstUpdate = con.prepareStatement(SQLUPDATE);
        pstDelete = con.prepareStatement(SQLDELETE);
        pstFindFilmCategory = con.prepareStatement(SQLFINDFILMCATEGORY);
        pstFilmStock = con.prepareCall(SQLFILMSTOCK);
    }

    public void cerrar() throws SQLException {
        pstSelectPK.close();
        pstSelectAll.close();
        pstInsert.close();
        pstUpdate.close();
        pstDelete.close();
        pstFindFilmCategory.close();
        pstFilmStock.close();
    }


    private Film build(int film_id, String title, int language_id, byte rental_duration, float rental_rate, short length,
                       float replacement_cost, Rating rating, String special_features) {

        return new Film(film_id,title,language_id,rental_duration,rental_rate,length,replacement_cost,rating,special_features);
    }
    @Override
    public Film find(int film_id) throws SQLException{
        try {
            Film f = null;
            pstSelectPK.setInt(1,film_id);
            ResultSet rs = pstSelectPK.executeQuery();
            if (rs.next()){
                f = build(film_id,rs.getString("title"),rs.getInt("language_id"), rs.getByte("rental_duration"),rs.getFloat("rental_rate"),rs.getShort("length"),
                        rs.getFloat("replacement_cost"),getRating(rs.getString("rating")),rs.getString("special_features"));
            }
            rs.close();
            return f;
        }catch (SQLException s){
            s.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Film> findAll(){
        try {
            List<Film> listaPeliculas = new ArrayList<Film>();
            ResultSet rs = pstSelectAll.executeQuery();
            while (rs.next()) {
                listaPeliculas.add(build(rs.getInt("film_id"),rs.getString("title"),rs.getInt("language_id"), rs.getByte("rental_duration"),rs.getFloat("rental_rate"),rs.getShort("length"),
                        rs.getFloat("replacement_cost"),Rating.valueOf(rs.getString("rating")),rs.getString("special_features")));
            }
            rs.close();
            return listaPeliculas;
        }catch (SQLException s){
            s.printStackTrace();
        }
        return null;
    }
        @Override
    	public boolean insert(Film filmInsertar){
        try {
            pstInsert.setString(1, filmInsertar.getTitle());
            pstInsert.setInt(2, filmInsertar.getLanguage_id());
            int insertados = pstInsert.executeUpdate();
            if (insertados == 1) {
                ResultSet rs = pstInsert.getGeneratedKeys();
                rs.next();
                filmInsertar.setFilm_id(rs.getInt(1));
                return true;
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
        return false;
	}


    @Override
    public boolean update(Film film){
        try {
            pstUpdate.setString(1, film.getTitle());
            pstUpdate.setInt(2, film.getLanguage_id());
            pstUpdate.setInt(3, film.getRental_duration());
            pstUpdate.setFloat(4, film.getRental_rate());
            pstUpdate.setShort(5, film.getLength());
            pstUpdate.setFloat(6, film.getReplacement_cost());
            pstUpdate.setString(7, film.getRating().toString());
            pstUpdate.setString(8, film.getSpecial_features());
            pstUpdate.setInt(9,film.getFilm_id());
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
        return false;
    }

    @Override
    public boolean delete(Film filmEliminar) {
        return this.delete(filmEliminar.getFilm_id());
    }

    public List<Film> findFilmByCategory(Category category){
        try {
            Film f = null;
            List<Film> listPelisPorCategoria = new ArrayList<>();
            pstFindFilmCategory.setInt(1,category.getCategory_id());
            ResultSet rs = pstFindFilmCategory.executeQuery();
            while (rs.next()){
                f = build(
                                rs.getInt("film_id"),
                                rs.getString("title"),
                                rs.getInt("language_id"),
                                rs.getByte("rental_duration"),
                                rs.getFloat("rental_rate"),
                                rs.getShort("length"),
                                rs.getFloat("replacement_cost"),
                                getRating(rs.getString("rating")),
                                rs.getString("special_features")
                        );
                f.addCategory(category);
                listPelisPorCategoria.add(f);

            }
            rs.close();
            return listPelisPorCategoria;
        }catch (SQLException s){
            s.printStackTrace();
        }
        return null;
    }

    public Rating getRating(String rating){
        switch (rating){
            case "G":
                return Rating.G;
            case "PG":
                return Rating.PG;
            case "PG13":
                return Rating.PG13;
            case "R":
                return Rating.R;
            case "NC17":
                return Rating.NC17;
            default:
                return null;
        }
    }

    public boolean estaDisponible(Film f, Store almacen){
        int outProcedure =0;
        try {
            if (find(f.getFilm_id()) != null){
                pstFilmStock.setInt(1,f.getFilm_id());
                pstFilmStock.setInt(2,almacen.getStore_id());
                pstFilmStock.registerOutParameter(3, Types.INTEGER);
                pstFilmStock.execute();
                outProcedure = pstFilmStock.getInt(3);
                if (outProcedure >=1){
                    return true;
                }else {
                    return false;
                }
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
        return false;
    }
}
