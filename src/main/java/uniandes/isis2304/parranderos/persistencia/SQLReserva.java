package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Reservas;


public class SQLReserva {
	
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
	public SQLReserva(PersistenciaHotelAndes pp) {
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
	public long adicionarReserva (PersistenceManager pm, long id, int numPersonas,Timestamp entrada,Timestamp salida, Timestamp checkIn, Timestamp checkOut, long idUsuario, String tipoDoc, long id_habitacion, long id_plan_consumo) 
	{
		// TO_TIMESTAMP('2019-09-19 12:00:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
		String entradaTS = "TO_TIMESTAMP('"+entrada.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
		String salidaTS = "TO_TIMESTAMP('"+salida.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";

        Query q = pm.newQuery(SQL, "INSERT INTO " + "RESERVAS" + "(id, num_personas, entrada, salida, check_in, check_out, id_usuario, tipo_documento_usuario, id_habitacion, id_plan_consumo) values ("+id +", "
        		+ numPersonas+", "
        		+ entradaTS+", "
        		+ salidaTS+", "
        		+ checkIn+", "
        		+ checkOut+", "
        		+ idUsuario+", '"
        		+ tipoDoc+"', "
        		+ id_habitacion+", "
        		+ id_plan_consumo+")");
        
        System.out.println("INSERT INTO " + "RESERVAS" + "(id, num_personas, entrada, salida, check_in, check_out, id_usuario, tipo_documento_usuario, id_habitacion, id_plan_consumo) values ("+id +", "
        		+ numPersonas+", "
        		+ entradaTS+", "
        		+ salidaTS+", "
        		+ checkIn+", "
        		+ checkOut+", "
        		+ idUsuario+", '"
        		+ tipoDoc+"', "
        		+ id_habitacion+", "
        		+ id_plan_consumo+")");
        
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UNA Reserva de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReserva - El identificador del Reserva
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarReservaPorId (PersistenceManager pm, long idReserva)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + "RESERVAS"+ " WHERE id = "
        		+ idReserva);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UNA Reserva de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idReserva - El identificador del bar
	 * @return El objeto Reserva que tiene el identificador dado
	 */
	public Reservas darReservaPorId (PersistenceManager pm, long idReserva) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + "RESERVAS" + " WHERE id = "+idReserva);
		System.out.println("SELECT * FROM " + "RESERVAS" + " WHERE id = "+idReserva);
		q.setResultClass(Reservas.class);
		Object o = null;
		o = q.executeUnique();
		return (Reservas) o;
	}
	
	public List<Reservas> darReservas(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM  RESERVAS");
		q.setResultClass(Reservas.class);
		return (List<Reservas>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS BEBEDORES Y DE SUS VISITAS REALIZADAS de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idBebedor - El identificador del bebedor
	 * @return Una lista de arreglos de objetos, de tama�o 7. Los elementos del arreglo corresponden a los datos de 
	 * los bares visitados y los datos propios de la visita:
	 * 		(id, nombre, ciudad, presupuesto, cantsedes) de los bares y (fechavisita, horario) de las visitas
	 */
	public List<Object []> darReservaUsuarios(PersistenceManager pm, long id)
	{
        String sql = "SELECT user.num_identidad, user.tipo_documento, user.nombre, user.apellido, user.correo, user.id_hotel";
        sql += " FROM ";
        sql += pp.darTablaUsuario () + " user, ";
        sql += pp.darTablaReserva () + " res, ";
       	sql	+= " WHERE ";
       	sql += "res.id = ?";
       	sql += " AND res.id = user.id_reserva";
		Query q = pm.newQuery(SQL, sql);
		q.setParameters(id);
		return q.executeList();
	}
	
	
	public long eliminarReservasUsuario(PersistenceManager pm, Long idUsuario, String tipoDocumento) {
		String sql = "DELETE from Reservas where ID_USUARIO = " +idUsuario + " AND TIPO_DOCUMENTO_USUARIO = '"+ tipoDocumento+ "'" ;
		Query q =pm.newQuery(SQL, sql);
		return  (long) q.execute();
	}

	public long terminarMantenimiento(PersistenceManager pm, Long num_identidad, String tipo_documento,
			int numHab) {
		String sql = "DELETE from Reservas where ID_USUARIO = " +num_identidad + " AND TIPO_DOCUMENTO_USUARIO = '"+ tipo_documento+ 
				"' AND ID_HABITACION = "+ numHab ;
		Query q =pm.newQuery(SQL, sql);
		return  (long) q.execute();
		
	}
}