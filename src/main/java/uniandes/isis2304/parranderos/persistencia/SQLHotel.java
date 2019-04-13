package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.HotelAndes;


public class SQLHotel {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaHotelAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaHotelAndes ph;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLHotel (PersistenciaHotelAndes ph)
	{
		this.ph = ph;
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un Hotel a la base de datos de HotelAndes
	 * @param pm - El manejador de persistencia
	 * @param idHotel - El identificador del hotel
	 * @param nombre - El nombre del hotel
	 * @param pais - El pais del hotel
	 * @param ciudad - La ciudad del bar
	 * @param capacidad - La capacidad del hotel
	 * @return El número de tuplas insertadas
	 */
	public long adicionarHotel (PersistenceManager pm, long idHotel, String nombre, String pais,  String ciudad, int capacidad) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaHotel() + "(id, nombre, pais, ciudad, capacidad) values (?, ?, ?, ?, ?)");
        q.setParameters(idHotel, nombre, pais, ciudad, capacidad);
        return (long) q.executeUnique();
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un Hotel de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idHotel - El identificador del hotel
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarHotelPorId (PersistenceManager pm, long idHotel)
	{
       Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaHotel() + " WHERE id = ?");
       q.setParameters(idHotel);
       return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN HOTEL de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idHotel - El identificador del hotel
	 * @return El objeto Hotel que tiene el identificador dado
	 */
	public HotelAndes darHotelPorId (PersistenceManager pm, long idHotel) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaHotel () + " WHERE id = ?");
		q.setResultClass(HotelAndes.class);
		q.setParameters(idHotel);
		return (HotelAndes) q.executeUnique();
	}
}
