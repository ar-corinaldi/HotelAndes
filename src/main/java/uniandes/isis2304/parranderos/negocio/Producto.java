package uniandes.isis2304.parranderos.negocio;

import java.util.ArrayList;
import java.util.List;

public class Producto implements VOProducto{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/
	
	private long id;
	
	private String nombre;
	
	private double costo;
	
	private String descripcion;
	
	private List<Servicio> servicios;
	
	/******************************************************************************
	 * CONSTRUCTORES
	 ******************************************************************************/
	
	public Producto() {
		id = 0;
		nombre = "";
		costo = 0.0;
		descripcion = "";
		servicios = new ArrayList<>();
	}
	
	public Producto(long id, String nombre, double costo, String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.costo = costo;
		this.descripcion = descripcion;
		servicios = new ArrayList<>();
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

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public List<Servicio> getServicio() {
		return servicios;
	}

	public void setServicio(List<Servicio> servicios) {
		this.servicios = servicios;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", costo=" + costo
				+ ", descripcion=" + descripcion + ", servicios=" + servicios
				+ "]";
	}

}
