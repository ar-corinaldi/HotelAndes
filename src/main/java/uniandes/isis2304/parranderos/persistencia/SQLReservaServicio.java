package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Reserva;

public class SQLReservaServicio {

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
	public SQLReservaServicio(PersistenciaHotelAndes pp) {
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una Reserva a la base de datos de HotelAndes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador de la Reserva
	 * @param numPersonas - El numero de Personas de la  Reserva
	 * @param entrada - La fecha de entrada de la  Reserva
	 * @param salida - La fecha de salida de la  Reserva
	 * @param id_plan_consumo - El identificador del plan de consumo de la  Reserva
	 * @param id_habitacion - El id de la habitacion de la  Reserva
	 * @return El n�mero de tuplas insertadas
	 */
	public long adicionarReservaServicio (PersistenceManager pm, long id, Timestamp fechaInicial, Timestamp fechaFinal, long idUsuario, String tipoDoc, long idServicio) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaReserva () + "(id, fecha_inicial, fecha_final, id_usuario, tipo_documento_usuario, id_servicio) values (?, ?, ?, ?, ?, ?)");
        q.setParameters(id, fechaInicial, fechaFinal, idUsuario, tipoDoc, idServicio);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UNA Reserva de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReserva - El identificador del Reserva
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarReservaServicioPorId (PersistenceManager pm, long idReservaServicio)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaReservaServicio() + " WHERE id = ?");
        q.setParameters(idReservaServicio);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UNA Reserva de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReserva - El identificador del bar
	 * @return El objeto Reserva que tiene el identificador dado
	 */
	public Reserva darReservaServicioPorId (PersistenceManager pm, long idReservaServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReservaServicio() + " WHERE id = ?");
		q.setResultClass(ReservaServicio.class);
		q.setParameters(idReserva);
		return (Reserva) q.executeUnique();
	}
	
	public List<Reserva> darReservaServicios(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaReservaServicio());
		q.setResultClass(ReservaServicio.class);
		return (List<Reserva>) q.executeList();
	}
}
