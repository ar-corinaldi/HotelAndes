package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

public class Check {

	private long id;
	
	private Timestamp fecha;
	
	private boolean ingreso;
	
	private long idUsuario;
	
	private long idHabitacion;
	

	private String tipoDocumentoUsuario;

	public Check() {
		id = 0;
		fecha = new Timestamp(0);
		idUsuario = 0;
		ingreso=false;
		tipoDocumentoUsuario = "";
		idHabitacion = 0;	
	}
	
	public Check(long id, Timestamp fecha, boolean ingreso, long idUsuario,  String tipoDocumentoUsuario, long idHabitacion) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.ingreso = ingreso;
		this.idUsuario = idUsuario;
		this.idHabitacion = idHabitacion;
		this.setTipoDocumentoUsuario(tipoDocumentoUsuario);
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public boolean isIngreso() {
		return ingreso;
	}

	public void setIngreso(boolean ingreso) {
		this.ingreso = ingreso;
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public long getIdHabitacion() {
		return idHabitacion;
	}

	public void setIdHabitacion(long idHabitacion) {
		this.idHabitacion = idHabitacion;
	}


	public String getTipoDocumentoUsuario() {
		return tipoDocumentoUsuario;
	}


	public void setTipoDocumentoUsuario(String tipoDocumentoUsuario) {
		this.tipoDocumentoUsuario = tipoDocumentoUsuario;
	}
	
	
	
	
}
