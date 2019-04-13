package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.TipoUsuario;

public class SQLTipoUsuario {
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
	public SQLTipoUsuario (PersistenciaHotelAndes ph)
	{
		this.ph = ph;
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un tipo de TipoUsuario a la base de datos de HotelAndes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del tipo de TipoUsuario
	 * @param nombre - El nombre del tipo de TipoUsuario
	 * @return El número de tuplas insertadas
	 */
	public long adicionarTipoUsuario (PersistenceManager pm, long id, String nombre) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaTipoUsuario() + "(id, nombre) values (?, ?)");
        q.setParameters(id, nombre);
        return (long) q.executeUnique();
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un tipo de TipoUsuario de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del tipo de TipoUsuario
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarTipoUsuarioPorId (PersistenceManager pm, long id)
	{
       Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaTipoUsuario() + " WHERE id = ?");
       q.setParameters(id);
       return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN Tipo de TipoUsuario de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del Tipo de TipoUsuario
	 * @return El objeto Tipo de PlanConsumo que tiene el identificador dado
	 */
	public TipoUsuario darTipoUsuarioPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaUsuario () + " WHERE id = ?");
		q.setResultClass(TipoUsuario.class);
		q.setParameters(id);
		return (TipoUsuario) q.executeUnique();
	}


	public List<TipoUsuario> darTiposUsuario(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaTipoUsuario());
		q.setResultClass(TipoUsuario.class);
		return (List<TipoUsuario>) q.executeList();
	}
}
