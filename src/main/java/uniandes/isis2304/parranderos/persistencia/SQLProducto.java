package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Producto;

public class SQLProducto {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra ac� para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaHotelAndes.SQL;
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicaci�n
	 */
	private PersistenciaHotelAndes pp;

	

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicaci�n
	 */
	public SQLProducto(PersistenciaHotelAndes pp) {
		super();
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un producto a la base de datos de HotelAndes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del producto
	 * @param nombre - El nombre del producto
	 * @param descripcion - descripcion del producto
	 * @param precio - precio del producto
	 * @param idServicio - El identificador del servicio
	 * @return El n�mero de tuplas insertadas
	 */
	public long adicionarProducto (PersistenceManager pm, long id, String nombre, String descripcion, double precio,  long idServicio) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaProducto () + "(id, nombre,  descripcion, precio, id_servicio) values (?, ?, ?, ?, ?)");
        q.setParameters(id, nombre, descripcion, precio, idServicio);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN PRODUCTO de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del producto
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarProductoPorId (PersistenceManager pm, long idProducto)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto() + " WHERE id = ?");
        q.setParameters(idProducto);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UN PRODUCTOS de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del bar
	 * @return El objeto PRODUCTOS que tiene el identificador dado
	 */
	public Producto darProductoPorId (PersistenceManager pm, long idProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + "PRODUCTOS" + " WHERE id = "+idProducto);
		Object o =q.executeUnique();
		if( o == null ) return null;
		else{
			
			Object[] datos = (Object[]) o;
			String nombre = datos[1].toString();
			String descripcion = datos[2]==null? null:datos[2].toString();
			double precio = ((BigDecimal) datos[3]).doubleValue();
			long idServ= ((BigDecimal) datos[4]).longValue();
			return  new Producto(idProducto, nombre, descripcion, precio, idServ);
		}
			
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS BARES de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreBar - El nombre de bar buscado
	 * @return Una lista de objetos BAR que tienen el nombre dado
	 */
	public List<Producto> darProductoPorNombre (PersistenceManager pm, String nombreBar) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto () + " WHERE nombre = ?");
		q.setResultClass(Producto.class);
		q.setParameters(nombreBar);
		return (List<Producto>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS Producto de la 
	 * base de datos de HotelAndes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos Productos
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Producto> darProductos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto ());
		q.setResultClass(Producto.class);
		return (List<Producto>) q.executeList();
	}
	
	

	
}
