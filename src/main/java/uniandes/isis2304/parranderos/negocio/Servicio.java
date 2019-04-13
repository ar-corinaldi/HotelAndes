package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Servicio implements VOServicio{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/

	private long id;

	private List<Object []> productos;

	private boolean cargadoHab;

	private Timestamp horaReserva;

	private boolean reservado;

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
		productos = new ArrayList<Object[]>();
		cargadoHab = false;
		horaReserva = new Timestamp(0);
		reservado = false;
		nombre = "";
		descripcion = "";
		costo = 0.0;
		capacidad = 0;
		setTipoServicio(0);
	}
	
	public Servicio(long id, boolean cargadoHab, int capacidad,
			Timestamp horaReserva, boolean reservado, String nombre,
			String descripcion, double costo, long tipoServicio) {
		super();
		this.id = id;
		productos = new ArrayList<Object[]>();
		this.cargadoHab = cargadoHab;
		this.horaReserva = horaReserva;
		this.reservado = reservado;
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

	public List<Object []> getProductos() {
		return productos;
	}

	public void setProductos(List<Object []> productos) {
		this.productos = productos;
	}

	public boolean isCargadoHab() {
		return cargadoHab;
	}

	public void setCargadoHab(boolean cargadoHab) {
		this.cargadoHab = cargadoHab;
	}

	public Timestamp getHoraReserva() {
		return horaReserva;
	}

	public void setHoraReserva(Timestamp horaReserva) {
		this.horaReserva = horaReserva;
	}

	public boolean isReservado() {
		return reservado;
	}

	public void setReservado(boolean reservado) {
		this.reservado = reservado;
	}


	@Override
	public String toString() {
		return "Servicio [id=" + id + ", productos=" + productos
				+ ", cargadoHab=" + cargadoHab + ", horaReserva=" + horaReserva
				+ ", reservado=" + reservado + ", nombre=" + nombre
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