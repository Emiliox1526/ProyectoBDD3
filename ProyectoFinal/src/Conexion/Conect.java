package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conect {
	
	Connection con;
	public Conect(){
	try{
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Proyecto_Final", "admin", "admin");
		}	catch(Exception e) {
			System.err.println("Error" +e);
		}
	}
	
	public static void main(String[] args) {
		Conect cn = new Conect();
	}
}
