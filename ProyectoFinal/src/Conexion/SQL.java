package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {
    
    private static final String DB_URL = "jdbc:sqlserver://STLAB-DB01:1433;databaseName=Asignacion Final - EJGR0001;integratedSecurity=true;";

    public static Connection getConnection() throws ClassNotFoundException {
        Connection connection = null;
        try {
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Conectado*");
        } catch (SQLException e) {
            System.out.println("Error entrando en la base de datos");
            e.printStackTrace();
        }
        return connection;
    }
}
