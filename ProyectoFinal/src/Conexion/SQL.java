package Conexion;
//aaaaaaaaa
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {
    
	private static final String DB_URL = "jdbc:sqlserver://LAPTOP-048K0K4K:1433;databaseName=Academico;user=sa;password=josvier23;";


    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Conectado*");
        } catch (SQLException e) {
            System.out.println("Error entrando en la base de datos");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error cargando el driver de la base de datos");
            e.printStackTrace();
        }
        return connection;
    }
}
 