package uniandes.isis2304.parranderos.negocio;

public class Habitacion implements VOHabitacion{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/

	private long tipoHabitacion;

	private double cuentaHab;

	private int numHab;

	private Consumo consumo;
	
	private Reserva reserva;
	
	private boolean ocupada;

	/******************************************************************************
	 * CONSTRUCTOR
	 ******************************************************************************/
	
	public Habitacion() {
		tipoHabitacion = 0;
		cuentaHab = 0.0;
		numHab = 0;
		consumo = new Consumo();
		reserva = new Reserva();
		setOcupada(false);
	}
	
	public Habitacion(long tipoHabitacion,  double cuentaHab, int numHab, boolean ocupada ) {
		super();
		this.tipoHabitacion = tipoHabitacion;
		this.cuentaHab = cuentaHab;
		this.numHab = numHab;
		this.setOcupada(ocupada);
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

	public int getNumHab(){
		return numHab;
	}

	public void setNumHab( int numHab ){
		this.numHab = numHab;
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
		return "Habitacion [tipoHabitacion=" + tipoHabitacion + ", cuentaHab=" + cuentaHab + ", numHab=" + numHab + ", consumo=" + consumo + ", reserva=" + reserva + "]";
	}

	public boolean isOcupada() {
		return ocupada;
	}

	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}
	
}