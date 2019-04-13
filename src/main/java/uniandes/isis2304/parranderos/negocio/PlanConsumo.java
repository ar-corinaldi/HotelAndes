package uniandes.isis2304.parranderos.negocio;

public class PlanConsumo implements VOPlanConsumo{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/
	
	private long id;

	private double descuento;

	private String descripcion;

	private long tipoPlanConsumo;
	
	private Reserva reserva;
	
	/******************************************************************************
	 * CONSTRUCTOR
	 ******************************************************************************/
	
	public PlanConsumo() {
		id = 0;
		descuento = 0;
		descripcion = "";
		tipoPlanConsumo = 0;
		reserva = new Reserva();
	}
	
	public PlanConsumo(long id, double descuento, String descripcion,
			long tipoPlanConsumo) {
		super();
		this.id = id;
		this.descuento = descuento;
		this.descripcion = descripcion;
		this.tipoPlanConsumo = tipoPlanConsumo;
		reserva = new Reserva();
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

	public long getTipoPlanConsumo() {
		return tipoPlanConsumo;
	}

	public void setTipoPlanConsumo(long tipoPlanConsumo) {
		this.tipoPlanConsumo = tipoPlanConsumo;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	@Override
	public String toString() {
		return "PlanConsumo [id=" + id + ", descuento=" + descuento
				+ ", descripcion=" + descripcion + ", tipoPlanConsumo="
				+ tipoPlanConsumo + ", reserva=" + reserva + "]";
	}
	
}
