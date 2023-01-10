package es.cipfpbatoi.dao;

import es.cipfpbatoi.modelo.Category;
import es.cipfpbatoi.modelo.Store;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreDAO implements GenericDAO<Store> {


    final String SQLSELECTALL = "SELECT * FROM store";
    final String SQLSELECTPK = "SELECT * FROM store WHERE store_id = ?";
    final String SQLDELETE = "DELETE FROM store WHERE store_id = ?";
    private final PreparedStatement pstSelectPK;
    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstDelete;

    public StoreDAO() throws SQLException{
        Connection con = ConexionBD.getConexionFile();
        pstSelectPK = con.prepareStatement(SQLSELECTPK);
        pstSelectAll = con.prepareStatement(SQLSELECTALL);
        pstDelete = con.prepareStatement(SQLDELETE);
    }

    public void cerrar() throws SQLException {
        pstSelectPK.close();
        pstSelectAll.close();
        pstDelete.close();
    }

    private Store build(int store_id, int manager_staff_id, int address_id){
        return new Store(store_id,manager_staff_id,address_id);
    }

    @Override
    public Store find(int id) throws SQLException {
        try {
            Store s = null;
            pstSelectPK.setInt(1,id);
            ResultSet rs = pstSelectPK.executeQuery();
            if (rs.next()){
                s = build(id,rs.getInt("manager_staff_id"), rs.getInt("address_id"));
            }
            rs.close();
            return s;
        }catch (SQLException s){
            s.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Store> findAll() throws SQLException {
        try {
            List<Store> listaAlmacenes = new ArrayList<>();
            ResultSet rs = pstSelectAll.executeQuery();
            while (rs.next()){
                listaAlmacenes.add(build(rs.getInt("store_id"),rs.getInt("manager_staff_id"), rs.getInt("address_id")));
            }
            rs.close();
            return listaAlmacenes;
        }catch (SQLException s){
            s.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insert(Store store) {
        return false;
    }

    @Override
    public boolean update(Store store) {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        pstDelete.setInt(1, id);
        int borrados = pstDelete.executeUpdate();
        return (borrados == 1);
    }

    @Override
    public boolean delete(Store store) throws SQLException {
        return this.delete(store.getStore_id());
    }
}
