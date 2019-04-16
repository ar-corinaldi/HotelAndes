package  uniandes.isis2304.parranderos.negocio;

import java.util.ArrayList;

public class Convencion {

	
	private long id;

	private String nombre;

	private int num_personas;
	
	private long id_plan_consumo;
	
	private long id_usuario;
	
	private String tipo_documento_usuario;
	
	private Usuarios organizador;
	
	private ArrayList<Usuarios> clientes;
	
	private PlanConsumo plan;

	
	public Convencion(long id, String nombre, int numPersonas, long idPlanCons, long idUsuario, String tipoDoc) {
		this.id = id;
		this.num_personas = numPersonas;
		this.nombre = nombre;
		id_plan_consumo = idPlanCons;
		id_usuario = idUsuario;
		tipo_documento_usuario = tipoDoc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNum_personas() {
		return num_personas;
	}

	public void setNum_personas(int num_personas) {
		this.num_personas = num_personas;
	}

	public long getId_plan_consumo() {
		return id_plan_consumo;
	}

	public void setId_plan_consumo(long id_plan_consumo) {
		this.id_plan_consumo = id_plan_consumo;
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

	public void setOrganizador(Usuarios organizador) {
		this.organizador = organizador;
	}

	public void setClientes(ArrayList<Usuarios> clientes) {
		this.clientes = clientes;
	}

	public Usuarios getOrganizador() {
		return organizador;
	}

	public ArrayList<Usuarios> getClientes() {
		return clientes;
	}

	public PlanConsumo getPlan() {
		return plan;
	}

	public void setPlan(PlanConsumo plan) {
		this.plan = plan;
	}

	@Override
	public String toString() {
		return "Convencion [id=" + id + ", nombre=" + nombre
				+ ", num_personas=" + num_personas + ", id_plan_consumo="
				+ id_plan_consumo + ", id_usuario=" + id_usuario
				+ ", tipo_documento_usuario=" + tipo_documento_usuario
				+ ", organizador=" + organizador + ", clientes=" + clientes
				+ ", plan=" + plan + "]";
	}
	
}
