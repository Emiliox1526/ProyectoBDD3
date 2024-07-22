package logico;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import Conexion.SQL;

public class Main2 {
    public static void main(String[] args) {
        Connection connection = SQL.getConnection();
        if (connection != null) {
            try {
                // Crear una declaración
                Statement stmt = connection.createStatement();
                // Ejecutar una consulta
                ResultSet rs = stmt.executeQuery("SELECT * FROM Estudiante");

                // Procesar los resultados
                while (rs.next()) {
                    System.out.println("Columna 1: " + rs.getString(1));
                    System.out.println("Columna 2: " + rs.getString(2));
                    // y así sucesivamente...
                }

                // Cerrar recursos
                rs.close();
                stmt.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
