package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Habitacion;


public class SQLHabitacion 
{
	
	
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
	public SQLHabitacion(PersistenciaHotelAndes ph) 
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
	public long adicionarHabitacion(PersistenceManager pm, int numHab, int ocupada, double cuenta_habitacion, long tipo_habitacion, long id_hotel)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO "+ ph.darTablaHabitacion() + "(num_hab, ocupada, cuenta_habitacion, "
		q.setParameters(numHab, ocupada,cuenta_habitacion, tipo_habitacion, id_hotel);
				+ "tipo_habitacion, id_hotel) values(?, ?, ?,?, ?)");
		return (long)q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar una Habitacion de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idHotel - El identificador del hotel
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarHabitacionPorId (PersistenceManager pm, int num_hab)
	{
       Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaHabitacion() + " WHERE num_hab = ?");
       q.setParameters(num_hab);
       return (long) q.executeUnique();
	}
	

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UNA Habitacion de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idHabitacion - El identificador de la Habitacion
	 * @return El objeto Habitacion que tiene el identificador dado
	 */
	public Habitacion darHabitacionPorId (PersistenceManager pm, long num_hab) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaHabitacion() + " WHERE num_hab = ?");
		q.setResultClass(Habitacion.class);
		q.setParameters(num_hab);
		return (Habitacion) q.executeUnique();
	}
	
	public List<Habitacion> darHabitaciones(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaHabitacion());
		q.setResultClass(Habitacion.class);
		return (List<Habitacion>) q.executeList();
	}
}