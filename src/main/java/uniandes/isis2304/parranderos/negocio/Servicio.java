package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Servicio implements VOServicio{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/
	
	
	private long id;

	private boolean cargadoHab;

	private String nombre;

	private String descripcion;
	
	private int capacidad;

	private double costo;
	
	private long tipoServicio;
	
	/******************************************************************************
	 * CONSTRUCTOR
	 ******************************************************************************/

	public Servicio() {
		id = 0;
		cargadoHab = false;
		nombre = "";
		descripcion = "";
		costo = 0.0;
		capacidad = 0;
		setTipoServicio(0);
	}
	
	public Servicio(long id, boolean cargadoHab, int capacidad,
			 String nombre,
			String descripcion, double costo, long tipoServicio) {
		super();
		this.id = id;
		this.cargadoHab = cargadoHab;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.costo = costo;
		this.capacidad = capacidad;
		this.tipoServicio = tipoServicio;
	}

	/******************************************************************************
	 * METODOS
	 ******************************************************************************/


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	public boolean isCargadoHab() {
		return cargadoHab;
	}

	public void setCargadoHab(boolean cargadoHab) {
		this.cargadoHab = cargadoHab;
	}




	@Override
	public String toString() {
		return "Servicio [id=" + id 
				+ ", cargadoHab=" 
				+ ", nombre=" + nombre
				+ ", descripcion=" + descripcion + ", costo=" + costo + ", tipoServicio=" + tipoServicio+"]";
	}

	public long getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(long tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	
}