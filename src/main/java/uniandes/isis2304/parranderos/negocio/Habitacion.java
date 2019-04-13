package uniandes.isis2304.parranderos.negocio;

public class Habitacion implements VOHabitacion{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/

	private long tipoHabitacion;


	private double cuentaHab;

	private long id;

	private Consumo consumo;
	
	private Reserva reserva;

	/******************************************************************************
	 * CONSTRUCTOR
	 ******************************************************************************/
	
	public Habitacion() {
		tipoHabitacion = 0;
		cuentaHab = 0.0;
		id = 0;
		consumo = new Consumo();
		reserva = new Reserva();
	}
	
	public Habitacion(long tipoHabitacion,  double cuentaHab, long id) {
		super();
		this.tipoHabitacion = tipoHabitacion;
		this.cuentaHab = cuentaHab;
		this.id = id;
		
		consumo = new Consumo();
		reserva = new Reserva();
	}

	/******************************************************************************
	 * METODOS
	 ******************************************************************************/

	

	

	@Override
	public double getCuentaHab() {
		return cuentaHab;
	}

	public void setCuentaHab(double cuentaHab) {
		this.cuentaHab = cuentaHab;
	}



	public long getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(long tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}

	public long getId(){
		return id;
	}

	public void setId( long id ){
		this.id = id;
	}

	public Consumo getConsumo() {
		return consumo;
	}

	public void setConsumo(Consumo consumo) {
		this.consumo = consumo;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	@Override
	public String toString() {
		return "Habitacion [tipoHabitacion=" + tipoHabitacion + ", cuentaHab=" + cuentaHab + ", id=" + id + ", consumo=" + consumo + ", reserva=" + reserva + "]";
	}
	
}