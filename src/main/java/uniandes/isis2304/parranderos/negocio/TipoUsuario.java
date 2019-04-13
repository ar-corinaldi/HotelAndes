package uniandes.isis2304.parranderos.negocio;

public class TipoUsuario {
	
	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/

	private long id;

	private String nombre;

	/******************************************************************************
	 * CONSTRUCTORES
	 ******************************************************************************/

	public TipoUsuario() {
		id = 0;
		nombre = "";
	}

	public TipoUsuario(long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
