package uniandes.isis2304.parranderos.negocio;

public class TipoHabitacion {

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/

	private long id;

	private String nombre;
	
	private double costo;
	
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
		this.setCosto(costo);
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
		return "TipoHabitacion [id=" + id + ", nombre=" + nombre + ", costo="+ costo+ ", capacidad="+ capacidad +"]";
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

}