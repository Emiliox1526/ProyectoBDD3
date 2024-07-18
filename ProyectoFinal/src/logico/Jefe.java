package logico;

import java.io.Serializable;
import java.sql.Date;

public class Jefe extends Trabajador implements Serializable{
	private static final long serialVersionUID = 1L;
	private int cantTrabajadores;

	public Jefe(String cedula, String nombre, String apellidos, String direccionParticular, String sexo,
			java.util.Date date, String evaluacionAnual, int cantTrabajadores,int cantProyectosFallidos, int cantProyectos) {
		super(cedula, nombre, apellidos, direccionParticular, sexo, date, evaluacionAnual, cantProyectos, cantProyectos);
		this.cantTrabajadores = cantTrabajadores;
	}

	public int getCantTrabajadores() {
		return cantTrabajadores;
	}

	public void setCantTrabajadores(int cantTrabajadores) {
		this.cantTrabajadores = cantTrabajadores;
	}
	

}
