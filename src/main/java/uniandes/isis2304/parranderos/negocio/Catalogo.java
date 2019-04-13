package uniandes.isis2304.parranderos.negocio;

public class Catalogo {

	private long idServicio;
	
	private long idProducto;

	public Catalogo(long idServicio, long idProducto) {
		super();
		this.setIdServicio(idServicio);
		this.setIdProducto(idProducto);
	}

	public long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	public long getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}

}