package es.cipfpbatoi.dao;

import es.cipfpbatoi.modelo.Category;
import es.cipfpbatoi.modelo.Film;
import es.cipfpbatoi.modelo.Inventory;
import es.cipfpbatoi.modelo.Store;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InventoryDAO implements GenericDAO<Inventory> {

    final String SQLSELECTALL = "SELECT * FROM inventory";
    final String SQLSELECTPK = "SELECT * FROM inventory WHERE inventory_id = ?";
    final String SQLDELETE = "DELETE FROM inventory WHERE inventory_id = ?";
    final String SQLINSERT = "INSERT INTO inventory (film_id, store_id) VALUES (?,?)";
    final String SQLINSERTPELISTORE = "INSERT INTO inventory (film_id,store_id) values(?,?)";


    private final PreparedStatement pstSelectPK;
    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstInsert;
//    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstDelete;
    private final PreparedStatement pstInsertPeliStore;

    public InventoryDAO() throws SQLException {
        Connection con = ConexionBD.getConexionFile();
        pstSelectPK = con.prepareStatement(SQLSELECTPK);
        pstSelectAll = con.prepareStatement(SQLSELECTALL);
        pstInsert = con.prepareStatement(SQLINSERT, PreparedStatement.RETURN_GENERATED_KEYS);
//        pstUpdate = con.prepareStatement(SQLUPDATE);
        pstDelete = con.prepareStatement(SQLDELETE);
        pstInsertPeliStore = con.prepareStatement(SQLINSERTPELISTORE);
    }

    public void cerrar() throws SQLException {
        pstSelectPK.close();
        pstSelectAll.close();
        pstDelete.close();
        pstInsertPeliStore.close();
    }


    @Override
    public Inventory find(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Inventory> findAll() throws SQLException {
        return null;
    }

    @Override
    public boolean insert(Inventory inventory) {
        return false;
    }

    public Inventory insert(Film film, Store store) {
        int insertados = 0;
        ResultSet rs;
        Inventory inventory = new Inventory(film, store);
        try {
            pstInsert.setInt(1, film.getFilm_id());
            pstInsert.setInt(2, store.getStore_id());

            insertados = pstInsert.executeUpdate();
            if (insertados == 1) {
                rs = pstInsert.getGeneratedKeys();
                rs.next();
                inventory.setInventory_id(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return inventory;
    }

    @Override
    public boolean update(Inventory inventory) {
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        pstDelete.setInt(1, id);
        int borrados = pstDelete.executeUpdate();
        return (borrados == 1);
    }

    @Override
    public boolean delete(Inventory inventory) throws SQLException {
        return this.delete(inventory.getInventory_id());
    }

    public boolean insertarPeliculaInventario(Film f, Store almacen) {
        try {
            pstInsertPeliStore.setInt(1, f.getFilm_id());
            pstInsertPeliStore.setInt(2, almacen.getStore_id());
            int insertados = pstInsertPeliStore.executeUpdate();
            if (insertados == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return false;
    }
}
