package uniandes.isis2304.parranderos.negocio;

public class Habitacion implements VOHabitacion{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/
						

	
	private long tipo_habitacion;

	private double cuenta_habitacion;
	
	private boolean ocupada;
	
	private long num_hab;

	

	/******************************************************************************
	 * CONSTRUCTOR
	 ******************************************************************************/
	
	public Habitacion() {
		tipo_habitacion = 0;
		cuenta_habitacion = 0.0;
		num_hab = 0;
		setOcupada(false);
	}
	
	public Habitacion( long id, long tipoHabitacion,  double cuentaHab) {
		this.tipo_habitacion = tipoHabitacion;
		this.cuenta_habitacion = cuentaHab;
		this.num_hab = id;
		setOcupada(false);
	}

	/******************************************************************************
	 * METODOS
	 ******************************************************************************/

	public boolean isOcupada() {
		return ocupada;
	}

	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}

	public long getTipo_habitacion() {
		return tipo_habitacion;
	}

	public void setTipo_habitacion(long tipo_habitacion) {
		this.tipo_habitacion = tipo_habitacion;
	}

	public double getCuenta_habitacion() {
		return cuenta_habitacion;
	}

	public void setCuenta_habitacion(double cuenta_habitacion) {
		this.cuenta_habitacion = cuenta_habitacion;
	}

	public long getNum_hab() {
		return num_hab;
	}

	public void setNum_hab(long num_hab) {
		this.num_hab = num_hab;
	}

}