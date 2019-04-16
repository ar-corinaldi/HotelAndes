package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;
import java.util.List;

public interface VOReserva {
	
	public long getId();
	
	public Timestamp getEntrada();
	
	public Timestamp getSalida();
	
	public int getNum_personas();
	
	public long getId_usuario();

	public String getTipo_documento_usuario();
	
	public long getId_habitacion();
	
	
}
