package uniandes.isis2304.parranderos.negocio;

public class Habitacion implements VOHabitacion{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/
						

	
	private long tipoHabitacion;

	private double cuentaHab;
	
	private boolean ocupada;
	
	private long numHabitacion;

	

	/******************************************************************************
	 * CONSTRUCTOR
	 ******************************************************************************/
	
	public Habitacion() {
		tipoHabitacion = 0;
		cuentaHab = 0.0;
		numHabitacion = 0;
		setOcupada(false);
	}
	
	public Habitacion( long id, long tipoHabitacion,  double cuentaHab) {
		this.tipoHabitacion = tipoHabitacion;
		this.cuentaHab = cuentaHab;
		this.numHabitacion = id;
		setOcupada(false);
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

	public long getNumHabitacion(){
		return numHabitacion;
	}

	public void setNumHabitacion( long id ){
		this.numHabitacion = id;
	}



	public boolean isOcupada() {
		return ocupada;
	}

	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}

	@Override
	public String toString() {
		return "Habitacion [tipoHabitacion=" + tipoHabitacion + ", cuentaHab=" + cuentaHab + ", numHabitacion=" + numHabitacion + "]";
	}

	public boolean isOcupada() {
		return ocupada;
	}

	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}
	
}