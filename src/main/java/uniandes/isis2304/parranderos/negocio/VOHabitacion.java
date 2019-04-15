package uniandes.isis2304.parranderos.negocio;

public interface VOHabitacion {
	
	
	public double getCuentaHab();
	
		
	public long getTipoHabitacion();
	
	public int getNumHab();
	
	public boolean isOcupada();
	
	public Consumo getConsumo();
	
	public Reserva getReserva();
}