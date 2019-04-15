package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;
import java.util.List;

public interface VOReserva {
	
	public long getId();
	
	public Timestamp getEntrada();
	
	public Timestamp getSalida();
	
	public int getNumPersonas();
	
	public Usuario getUsuarios();
	
	public Habitacion getHabitacion();
	
	
}
