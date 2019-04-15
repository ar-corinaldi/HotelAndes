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

	private Timestamp check_in;
	
	private Timestamp check_out;
	
	private int numPersonas;

	private Usuarios usuario;

	private Habitacion habitacion;
	
	/******************************************************************************
	 * CONSTRUCTORES
	 ******************************************************************************/

	public Reserva() {
		salida = new Timestamp(0);
		entrada = new Timestamp(0);
		usuario = new Usuarios();
		habitacion= new Habitacion();
		setNumPersonas(0);
		id = 0;
		check_in = null;
		check_out = null;
		
	}

	public Reserva(long id, int numPersonas,Timestamp entrada, Timestamp salida, Timestamp checkIn, Timestamp checkOut, Usuarios pUsuario, Habitacion pHabitacion) {
		super();
		this.id = id;
		this.entrada = entrada;
		this.salida = salida;
		this.setNumPersonas(numPersonas);
		usuario = pUsuario;
		habitacion= pHabitacion;
		check_in = null;
		check_out = null;
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

	

	public Usuarios getUsuarios() {
		return usuario;
	}

	public void setIdCliente(Usuarios usuarios) {
		this.usuario = usuarios;
	}

	public int getNumPersonas() {
		return numPersonas;
	}

	public void setNumPersonas(int numPersonas) {
		this.numPersonas = numPersonas;
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

	public Timestamp getCheck_in() {
		return check_in;
	}

	public void setCheck_in(Timestamp check_in) {
		this.check_in = check_in;
	}

	public Timestamp getCheck_out() {
		return check_out;
	}

	public void setCheck_out(Timestamp check_out) {
		this.check_out = check_out;
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", entrada=" + entrada
				+ ", salida=" + salida 
				+ ", usuarios=" + usuario+ ", habitacion=" + habitacion + "]";
	}

}
