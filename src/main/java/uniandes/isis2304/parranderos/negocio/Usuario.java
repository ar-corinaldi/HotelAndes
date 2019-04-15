package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class Usuario implements VOUsuario{
	
	/******************************************************************************
	 * CONSTANTES
	 ******************************************************************************/
	
	public final static String CEDULA = "cedula";
	
	public final static String PASAPORTE = "pasaporte";

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/

	/**
	 * (Cedula, Pasaporte)
	 */
	private String tipoDoc;

	private long numeroDoc;

	private String nombre;

	private String apellido;
	
	private String correo;
	
	private Reserva reserva;
	
	private List<Consumo> consumos;

	/******************************************************************************
	 * CONSTRUCTOR
	 ******************************************************************************/

	public Usuario() {
		tipoDoc = "";
		numeroDoc = 0;
		nombre = "";
		apellido="";
		setCorreo("");
		setReserva(new Reserva());
		setConsumos(new ArrayList<>());
	}
	
	public Usuario(String tipoDoc, Long numeroDoc, String nombre, String apellido, String correo) {
		super();
		this.tipoDoc = tipoDoc;
		this.numeroDoc = numeroDoc;
		this.nombre = nombre;
		this.apellido = apellido;
		this.setCorreo(correo);
		setReserva(new Reserva());
		setConsumos(new ArrayList<>());
	}

	/******************************************************************************
	 * METODOS
	 ******************************************************************************/

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public Long getNumeroDoc() {
		return numeroDoc;
	}

	public void setNumeroDoc(Long numeroDoc) {
		this.numeroDoc = numeroDoc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	@Override
	public String toString() {
		return "Usuario [tipoDoc=" + tipoDoc + ", numeroDoc=" + numeroDoc
				+ ", nombre=" + nombre + ", apellido=" + apellido + ", correo="
				+ correo + ", reserva=" + reserva + "]";
	}

	public List<Consumo> getConsumos() {
		return consumos;
	}

	public void setConsumos(List<Consumo> consumos) {
		this.consumos = consumos;
	}
	
	
	
}
