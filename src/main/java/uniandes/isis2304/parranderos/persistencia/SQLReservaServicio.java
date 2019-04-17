package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Reservas;
import uniandes.isis2304.parranderos.negocio.ReservaServicio;

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
		String fechaInicialTS = "TO_TIMESTAMP('"+fechaInicial.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
		String fechaFinalTS = "TO_TIMESTAMP('"+fechaFinal.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";

		Query q = pm.newQuery(SQL, "INSERT INTO " + "RESERVAS_SERVICIOS" + "(id, fecha_inicial, fecha_final, id_usuario, tipo_documento_usuario, id_servicio) values ("+id+", "
				+ fechaInicialTS+", "
				+ fechaFinalTS+", "
				+ idUsuario+", '"
				+ tipoDoc+"', "
				+ idServicio+")");
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
		Query q = pm.newQuery(SQL, "DELETE FROM " + "RESERVAS_SERVICIOS"+ " WHERE id = " + idReservaServicio);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UNA Reserva de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReserva - El identificador del bar
	 * @return El objeto Reserva que tiene el identificador dado
	 */
	public Reservas darReservaServicioPorId (PersistenceManager pm, long idReservaServicio) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " +  "RESERVAS_SERVICIOS"+  " WHERE id = " + idReservaServicio);
		q.setResultClass(ReservaServicio.class);
		return (Reservas) q.executeUnique();
	}

	public List<Reservas> darReservaServicios(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + "RESERVAS_SERVICIOS");
		q.setResultClass(ReservaServicio.class);
		return (List<Reservas>) q.executeList();
	}

	public long cancelarReservasServicios(PersistenceManager pm, long numIdentidad,
			String tipoDocumento) {
		String sql = "DELETE FROM RESERVAS_SERVICIOS WHERE id_usuario = "+ numIdentidad + "	AND tipo_documento_usuario = '" + tipoDocumento+ "'";
		Query q = pm.newQuery(SQL,sql);
		return (long) q.executeUnique();

	}

	public Object verificarDisponibilidad(PersistenceManager pm, long tipo, int cantidad,
			Timestamp entrada, Timestamp salida) {
		String entradaTS = "TO_TIMESTAMP('"+entrada.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
		String salidaTS = "TO_TIMESTAMP('"+salida.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";

		String sql =  "SELECT ser.id, ser.nombre, ser.descripcion, ser.costo, ser.cargado_habitacion, ser.capacidad, ser.tipo_servicios ";
		sql += "FROM  RESERVAS_SERVICIOS, SERVICIOS ser ";
		sql += "WHERE RESERVAS_SERVICIOS.fecha_inicial BETWEEN "+entradaTS+ " AND "+salidaTS+" AND ser.tipo_servicios = "+tipo + " ";
		sql += "FETCH FIRST 1 ROW ONLY";		
		System.out.println(sql);
		Query q = pm.newQuery(SQL, sql);

		return (Object) q.executeUnique();
	}

	public long terminarMantenimiento(PersistenceManager pm, Long num_identidad, String tipo_documento,
			int idServ) {
		String sql = "DELETE FROM RESERVAS_SERVICIOS WHERE id_usuario = "+ num_identidad + "	AND tipo_documento_usuario = '" + tipo_documento+ 
				"' AND ID_SERVICIO = "+idServ;
		Query q = pm.newQuery(SQL,sql);
        return (long) q.executeUnique();		
	}
}
