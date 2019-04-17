package uniandes.isis2304.parranderos.negocio;

public class PlanConsumo implements VOPlanConsumo{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/
	
	
							
	private long id;

	private double descuento;

	private String descripcion;

	
	
	/******************************************************************************
	 * CONSTRUCTOR
	 ******************************************************************************/
	
	public PlanConsumo() {
		id = 0;
		descuento = 0;
		descripcion = "";
	}
	
	public PlanConsumo(long id, double descuento, String descripcion) {
		this.id = id;
		this.descuento = descuento;
		this.descripcion = descripcion;
	}

	/******************************************************************************
	 * METODOS
	 ******************************************************************************/

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	


	@Override
	public String toString() {
		return "PlanConsumo [id=" + id + ", descuento=" + descuento
				+ ", descripcion=" + descripcion + "]";
	}
	
}
