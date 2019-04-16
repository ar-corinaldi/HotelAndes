package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class ReservaServicio {

	private long id;
	
	private Timestamp fecha_Inicial;
	
	
	private Timestamp fecha_Final;
	
	private long id_usuario; 
	
	private String tipo_documento_usuario;
	
	private long id_servicio;
	
	public ReservaServicio() {
		this.id = 0;
		this.fecha_Inicial = new Timestamp(0);
		this.fecha_Final = new Timestamp(0);
		this.id_usuario = 0;
		this.tipo_documento_usuario = null;
		this.id_servicio = 0;
	}
	
	public ReservaServicio(long id, Timestamp fecha_Inicial, Timestamp fecha_Final, long id_usuario,
			String tipo_documento_usuario, long id_servicio) {
		super();
		this.id = id;
		this.fecha_Inicial = fecha_Inicial;
		this.fecha_Final = fecha_Final;
		this.id_usuario = id_usuario;
		this.tipo_documento_usuario = tipo_documento_usuario;
		this.id_servicio = id_servicio;
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getFecha_Inicial() {
		return fecha_Inicial;
	}

	public void setFecha_Inicial(Timestamp fecha_Inicial) {
		this.fecha_Inicial = fecha_Inicial;
	}

	public Timestamp getFecha_Final() {
		return fecha_Final;
	}

	public void setFecha_Final(Timestamp fecha_Final) {
		this.fecha_Final = fecha_Final;
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

	public long getId_servicio() {
		return id_servicio;
	}

	public void setId_servicio(long id_servicio) {
		this.id_servicio = id_servicio;
	}


}
