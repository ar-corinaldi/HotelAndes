package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Reserva implements VOReserva{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/
	private long id;

	private Timestamp entrada;

	private Timestamp salida;

	private int numPersonas;

	private List<Usuario> usuarios;

	private Habitacion habitacion;
	
	private PlanConsumo planConsumo;

	/******************************************************************************
	 * CONSTRUCTORES
	 ******************************************************************************/

	public Reserva() {
		salida = new Timestamp(0);
		entrada = new Timestamp(0);
		numPersonas = 0;
		usuarios = new ArrayList<>();
		habitacion= new Habitacion();
		id = 0;
		planConsumo = new PlanConsumo();
	}

	public Reserva(long id, Timestamp entrada, Timestamp salida, int numPersonas) {
		super();
		this.id = id;
		this.entrada = entrada;
		this.salida = salida;
		this.numPersonas = numPersonas;
		usuarios = new ArrayList<>();
		habitacion= new Habitacion();
		planConsumo = new PlanConsumo();
	}

	/******************************************************************************
	 * METODOS
	 ******************************************************************************/

	public Timestamp getEntrada() {
		return entrada;
	}

	public void setEntrada(Timestamp entrada) {
		this.entrada = entrada;
	}

	public Timestamp getSalida() {
		return salida;
	}

	public void setSalida(Timestamp salida) {
		this.salida = salida;
	}

	public int getNumPersonas() {
		return numPersonas;
	}

	public void setNumPersonas(int numPersonas) {
		this.numPersonas = numPersonas;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setIdCliente(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id= id;
	}
	
	public PlanConsumo getPlanConsumo() {
		return planConsumo;
	}

	public void setPlanConsumo(PlanConsumo planConsumo) {
		this.planConsumo = planConsumo;
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", entrada=" + entrada
				+ ", salida=" + salida + ", numPersonas=" + numPersonas
				+ ", usuarios=" + usuarios+ ", habitacion=" + habitacion
				+ ", planConsumo=" + planConsumo + "]";
	}

}
