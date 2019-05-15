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
	
	private long id_servicio;
	
	private Servicios servicio;
	
	/******************************************************************************
	 * CONSTRUCTORES
	 ******************************************************************************/
	
	public Producto() {
		id = 0;
		nombre = "";
		costo = 0.0;
		descripcion = "";
		servicio = null;
	}
	
	public Producto(long id, String nombre,  String descripcion, double costo, long idServ) {
		this.id = id;
		this.nombre = nombre;
		this.costo = costo;
		this.descripcion = descripcion;
		setId_servicio(idServ);
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
	
	public Servicios getServicio() {
		return servicio;
	}

	public void setServicio(Servicios servicio) {
		this.servicio = servicio;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", costo=" + costo
				+ ", descripcion=" + descripcion + ", servicios=" + servicio
				+ "]";
	}

	public long getId_servicio() {
		return id_servicio;
	}

	public void setId_servicio(long id_servicio) {
		this.id_servicio = id_servicio;
	}

}
