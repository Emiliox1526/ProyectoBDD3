package logico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Proyecto implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private Cliente cliente;
	private ArrayList<Trabajador>participantes;
	private Contrato contrato;
	private Date fechaInicio;
	private Date fechaEntregaInicial;
	private Date fechaEntregaFinal = fechaEntregaInicial;
	private Date fechaProrroga = null;
	private boolean isPenalizado = false;
	
	public Proyecto(String id, Cliente cliente, ArrayList<Trabajador> participantes, Contrato contrato,
			Date fechaInicio, Date fechaEntregaInicial, Date fechaEntregaFinal, Date fechaProrroga,
			boolean isPenalizado) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.participantes = participantes;
		this.contrato = contrato;
		this.fechaInicio = fechaInicio;
		this.fechaEntregaInicial = fechaEntregaInicial;
		this.fechaEntregaFinal = fechaEntregaFinal;
		this.fechaProrroga = fechaProrroga;
		this.isPenalizado = isPenalizado;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public ArrayList<Trabajador> getParticipantes() {
		return participantes;
	}
	public void setParticipantes(ArrayList<Trabajador> participantes) {
		this.participantes = participantes;
	}
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaEntregaInicial() {
		return fechaEntregaInicial;
	}
	public void setFechaEntregaInicial(Date fechaEntregaInicial) {
		this.fechaEntregaInicial = fechaEntregaInicial;
	}
	public Date getFechaEntregaFinal() {
		return fechaEntregaFinal;
	}
	public void setFechaEntregaFinal(Date fechaEntregaFinal) {
		this.fechaEntregaFinal = fechaEntregaFinal;
	}
	public Date getFechaProrroga() {
		return fechaProrroga;
	}
	public void setFechaProrroga(Date fechaProrroga) {
		this.fechaProrroga = fechaProrroga;
	}
	public boolean isPenalizado() {
		return isPenalizado;
	}
	public void setPenalizado(boolean isPenalizado) {
		this.isPenalizado = isPenalizado;
	}
	

	
	
}
