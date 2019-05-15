package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class ReservaServicio {

	private long id;
	
	private Timestamp fecha_inicial;
	
	
	private Timestamp fecha_final;
	
	private long id_usuario; 
	
	private String tipo_documento_usuario;
	
	private long id_servicio;
	
	public ReservaServicio() {
		this.id = 0;
		this.fecha_inicial = new Timestamp(0);
		this.fecha_final = new Timestamp(0);
		this.id_usuario = 0;
		this.tipo_documento_usuario = null;
		this.id_servicio = 0;
	}
	
	public ReservaServicio(long id, Timestamp fecha_Inicial, Timestamp fecha_Final, long id_usuario,
			String tipo_documento_usuario, long id_servicio) {
		super();
		this.id = id;
		this.fecha_inicial = fecha_Inicial;
		this.fecha_final = fecha_Final;
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

	public Timestamp getFecha_inicial() {
		return fecha_inicial;
	}

	public void setFecha_inicial(Timestamp fecha_inicial) {
		this.fecha_inicial = fecha_inicial;
	}

	public Timestamp getFecha_final() {
		return fecha_final;
	}

	public void setFecha_final(Timestamp fecha_final) {
		this.fecha_final = fecha_final;
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

	@Override
	public String toString() {
		return "ReservaServicio [id=" + id + ", fecha_inicial=" + fecha_inicial
				+ ", fecha_final=" + fecha_final + ", id_usuario=" + id_usuario
				+ ", tipo_documento_usuario=" + tipo_documento_usuario
				+ ", id_servicio=" + id_servicio + "]";
	}

}
