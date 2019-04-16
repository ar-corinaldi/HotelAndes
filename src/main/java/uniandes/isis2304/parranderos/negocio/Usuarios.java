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
	private String tipo_documento;

	private long num_identidad;

	private String nombre;

	private String apellido;
	
	private long tipo_usuario;
	
	private long id_convencion;
	
	private List<Consumo> consumos;

	/******************************************************************************
	 * CONSTRUCTOR
	 ******************************************************************************/

	public Usuarios() {
		tipo_documento = "";
		num_identidad = 0;
		nombre = "";
		apellido="";
		tipo_usuario = 0;
		id_convencion = 0;
		setConsumos(new ArrayList<>());
	}
	
	public Usuarios(Long num_identidad, String tipo_documento, String nombre, String apellido, long tipo_usuario, long id_convencion) {
		super();
		this.tipo_documento = tipo_documento;
		this.num_identidad = num_identidad;
		this.nombre = nombre;
		this.apellido = apellido;
		this.tipo_usuario = tipo_usuario;
		this.setId_convencion(id_convencion);
		setConsumos(new ArrayList<>());
	}
	
	public Usuarios(String num_identidad, String tipo_documento, String nombre, String apellido, String tipo_usuario, String id_convencion){
		this.num_identidad = Long.valueOf(num_identidad);
		this.tipo_documento = tipo_documento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.tipo_usuario = Long.valueOf(tipo_usuario);
		this.id_convencion = Long.valueOf(id_convencion);
	}

	/******************************************************************************
	 * METODOS
	 ******************************************************************************/

	public String getTipo_documento() {
		return tipo_documento;
	}

	public void setTipo_documento(String tipo_documento) {
		this.tipo_documento = tipo_documento;
	}

	public Long getNum_identidad() {
		return num_identidad;
	}

	public void setNum_identidad(Long num_identidad) {
		this.num_identidad = num_identidad;
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
		return "Usuarios [tipo_documento=" + tipo_documento
				+ ", num_identidad=" + num_identidad + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", tipo_usuario=" + tipo_usuario
				+ ", id_convencion=" + id_convencion + ", consumos=" + consumos
				+ "]";
	}

	public List<Consumo> getConsumos() {
		return consumos;
	}

	public void setConsumos(List<Consumo> consumos) {
		this.consumos = consumos;
	}

	public Long getTipo_usuario() {
		return tipo_usuario;
	}

	public void setTipo_usuario(long tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}

	public long getId_convencion() {
		return id_convencion;
	}

	public void setId_convencion(long id_convencion) {
		this.id_convencion = id_convencion;
	}
	
	
	
}
