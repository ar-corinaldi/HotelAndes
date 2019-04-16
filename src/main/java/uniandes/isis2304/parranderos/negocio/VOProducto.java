package uniandes.isis2304.parranderos.negocio;

import java.util.List;

public interface VOProducto {
	
	public long getId();
	
	public String getNombre();
	
	public double getCosto();
	
	public String getDescripcion();
	
	public Servicios getServicio();
}
