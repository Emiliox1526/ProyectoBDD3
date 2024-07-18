package logico;

import java.io.Serializable;
import java.util.Date;

public class Contrato implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private String id;
	private String idCliente;
	private String nombre;
	private Date fechaEntrega;
	private Date fechaInicio;
	
	public Contrato(String id, String idCliente, String nombre, Date fechaEntrega, Date fechaInicio) {
		super();
		this.id = id;
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.fechaEntrega = fechaEntrega;
		this.fechaInicio = fechaInicio;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public float calcularPenalizacion() {
		return 0; //Cambiar cuando tengamos mas claro todas las otras funciones [TEMPORAL]
	}
	

}
