package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class Usuarios implements VOUsuario{
	
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
	
	private long tipoUsuario;
	
	private List<Consumo> consumos;

	/******************************************************************************
	 * CONSTRUCTOR
	 ******************************************************************************/

	public Usuarios() {
		tipoDoc = "";
		numeroDoc = 0;
		nombre = "";
		apellido="";
		tipoUsuario = 0;
		setConsumos(new ArrayList<>());
	}
	
	public Usuarios(String tipoDoc, Long numeroDoc, String nombre, String apellido, long tipoUsuario) {
		super();
		this.tipoDoc = tipoDoc;
		this.numeroDoc = numeroDoc;
		this.nombre = nombre;
		this.apellido = apellido;
		this.tipoUsuario = tipoUsuario;
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

	@Override
	public String toString() {
		return "Usuario [tipoDoc=" + tipoDoc + ", numeroDoc=" + numeroDoc
				+ ", nombre=" + nombre + ", apellido=" + apellido
				+ ", consumos=" + consumos + "]";
	}

	public List<Consumo> getConsumos() {
		return consumos;
	}

	public void setConsumos(List<Consumo> consumos) {
		this.consumos = consumos;
	}

	public Long getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(long tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
	
	
}