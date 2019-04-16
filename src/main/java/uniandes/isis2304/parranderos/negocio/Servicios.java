package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Servicios implements VOServicio{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/
	public final static int SI_CAR_HAB = 1;
	public final static int NO_CAR_HAB = 0;
	
	public final static int SI_RESERVADO = 1;
	public final static int NO_RESERVADO = 0;

	
	
	
	private long id;

	private int cargado_habitacion;

	private String nombre;

	private String descripcion;
	
	private int capacidad;

	private double costo;
	
	private int reservado;
	
	private long id_tipo_servicios;
	
	public int getCargado_habitacion() {
		return cargado_habitacion;
	}

	public void setCargado_habitacion(int cargado_habitacion) {
		this.cargado_habitacion = cargado_habitacion;
	}

	public int getReservado() {
		return reservado;
	}

	public void setReservado(int reservado) {
		this.reservado = reservado;
	}

	public long getTipo_servicios() {
		return id_tipo_servicios;
	}

	public void setTipo_servicios(long tipo_servicios) {
		this.id_tipo_servicios = tipo_servicios;
	}

	/******************************************************************************
	 * CONSTRUCTOR
	 ******************************************************************************/

	public Servicios() {
		id = 0;
		cargado_habitacion= 0;
		nombre = "";
		descripcion = "";
		costo = 0.0;
		capacidad = 0;
		reservado = 0;
		setTipo_servicios(0);
	}
	
	public Servicios(long id, String nombre,
			String descripcion,   double costo, int cargado_habitacion, int capacidad,
			long tipoServicio) {
		super();
		this.id = id;
		this.cargado_habitacion = cargado_habitacion;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.costo = costo;
		this.capacidad = capacidad;
		this.id_tipo_servicios = id_tipo_servicios;
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

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	@Override
	public String toString() {
		return "Servicios [id=" + id + ", cargado_habitacion="
				+ cargado_habitacion + ", nombre=" + nombre + ", descripcion="
				+ descripcion + ", capacidad=" + capacidad + ", costo=" + costo
				+ ", reservado=" + reservado + ", tipo_servicios="
				+ id_tipo_servicios + "]";
	}
	
}