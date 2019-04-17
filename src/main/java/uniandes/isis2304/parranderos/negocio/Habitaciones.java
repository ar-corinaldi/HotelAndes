package uniandes.isis2304.parranderos.negocio;

public class Habitaciones implements VOHabitacion{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/
						


	private long tipo_habitacion;

	private double cuenta_habitacion;
	
	
	private long num_hab;

	/******************************************************************************
	 * CONSTRUCTOR
	 ******************************************************************************/
	
	public Habitaciones() {
		tipo_habitacion = 0;
		cuenta_habitacion = 0.0;
		num_hab = 0;
	}
	
	public Habitaciones( long num_hab,  double cuenta_habitacion, long tipo_habitacion) {
		this.tipo_habitacion = tipo_habitacion;
		this.cuenta_habitacion = cuenta_habitacion;
		this.num_hab = num_hab;
	}
	
	public Habitaciones( String num_hab, String cuenta_habitacion, String tipo_habitacion ){
		this.tipo_habitacion = Long.valueOf(tipo_habitacion);
		this.cuenta_habitacion = Double.valueOf(cuenta_habitacion);
		this.num_hab = Long.valueOf(num_hab);
	}
	/******************************************************************************
	 * METODOS
	 ******************************************************************************/

	
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

	@Override
	public String toString() {
		return "Habitacion [tipo_habitacion=" + tipo_habitacion
				+ ", cuenta_habitacion=" + cuenta_habitacion + ", num_hab=" + num_hab + "]";
	}
}