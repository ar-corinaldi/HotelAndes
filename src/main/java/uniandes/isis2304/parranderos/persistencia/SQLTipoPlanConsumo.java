package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.PlanConsumo;
import uniandes.isis2304.parranderos.negocio.TipoPlanConsumo;


public class SQLTipoPlanConsumo {
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
	public SQLTipoPlanConsumo (PersistenciaHotelAndes ph)
	{
		this.ph = ph;
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un tipo de PlanConsumo a la base de datos de HotelAndes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del tipo de PlanConsumo
	 * @param nombre - El nombre del tipo de PlanConsumo
	 * @return El número de tuplas insertadas
	 */
	public long adicionarTipoPlanConsumo (PersistenceManager pm, long id, String nombre) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaTipoPlanConsumo() + "(id, nombre) values (?, ?)");
        q.setParameters(id, nombre);
        return (long) q.executeUnique();
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un tipo de PlanConsumo de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del tipo de PlanConsumo
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarTipoPlanConsumoPorId (PersistenceManager pm, long id)
	{
       Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaTipoPlanConsumo() + " WHERE id = ?");
       q.setParameters(id);
       return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN Tipo de PlanConsumo de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del Tipo de PlanConsumo
	 * @return El objeto Tipo de PlanConsumo que tiene el identificador dado
	 */
	public TipoPlanConsumo darTipoPlanConsumoPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaTipoPlanConsumo () + " WHERE id = ?");
		q.setResultClass(TipoPlanConsumo.class);
		q.setParameters(id);
		return (TipoPlanConsumo) q.executeUnique();
	}


	public List<PlanConsumo> darTiposPlanConsumo(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaHabitacion());
		q.setResultClass(PlanConsumo.class);
		return (List<PlanConsumo>) q.executeList();
	}
}
