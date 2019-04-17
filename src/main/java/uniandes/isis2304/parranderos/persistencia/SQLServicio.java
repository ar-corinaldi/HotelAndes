package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Servicios;


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
	public long adicionarServicio (PersistenceManager pm, long id, String nombre, String descripcion, double costo, int cargado_habitacion,	 int capacidad, long id_tipo_servicios) 
	{
		
		Query q = pm.newQuery(SQL, "INSERT INTO " + "SERVICIOS"+ "(id, nombre, descripcion, costo, cargado_habitacion, capacidad, id_tipo_servicios) "
				+ "values ("
				+ id +", '"
				+ nombre +"', '"
				+ descripcion +"',"
				+ costo +","
				+ cargado_habitacion +","
				+ capacidad +","
				+ id_tipo_servicios +")");
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
		Query q = pm.newQuery(SQL, "DELETE FROM " + "SERVICIOS" + " WHERE id = "+idServicio);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UN Servicio de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idServicio - El identificador del Servicio
	 * @return El objeto Hotel que tiene el identificador dado
	 */
	public Servicios darServicioPorId (PersistenceManager pm, long idServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + "SERVICIOS" + " WHERE id = "+idServicio);
		q.setResultClass(Servicios.class);
		return (Servicios) q.executeUnique();
	}


	public List<Servicios> darServicios(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + "SERVICIOS");
		q.setResultClass(Servicios.class);
		return (List<Servicios>) q.executeList();
	}


	public Object darServiciosDisponibles(
			PersistenceManager pm, long tipo, int cantidad) {
		//		SELECT *
		//		FROM HABITACIONES 
		//		WHERE tipo_habitacion = 5 AND ocupada=0
		//		FETCH FIRST 30 ROWS ONLY;
		String sql = "SELECT * FROM SERVICIOS WHERE TIPO_SERVICIOS = "+ tipo +" FETCH FIRST "+cantidad+ " ROWS ONLY";
		Query q = pm.newQuery(SQL, sql);
		
		return (Object) q.executeUnique();
	}

//DEPRECIEATED XD
//	public long reservarServicioPorId(PersistenceManager pm, int reservado,
//			long id) {
//		Query q = pm.newQuery(SQL, "UPDATE " + "SERVICIOS" + " SET reservado = "+ reservado 
//				+" WHERE id = "+id);
//		return (long) q.executeUnique();
//	}

}