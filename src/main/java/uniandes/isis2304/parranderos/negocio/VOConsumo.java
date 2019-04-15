package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public interface VOConsumo {

	public long getId();
	
	public Timestamp getFecha();
	
	public long getIdUsuario();
	
	public String getTipoDocumentoUsuario();
	
	public long getIdServicio();
	
	public Habitaciones getHabitacion();
	
}
