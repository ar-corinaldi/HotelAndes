package uniandes.isis2304.parranderos.negocio;

import java.math.BigDecimal;

public class TipoHabitacion {

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/
	private long id;

	private String nombre;
	
	private double costo_noche;
	
	private int capacidad;



	/******************************************************************************
	 * CONSTRUCTORES
	 ******************************************************************************/
	public TipoHabitacion() {
		id = 0;
		nombre = "";
		setCosto(0);
	}

	public TipoHabitacion(long id, String nombre, double costo, int capacidad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.costo_noche = costo;
		this.capacidad = capacidad;
	}
	
	/******************************************************************************
	 * METODOS
	 ******************************************************************************/

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "TipoHabitacion [id=" + id + ", nombre=" + nombre + ", costo="+ costo_noche+ ", capacidad="+ capacidad +"]";
	}

	public double getCosto() {
		return costo_noche;
	}

	public void setCosto(double costo) {
		this.costo_noche = costo;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

}