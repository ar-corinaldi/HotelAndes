package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Habitaciones;


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
	public long adicionarHabitacion(PersistenceManager pm, int numHab, double cuenta_habitacion, long tipo_habitacion)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO "+ ph.darTablaHabitacion() + "(num_hab,  cuenta_habitacion, "
				+ "tipo_habitacion) values(?, ?, ?)");
		q.setParameters(numHab, cuenta_habitacion, tipo_habitacion);
		return (long)q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar una Habitacion de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idHotel - El identificador del hotel
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarHabitacionPorId (PersistenceManager pm, long num_hab)
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
	public Habitaciones darHabitacionPorId (PersistenceManager pm, long num_hab) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + "HABITACIONES" + " WHERE num_hab = "+num_hab);
		q.setResultClass(Habitaciones.class);
		return (Habitaciones) q.executeUnique();
	}

	public List<Habitaciones> darHabitaciones(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaHabitacion());
		q.setResultClass(Habitaciones.class);
		return (List<Habitaciones>) q.executeList();
	}

	public List<Object> darHabitacionesDisponibles(
			PersistenceManager pm, long tipo, int cantidad, Timestamp entrada, Timestamp salida) {

		String entradaTS = "TO_TIMESTAMP('"+entrada.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
		String salidaTS = "TO_TIMESTAMP('"+salida.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
		//		SELECT hab.num_hab, hab.cuenta_habitacion, hab.tipo_habitacion
		//		FROM HABITACIONES hab, Reservas res
		//		WHERE hab.tipo_habitacion = 2 AND hab.num_hab NOT IN (SELECT id_habitacion 
		//		FROM RESERVAS 
		//		WHERE RESERVAS.ENTRADA BETWEEN TO_TIMESTAMP('2019-09-16 06:00:00.0', 'YYYY-MM-DD HH24:MI:SS.FF') AND TO_TIMESTAMP('2019-09-23 12:00:00.0', 'YYYY-MM-DD HH24:MI:SS.FF') AND 
		//		RESERVAS.SALIDA BETWEEN TO_TIMESTAMP('2019-09-16 06:00:00.0', 'YYYY-MM-DD HH24:MI:SS.FF') AND TO_TIMESTAMP('2019-09-23 12:00:00.0', 'YYYY-MM-DD HH24:MI:SS.FF') )
		//		FETCH FIRST 10 ROWS ONLY;
		
		System.out.println("QUERRY CALIENTE!!");
		
		String sql = "SELECT hab.num_hab, hab.cuenta_habitacion, hab.tipo_habitacion ";
		sql += "FROM HABITACIONES hab LEFT JOIN Reservas res ON hab.num_hab = res.ID_HABITACION ";
		sql += "WHERE hab.tipo_habitacion = " +tipo+ " AND ";
		sql += "hab.num_hab NOT IN (SELECT id_habitacion FROM RESERVAS ";
		sql += "WHERE RESERVAS.ENTRADA BETWEEN " + entradaTS + " AND " + salidaTS + " AND ";
		sql += "RESERVAS.SALIDA BETWEEN " + entradaTS + " AND " + salidaTS + " )";
		sql += "FETCH FIRST " + cantidad + " ROWS ONLY";
		System.out.println(sql);
		Query q = pm.newQuery(SQL, sql);
		return (List<Object>) q.executeList();
	}


	public void moverUsuario(PersistenceManager pm, long numHab, double nuevaCuenta, long tipoHab) {
		String sql = "UPDATE HABITACIONES ";
		sql += "SET CUENTA_HABITACION = "+nuevaCuenta + " ";
		sql += "WHERE num_hab = "+numHab; 
		Query q = pm.newQuery(SQL, sql);
		System.out.println(sql);
		q.executeUnique();
	}


	public void agregarConsumoHabitacion(PersistenceManager pm, long idHab,
			double consumo) {
		String sql = "UPDATE HABITACIONES ";
		sql += "SET CUENTA_HABITACION = "+consumo + " ";
		sql += "WHERE num_hab = "+idHab; 
		Query q = pm.newQuery(SQL, sql);
		q.executeUnique();
	}
}