package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Reservas implements VOReserva{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/


	private long id;

	private Timestamp entrada;

	private Timestamp salida;

	private Timestamp check_in;

	private Timestamp check_out;

	private int num_personas;

	private long id_usuario;

	private String tipo_documento_usuario;

	private long id_habitacion;

	/******************************************************************************
	 * CONSTRUCTORES
	 ******************************************************************************/

	public Reservas() {
		salida = new Timestamp(0);
		entrada = new Timestamp(0);
		setId_usuario(0);
		setTipo_documento_usuario("");
		setNum_personas(0);
		id = 0;
		check_in = null;
		check_out = null;

	}

	public Reservas(long id, int numPersonas,Timestamp entrada, Timestamp salida, Timestamp checkIn, Timestamp checkOut, long pUsuario, String tipoDoc, long pHabitacion) {
		super();
		this.id = id;
		this.entrada = entrada;
		this.salida = salida;
		this.num_personas = numPersonas;
		setTipo_documento_usuario(tipoDoc);
		this.setId_habitacion(pHabitacion);
		this.setTipo_documento_usuario(tipoDoc);
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

	public int getNum_personas() {
		return num_personas;
	}

	public void setNum_personas(int num_personas) {
		this.num_personas = num_personas;
	}

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getTipo_documento_usuario() {
		return tipo_documento_usuario;
	}

	public void setTipo_documento_usuario(String tipo_documento_usuario) {
		this.tipo_documento_usuario = tipo_documento_usuario;
	}

	public long getId_habitacion() {
		return id_habitacion;
	}

	public void setId_habitacion(long id_habitacion) {
		this.id_habitacion = id_habitacion;
	}

	@Override
	public String toString() {
		return "Reservas [id=" + id + ", entrada=" + entrada + ", salida="
				+ salida + ", check_in=" + check_in + ", check_out="
				+ check_out + ", num_personas=" + num_personas
				+ ", id_usuario=" + id_usuario + ", tipo_documento_usuario="
				+ tipo_documento_usuario + ", id_habitacion=" + id_habitacion
				+ "]";
	}
	
	

}
