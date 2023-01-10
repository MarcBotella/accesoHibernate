package es.cipfpbatoi.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<T> {
	T find(int id) throws SQLException;
    
    List<T> findAll() throws SQLException;
         
    boolean insert(T t) throws SQLException;
     
    boolean update(T t) throws SQLException;
     
    boolean delete(int id) throws SQLException;
    boolean delete(T t) throws SQLException;
}
