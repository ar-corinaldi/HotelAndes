package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.TipoServicio;


public class SQLTipoServicio {

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
	private PersistenciaHotelAndes ph;

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicaci�n
	 */
	public SQLTipoServicio (PersistenciaHotelAndes ph)
	{
		this.ph = ph;
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un tipo de Servicio a la base de datos de HotelAndes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del tipo de Servicio
	 * @param nombre - El nombre del tipo de Servicio
	 * @return El n�mero de tuplas insertadas
	 */
	public long adicionarTipoServicio (PersistenceManager pm, long id, String nombre) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + "TIPO_SERVICIOS" + "(id, nombre)values ( "
        		+ id + " ,"
        		+ "'"+ nombre+"' )");
        return (long) q.executeUnique();
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un tipo de Servicio de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del tipo de Servicio
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarTipoServicioPorId (PersistenceManager pm, long id)
	{
       Query q = pm.newQuery(SQL, "DELETE FROM " + "TIPO_SERVICIOS" + " WHERE id = " +id);
       q.setParameters(id);
       return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UN Tipo de Servicio de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del Tipo de Servicio
	 * @return El objeto Tipo de Servicio que tiene el identificador dado
	 */
	public TipoServicio darTipoServicioPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + "TIPO_SERVICIOS" + " WHERE id = " +id);
		q.setResultClass(TipoServicio.class);
		q.setParameters(id);
		return (TipoServicio) q.executeUnique();
	}


	public List<TipoServicio> darTiposServicio(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + "TIPO_SERVICIOS");
		q.setResultClass(TipoServicio.class);
		return (List<TipoServicio>) q.executeList();
	}
}