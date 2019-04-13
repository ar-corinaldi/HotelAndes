package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class Consumo implements VOConsumo{

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/

	private long id;

	private Timestamp fecha;

	private long idUsuario;
	
	private String tipoDocumentoUsuario;

	private long idServicio;
	
	private Habitacion habitacion;
	
	/******************************************************************************
	 * CONSTRUCTORES
	 ******************************************************************************/

	public Consumo() {
		id=0;
		fecha = new Timestamp(0);
		idUsuario = 0;
		idServicio=0;
		tipoDocumentoUsuario = "";
		habitacion = new Habitacion();
	}

	public Consumo(long id, Timestamp fecha, long idCliente, long idServicio ) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.idUsuario = idCliente;
		this.idServicio = idServicio;
		this.habitacion = new Habitacion();
	}

	/******************************************************************************
	 * METODOS
	 ******************************************************************************/
	public long getId(){
		return id;
	}

	public void setId( long id ){
		this.id=id;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}
	
	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}

	public String getTipoDocumentoUsuario() {
		return tipoDocumentoUsuario;
	}

	public void setTipoDocumentoUsuario(String tipoDocumentoUsuario) {
		this.tipoDocumentoUsuario = tipoDocumentoUsuario;
	}

	@Override
	public String toString() {
		return "Consumo [id="+ id +"fecha=" + fecha + ", idCliente=" + idUsuario
				+ ", idServicio=" + idServicio + ", habitacion=" + habitacion +"]";
	}
	
}
