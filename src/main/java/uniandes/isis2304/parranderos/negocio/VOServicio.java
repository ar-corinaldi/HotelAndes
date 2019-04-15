package uniandes.isis2304.parranderos.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface VOServicio {
	
	public String getNombre();
	
	public String getDescripcion();
	
	public double getCosto();
	
	public long getId();
	
	public boolean isCargadoHab();
		
	@Override
	public String toString();
	
}
