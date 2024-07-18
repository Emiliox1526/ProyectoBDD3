package logico;

import java.io.Serializable;
import java.sql.Date;

public class Diseñador extends Trabajador implements Serializable{
	private static final long serialVersionUID = 1L;
	private int cantAgnoExp;

	
	public Diseñador(String cedula, String nombre, String apellidos, String direccionParticular, String sexo,
			java.util.Date fecha, String evaluacionAnual, int cantAgnoExp,int cantProyectosFallidos, int cantProyectos) {
		super(cedula, nombre, apellidos, direccionParticular, sexo, fecha, evaluacionAnual, cantProyectos, cantProyectos);
		this.cantAgnoExp = cantAgnoExp;
	}

	public int getCantAgnoExp() {
		return cantAgnoExp;
	}

	public void setCantAgnoExp(int cantAgnoExp) {
		this.cantAgnoExp = cantAgnoExp;
	}

}
