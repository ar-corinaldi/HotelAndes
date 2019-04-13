package hotelAndes.negocio;

import java.util.ArrayList;

public class Convencion {

	
	private int id;
	
	private int numPersonas;
	private String nombre;
	
	private Usuario organizador;
	
	private ArrayList<Usuario> clientes;
	
	private PlanConsumo plan;

	
	public Convencion(int id, int numPersonas, String nombre, Usuario organizador, ArrayList<Usuario> clientes, PlanConsumo plan) {
		this.id = id;
		this.numPersonas = numPersonas;
		this.nombre = nombre;
		this.organizador = organizador;
		this.clientes = clientes;
		this.setPlan(plan);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public int getNumPersonas() {
		return numPersonas;
	}

	public Usuario getOrganizador() {
		return organizador;
	}

	public ArrayList<Usuario> getClientes() {
		return clientes;
	}

	public PlanConsumo getPlan() {
		return plan;
	}

	public void setPlan(PlanConsumo plan) {
		this.plan = plan;
	}
	
	
}
