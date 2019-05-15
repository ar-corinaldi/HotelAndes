package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class Consumo {

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/

	private long id;

	private Timestamp fecha;

	private long id_usuario;
	
	private String tipo_documento_usuario;

	private long id_producto;
	
	private long id_habitacion;
	
	/******************************************************************************
	 * CONSTRUCTORES
	 ******************************************************************************/

	public Consumo() {
		id=0;
		fecha = new Timestamp(0);
		setId_usuario(0);
		id_producto=0;
		tipo_documento_usuario = "";
		id_habitacion = 0;
	}

	public Consumo(long id, Timestamp fecha, long idCliente, String tipoDoc, long idProd, long idHab) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.setId_usuario(idCliente);
		this.tipo_documento_usuario = tipoDoc;
		this.id_producto = idProd;
		this.id_habitacion = idHab;
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

	public long getId_producto() {
		return id_producto;
	}

	public void setId_producto(long id_producto) {
		this.id_producto = id_producto;
	}

	public long getId_habitacion() {
		return id_habitacion;
	}

	public void setId_habitacion(long id_habitacion) {
		this.id_habitacion = id_habitacion;
	}

	@Override
	public String toString() {
		return "Consumo [id=" + id + ", fecha=" + fecha + ", id_usuario="
				+ id_usuario + ", tipo_documento_usuario="
				+ tipo_documento_usuario + ", id_producto=" + id_producto
				+ ", id_habitacion=" + id_habitacion + "]";
	}
	
}
