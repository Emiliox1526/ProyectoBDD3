package logico;

import java.io.Serializable;

public class Cliente implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private String apellido;
	private String direccion;
	private int cantProyectos;
	private boolean maxProyectos;
	
	public Cliente(String id, String nombre,String apellido, String direccion, int cantProyectos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.setApellido(apellido);
		this.direccion = direccion;
		this.cantProyectos = cantProyectos;
		this.maxProyectos = false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getCantProyectos() {
		return cantProyectos;
	}

	public void setCantProyectos(int cantProyectos) {
		this.cantProyectos = cantProyectos;
	}

	public boolean isMaxProyectos() {
		return maxProyectos;
	}

	public void setMaxProyectos(boolean maxProyectos) {
		this.maxProyectos = maxProyectos;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	
	

}
