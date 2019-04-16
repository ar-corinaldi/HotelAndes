package uniandes.isis2304.parranderos.negocio;

public class Habitaciones implements VOHabitacion{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/
						
	public final static int SE_OCUPA = 1;
	
	public final static int SE_DESOCUPA = 0;

	private long tipo_habitacion;

	private double cuenta_habitacion;
	
	private int ocupada;
	
	private long num_hab;

	/******************************************************************************
	 * CONSTRUCTOR
	 ******************************************************************************/
	
	public Habitaciones() {
		tipo_habitacion = 0;
		cuenta_habitacion = 0.0;
		num_hab = 0;
		ocupada = 0;
	}
	
	public Habitaciones( long num_hab, int ocupada, double cuenta_habitacion, long tipo_habitacion) {
		this.tipo_habitacion = tipo_habitacion;
		this.cuenta_habitacion = cuenta_habitacion;
		this.num_hab = num_hab;
		this.ocupada = ocupada;
	}
	
	public Habitaciones( String num_hab, String ocupada, String cuenta_habitacion, String tipo_habitacion ){
		this.tipo_habitacion = Long.valueOf(tipo_habitacion);
		this.cuenta_habitacion = Double.valueOf(cuenta_habitacion);
		this.num_hab = Long.valueOf(num_hab);
		this.ocupada = Integer.parseInt(ocupada);
	}
	/******************************************************************************
	 * METODOS
	 ******************************************************************************/

	public int getOcupada() {
		return ocupada;
	}

	public void setOcupada(int ocupada) {
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

	@Override
	public String toString() {
		return "Habitacion [tipo_habitacion=" + tipo_habitacion
				+ ", cuenta_habitacion=" + cuenta_habitacion + ", ocupada="
				+ ocupada + ", num_hab=" + num_hab + "]";
	}
}