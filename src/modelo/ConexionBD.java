package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBD {

    private static ConexionBD instancia;
    private Connection connection = null;

    private final String URL      = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String USUARIO  = "EjemploCrud";
    private final String PASSWORD = "EjemploCrud";

    private ConexionBD() {
        conectar();
    }

    private void conectar() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            connection = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            if (connection != null) {
                System.out.println("Conexion exitosa con Oracle 10g.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: ojdbc14.jar no esta en Libraries.");
        } catch (SQLException ex) {
            System.err.println("ERROR SQL: " + ex.getMessage());
        }
    }

    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                conectar();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}