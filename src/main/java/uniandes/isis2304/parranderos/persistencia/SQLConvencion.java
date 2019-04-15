package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Convencion;
import uniandes.isis2304.parranderos.negocio.Habitacion;

public class SQLConvencion {
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
	public SQLConvencion(PersistenciaHotelAndes ph) 
	{
		this.ph = ph;
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una habitacion a la base de datos de HotelAndes
	 * @param pm - El manejador de persistencia
	 * @param idHabitacion - El identificador de la Habitacion
	 * @param numHabitacion - El numero de la habitacion
	 * @param idConsumo - El identificador del consumo
	 * @param idHotel - El identificador del hotel
	 * @return El n�mero de tuplas insertadas
	 */
	public long adicionarConvencion(PersistenceManager pm, long id  , String nombre,  int cantidadPersonas,  long idPlanConsumo, long id_organizador, int tipo_documento_Organizador	)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO "+ ph.darTablaConvencion() + "(id, nombre, cantidadPersonas, "
				+ "idPlanConsumo, id_organizador,tipo_documento_Organizador	) values(?, ?, ?, ?, ?, ?)");
		q.setParameters(id, nombre, cantidadPersonas, idPlanConsumo, id_organizador,tipo_documento_Organizador	);
		return (long)q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar una Habitacion de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idHotel - El identificador del hotel
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminaConvencionPorId (PersistenceManager pm, long idConvencion)
	{
       Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaConvencion() + " WHERE id = ?");
       q.setParameters(idConvencion);
       return (long) q.executeUnique();
	}
	

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UNA Habitacion de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idHabitacion - El identificador de la Habitacion
	 * @return El objeto Habitacion que tiene el identificador dado
	 */
	public Convencion darHabitacionPorId (PersistenceManager pm, long idConvencion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaConvencion() + " WHERE id = ?");
		q.setResultClass(Convencion.class);
		q.setParameters(idConvencion);
		return (Convencion) q.executeUnique();
	}
	
	public List<Convencion> darHabitaciones(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaConvencion());
		q.setResultClass(Convencion.class);
		return (List<Convencion>) q.executeList();
	}

}