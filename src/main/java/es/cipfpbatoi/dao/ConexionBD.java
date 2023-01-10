package es.cipfpbatoi.dao;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

// Patrón singleton
public class ConexionBD {

    private static final String JDBC_URL = "jdbc:mariadb://192.168.56.102/empresa_ad";
//    private static final String JDBC_URL = "jdbc:postgresql://192.168.56.102:5432/batoi?currentSchema=empresa_ad";
    private static Connection con = null;    

    public static Connection getConexion() throws SQLException {
        if (con == null) {
            Properties pc = new Properties();
            pc.put("user", "batoi");
            pc.put("password", "1234");
            con = DriverManager.getConnection(JDBC_URL, pc);
        }
        return con;
    }

    public static void cerrar() throws SQLException {
        if (con != null) {
            con.close();
            con = null;
        }
    }

    public static Connection getConexionFile() throws SQLException{
        BufferedReader br = null;
        String linea = "";
        String jdbcUrl = "jdbc:mariadb://";
        File file1 = new File("./src/main/resources/datos_conexion.conf");

        if (con == null) {
            Properties pc = new Properties();
            try {
                br = new BufferedReader(new FileReader(file1));
                while ((linea = br.readLine()) != null) {
                    String[] partes = linea.split("=");

                    String enunciado = partes[0].trim();
                    String resultado = partes[1].trim();

                    if (enunciado.equals("user")) {
                        pc.put(enunciado,resultado);
                    }else if (enunciado.equals("password")){
                        pc.put(enunciado,resultado);
                    }else if (enunciado.equals("ip")){
                        jdbcUrl+=resultado+'/';
                    } else if (enunciado.equals("bd")) {
                        jdbcUrl+=resultado;
                    }else{
                        break;
                    }
                }

                con = DriverManager.getConnection(jdbcUrl,pc);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return con;
    }

}
