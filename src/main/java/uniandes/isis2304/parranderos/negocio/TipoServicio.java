package uniandes.isis2304.parranderos.negocio;

public class TipoServicio {

	/******************************************************************************
	 * ATRIBUTOS
	 ******************************************************************************/

	private long id;

	private String nombre;
	
	

	/******************************************************************************
	 * CONSTRUCTORES
	 ******************************************************************************/
	public TipoServicio() {
		setId(0);
		setNombre("");
	}

	/******************************************************************************
	 * METODOS
	 ******************************************************************************/

	public TipoServicio(long id, String nombre) {
		super();
		this.setId(id);
		this.setNombre(nombre);
	}

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