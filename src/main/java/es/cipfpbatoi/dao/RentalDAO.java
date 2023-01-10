package es.cipfpbatoi.dao;

import es.cipfpbatoi.modelo.Category;
import es.cipfpbatoi.modelo.Rental;

import java.io.File;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class RentalDAO implements GenericDAO<Rental> {


    final String SQLSELECTALL = "SELECT * FROM rental";
    final String SQLSELECTPK = "SELECT * FROM rental WHERE rental_id = ?";
    final String SQLDELETE = "DELETE FROM rental WHERE rental_id = ?";
    final String SQLINSERT = "INSERT into rental (rental_date,inventory_id,customer_id,staff_id) values(?,?,?,?)";
    final String SQLUPDATE = "UPDATE rental set return_date = ? WHERE rental_id = ?";
    private final PreparedStatement pstSelectPK;
    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstDelete;

    public RentalDAO() throws SQLException{
        Connection con = ConexionBD.getConexionFile();
        pstSelectPK = con.prepareStatement(SQLSELECTPK);
        pstSelectAll = con.prepareStatement(SQLSELECTALL);
        pstInsert = con.prepareStatement(SQLINSERT, PreparedStatement.RETURN_GENERATED_KEYS);
        pstUpdate = con.prepareStatement(SQLUPDATE);
        pstDelete = con.prepareStatement(SQLDELETE);
    }

    public void cerrar() throws SQLException {
        pstSelectPK.close();
        pstSelectAll.close();
        pstDelete.close();
    }

    @Override
    public Rental find(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Rental> findAll() throws SQLException {
        return null;
    }

    @Override
    public boolean insert(Rental rental) {
        try {
            pstInsert.setDate(1, Date.valueOf(rental.getRental_date().toLocalDate()));
            pstInsert.setInt(2,rental.getInventory().getInventory_id());
            pstInsert.setInt(3,rental.getCustomer_id());
            pstInsert.setInt(4,1);
            int insertados = pstInsert.executeUpdate();
            if (insertados == 1){
                ResultSet rs = pstInsert.getGeneratedKeys();
                rs.next();
                rental.setRental_id(rs.getInt(1));
                return true;
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Rental rental) {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.valueOf(localDateTime.toLocalDate());
            pstUpdate.setDate(1,date);
            pstUpdate.setInt(2,rental.getRental_id());

            rental.setReturn_date(localDateTime);
            int actualizados = pstUpdate.executeUpdate();
            return (actualizados == 1);
        }catch (SQLException s){
            s.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        pstDelete.setInt(1, id);
        int borrados = pstDelete.executeUpdate();
        return (borrados == 1);
    }

    @Override
    public boolean delete(Rental rental) throws SQLException {
        return this.delete(rental.getRental_id());
    }
}
