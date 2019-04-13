package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Check;


public class SQLCheck {
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
	public SQLCheck(PersistenciaHotelAndes pp) {
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un Check a la base de datos de HotelAndes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del Check
	 * @param fecha - La fecha  del  Check
	 * @param ingreso - El indicador si es o no check ingreso
	 * @param id_usuario - El identificador del usuario que hace el Check
	 * @param id_habitacion - El id de la habitacion del  Check
	 * @return El n�mero de tuplas insertadas
	 */
	public long adicionarCheck (PersistenceManager pm, long id, Timestamp fecha, int ingreso, long id_usuario, 
			String tipo_documento_usuario, long id_habitacion){
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCheck () + "(id,	fecha, ingreso, id_usuario,	tipo_documento_usuario,	id_habitacion) values (?, ?, ?, ?, ?)");
        q.setParameters(id,	fecha,	ingreso,	id_usuario,	tipo_documento_usuario,	id_habitacion);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN Check de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCheck - El identificador del Check
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarCheckPorId (PersistenceManager pm, long idCheck)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCheck() + " WHERE id = ?");
        q.setParameters(idCheck);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UN Check de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCheck - El identificador del bar
	 * @return El objeto Check que tiene el identificador dado
	 */
	public Check darCheckPorId (PersistenceManager pm, long idCheck) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCheck () + " WHERE id = ?");
		q.setResultClass(Check.class);
		q.setParameters(idCheck);
		return (Check) q.executeUnique();
	}
	
	public List<Check> darChecks(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCheck());
		q.setResultClass(Check.class);
		return (List<Check>) q.executeList();
	}
	
}
