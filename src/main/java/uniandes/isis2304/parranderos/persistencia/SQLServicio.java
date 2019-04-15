package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Servicio;


public class SQLServicio {

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
	public SQLServicio (PersistenciaHotelAndes ph)
	{
		this.ph = ph;
	}


	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un Servicio a la base de datos de HotelAndes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del Servicio
	 * @param nombre - El nombre del Servicio
	 * @param descripcion - La descripcion del servicio
	 * @param costo - El costo del Servicio
	 * @param cargadoHabitacion - Indica si se carga a la habitacion
	 * @param reservado - Indica si el servicio esta reservado o no
	 * @param fechaInicial - La fechaInicial del servicio
	 * @param fechaFinal - La fechaFinal del servicio
	 * @param idHotel - El identificador del Servicio
	 * @param tipoServicio - El tipo del Servicio
	 * @return El n�mero de tuplas insertadas
	 */
	public long adicionarServicio (PersistenceManager pm, long id, String nombre, String descripcion, 
			double costo, int cargadoHabitacion,  int capacidad, int reservado, Timestamp fechaInicial,
			Timestamp fechaFinal, long idHotel, long tipoServicio) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaServicio() + "(id, nombre, descripcion, costo, cargado_habitacion, capacidad, reservado, fecha_inicial, fecha_final, tipo_servicios, id_hotel) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(id, nombre, descripcion, costo, cargadoHabitacion, capacidad, reservado, fechaInicial, fechaFinal, idHotel, tipoServicio );
		return (long) q.executeUnique();
	}


	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un Servicio de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del Servicio
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarServicioPorId (PersistenceManager pm, long idServicio)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaServicio() + " WHERE id = ?");
		q.setParameters(idServicio);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UN Servicio de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del Servicio
	 * @return El objeto Hotel que tiene el identificador dado
	 */
	public Servicio darServicioPorId (PersistenceManager pm, long idServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaServicio () + " WHERE id = ?");
		q.setResultClass(Servicio.class);
		q.setParameters(idServicio);
		return (Servicio) q.executeUnique();
	}


	public List<Servicio> darServicios(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaServicio());
		q.setResultClass(Servicio.class);
		return (List<Servicio>) q.executeList();
	}

}