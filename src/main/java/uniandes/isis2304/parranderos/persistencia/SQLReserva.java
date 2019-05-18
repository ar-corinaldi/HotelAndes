package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Reservas;
import uniandes.isis2304.parranderos.negocio.Usuarios;


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
	public long adicionarReserva (PersistenceManager pm, long id, int numPersonas,Timestamp entrada,Timestamp salida, Timestamp checkIn, Timestamp checkOut, long idUsuario, String tipoDoc, long id_habitacion, long id_plan_consumo) throws Exception 
	{
		// TO_TIMESTAMP('2019-09-19 12:00:00.0', 'YYYY-MM-DD HH24:MI:SS.FF')
		String entradaTS = "TO_TIMESTAMP('"+entrada.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
		String salidaTS = "TO_TIMESTAMP('"+salida.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
		String checkInTS = null;
		if( checkIn != null ) checkInTS = "TO_TIMESTAMP('"+checkIn.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
		String checkOutTS = null;
		if( checkOut != null ) checkOutTS = "TO_TIMESTAMP('"+checkOut.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
		String sql = "INSERT INTO RESERVAS " + "(id, num_personas, entrada, salida, check_in, check_out, id_usuario, tipo_documento_usuario, id_habitacion, id_plan_consumo) values ("+id +", "
				+ numPersonas+", "
				+ entradaTS+", "
				+ salidaTS+", "
				+ checkInTS+", "
				+ checkOutTS+", "
				+ idUsuario+", '"
				+ tipoDoc+"', "
				+ id_habitacion+", "
				+ id_plan_consumo+")";
		System.out.println(sql);

		boolean r = verificarReservaExistente( pm,entradaTS, salidaTS,  id_habitacion);
		if( r )	{
			System.err.println("PING");
			Query q = pm.newQuery(SQL, sql);
			System.err.println("PING2");
			long var = (long) q.executeUnique();
			System.err.println("PING3");
			return var;
		} 
		else
			throw new Exception("No hay habitaciones del tipo solicitado para la fecha");

	}

	private boolean verificarReservaExistente(PersistenceManager pm, String entradaTS, String salidaTS, long idHab) {
		String sql = "SELECT RESERVAS.ID FROM RESERVAS ";
		sql += "WHERE RESERVAS.ENTRADA BETWEEN "+ entradaTS +" AND "+ salidaTS + " AND ";
		sql += "RESERVAS.SALIDA BETWEEN "+ entradaTS + " AND " + salidaTS + " AND ";
		sql += "ID_HABITACION = "+idHab;

		System.out.println(sql);
		Query q = pm.newQuery(SQL, sql);
		Object o = q.executeUnique();
		boolean b = o == null ?  true :  false;
		System.out.println(b);
		return b;
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

	public List<Object> darHabitacionesDisponibles(PersistenceManager pm, int cantidad, long tipoHab, Timestamp entrada, Timestamp salida){
		//		SELECT hab.num_hab, hab.cuenta_habitacion, hab.tipo_habitacion
		//		FROM RESERVAS, HABITACIONES HAB
		//		WHERE entrada NOT BETWEEN TO_TIMESTAMP('2019-09-16 06:00:00.0', 'YYYY-MM-DD HH24:MI:SS.FF') AND TO_TIMESTAMP('2019-09-23 12:00:00.0', 'YYYY-MM-DD HH24:MI:SS.FF') AND hab.tipo_habitacion = 3;
		String entradaTS = "TO_TIMESTAMP('"+entrada.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
		String salidaTS = "TO_TIMESTAMP('"+salida.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
		String sql =  "SELECT hab.num_hab, hab.cuenta_habitacion, hab.tipo_habitacion ";
		sql += "FROM  HABITACIONES hab ";
		sql += "WHERE hab.tipo_habitacion = "+tipoHab+ " ";
		sql += "FETCH FIRST "+cantidad+ " ROWS ONLY";
		Query q = pm.newQuery(SQL, sql);
		System.out.println(sql);
		return (List<Object>) q.executeList();
	}

	public Reservas darReservaXFechasYNumHab(
			PersistenceManager pm, Timestamp entrada,
			Timestamp salida, long numHab) {
		String entradaTS = "TO_TIMESTAMP('"+entrada.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
		String salidaTS = "TO_TIMESTAMP('"+salida.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
		String sql =  "SELECT * ";
		sql += "FROM  RESERVAS ";
		sql += "WHERE RESERVAS.entrada >= "+entradaTS+ " AND RESERVAS.salida <= "+salidaTS+" AND RESERVAS.id_habitacion = "+numHab+ " ";
		sql += "FETCH FIRST 1 ROW ONLY";
		Query q = pm.newQuery(SQL, sql);
		Object o =q.executeUnique();
		Object[] datos = (Object[]) o;
		Reservas res = null;
		if( o!=null ){
			long id = ((BigDecimal) datos[0]).longValue();
			int numPersonas = ((BigDecimal) datos[1]).intValue();
			Timestamp checkIn= null;
			if( datos[4] != null ) checkIn = Timestamp.valueOf(datos[4].toString());
			Timestamp checkOut= null;
			if( datos[5] != null ) checkOut = Timestamp.valueOf(datos[5].toString());
			long pUsuario = ((BigDecimal) datos[6]).longValue();
			String tipoDoc = datos[7].toString();
			long idPlanCons= ((BigDecimal) datos[9]).longValue();
			res= new Reservas(id, numPersonas, entrada, salida, checkIn, checkOut, pUsuario, tipoDoc, numHab, idPlanCons);
		}
		return res;
	}

	public long terminarMantenimiento(PersistenceManager pm, Long num_identidad, String tipo_documento,
			int numHab) {
		String sql = "DELETE from Reservas where ID_USUARIO = " +num_identidad + " AND TIPO_DOCUMENTO_USUARIO = '"+ tipo_documento+ 
				"' AND ID_HABITACION = "+ numHab ;
		Query q =pm.newQuery(SQL, sql);
		return  (long) q.execute();

	}

	public void registrarLlegadaReserva(PersistenceManager pm, long idUsuario, String tipoDoc, Timestamp llegada, long idRes) {
		String llegadaTS = "TO_TIMESTAMP('"+llegada.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";

		String sql = "UPDATE RESERVAS ";
		sql += "SET check_in = "+llegadaTS+" ";
		sql += "WHERE id = "+idRes+" AND id_usuario = "+idUsuario+ " AND tipo_documento_usuario = '"+tipoDoc+"'";
		System.out.println(sql);
		Query q = pm.newQuery(SQL, sql);
		q.executeUnique();
	}

	public void registrarSalidaReserva(PersistenceManager pm, long idUsuario, String tipoDoc, Timestamp salida, long idRes) throws Exception {
		String salidaTS = "TO_TIMESTAMP('"+salida.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";

		boolean b = verificaCheckIn(pm, idRes, idUsuario, tipoDoc);

		String sql = "UPDATE RESERVAS ";
		sql += "SET check_out = "+salidaTS+" ";
		sql += "WHERE id = "+idRes+" AND id_usuario = "+idUsuario+ " AND tipo_documento_usuario = '"+tipoDoc+"' AND check_in IS NOT null";
		System.out.println(sql);
		if( b )	{
			Query q = pm.newQuery(SQL, sql);
			q.executeUnique();
		} 
		else
			throw new Exception("El usuario no ha hecho el check in o el usuario no se encuentra en la base de datos o la reserva no se encuentra");
	}

	private boolean verificaCheckIn(PersistenceManager pm, long idRes,
			long idUsuario, String tipoDoc) {
		String sql = "SELECT ID FROM RESERVAS ";
		sql += "WHERE id = "+ idRes +" AND id_usuario = "+ idUsuario + " AND ";
		sql += "tipo_documento_usuario = '"+ tipoDoc + "' AND ";
		sql += "check_in IS NOT NULL";

		System.out.println(sql);
		Query q = pm.newQuery(SQL, sql);
		Object o = q.executeUnique();
		boolean b = o == null ?  false :  true;
		System.out.println(b);
		return b;
	}

	public long darUltimoId(PersistenceManager pm) {
		String sql = "SELECT MAX(id) FROM RESERVAS";
		try{
			Query q = pm.newQuery(SQL, sql);
			Object o = q.executeUnique();
			long id = ((BigDecimal) o).longValue();
			return id+1;
		}
		catch( Exception e ){
			System.out.println(e.getMessage());
			return 1;
		}
	}

	/**
	 * 
	 * @param pm
	 * @param tipoHab
	 * @param tipoTiempo
	 * @return Retorna en pos 0 cuantas fueron las reservas hechas de la semana mas visitada
	 * En pos 1 retorna la fecha de la cual se hicieron mas reservas
	 * En pos 2 retorna el numero de semana
	 * @throws Exception 
	 */
	public Object[] fechasMayorDemanda(PersistenceManager pm, int tipoHab, String tipoTiempo) throws Exception {
		//		SELECT COUNT(to_number(to_char(entrada,'WW'))) as contador, entrada as Semanas_Mayor_Demanda, to_number(to_char(entrada,'WW')) as Semana
		//		FROM RESERVAS
		//		GROUP BY to_number(to_char(entrada,'WW')), entrada;

		//TODO hacer lo mismo con el mes
		//TODO hacer lo mismo con el mes
		//TODO hacer lo mismo con el mes

		String sql = "SELECT COUNT(to_number(to_char(entrada,'" + tipoTiempo + "'))) as contador, entrada as Semanas_Mayor_Demanda, to_number(to_char(entrada,'" + tipoTiempo + "')) as Semana ";
		sql += "FROM RESERVAS ";
		//		sql += "WHERE ";
		sql += "GROUP BY to_number(to_char(entrada,'" + tipoTiempo + "')), entrada";
		System.out.println(sql);

		Object[] rta = new Object[3];
		Reservas res = null;
		List<Object> l;
		try{
			Query q = pm.newQuery(SQL, sql);

			l = q.executeList();
			Object[] datos = (Object[]) l.get(l.size()-1);
			rta[0] = ((BigDecimal) datos[0]).intValue();
			rta[1] = Timestamp.valueOf(datos[1].toString());
			rta[2] = ((BigDecimal) datos[2]).intValue();
			return rta;
		} 
		catch( Exception e ) {
			throw new Exception(e.getMessage());
		}
	}

	public List<Reservas> reqFC11(PersistenceManager pm, String maxOMin) throws Exception {

		String sql = darSQLReqFC11(maxOMin);
		System.out.println(sql);
		
		List<Reservas> list = new LinkedList<Reservas>();
		try{
			System.out.println("Sirve");
			Query q = pm.newQuery(SQL, sql);
			System.out.println("No sirve");
			List<Object> listObject = q.executeList();
			System.out.println("Sirve");
			for (Object o : listObject) {
				Object[] datos = (Object[]) o;
				String semana = datos[0].toString();
				String tipoDoc = (String) datos[1];
				String numHabMayor = (String) datos[2];
				long numHab = ((BigDecimal)datos[3]).longValue();
				double cuentaHab = ((BigDecimal)datos[4]).doubleValue();
				long tipoHab = ((BigDecimal)datos[5]).longValue();
				//TODO	revisar sql para que de reserva
//				list.add( new Reserva);
			}
		}
		catch( Exception e ){
			e.printStackTrace();
			throw new Exception("Error en el SQL\n" + e.getMessage());
		}

		return list;
	}
	
	private String darSQLReqFC11(String maxOMin){
//		SELECT *
//		FROM (SELECT DISTINCT x.semana , x.habitaciones_mas_solicitadas, y.numero_habitacion as num_hab_mayor
//				FROM (
//				        SELECT A.semana, MAX(A.cantidad_habitacion) as habitaciones_mas_solicitadas
//				        FROM (
//				                SELECT to_char(entrada,'ww') as semana, COUNT(h.num_hab) as cantidad_habitacion, h.num_hab as numero_habitacion
//				                FROM reservas r INNER JOIN habitaciones h ON r.id_habitacion = h.num_hab
//				                WHERE r.entrada BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') 
//				                GROUP BY to_char(entrada,'ww'), h.num_hab
//				            ) A
//				        GROUP BY A.semana
//				        ) X
//				INNER JOIN (
//				                SELECT to_char(entrada,'ww') as semana, COUNT(h.num_hab) as cantidad_habitacion, h.num_hab as numero_habitacion
//				                FROM reservas r INNER JOIN habitaciones h ON r.id_habitacion = h.num_hab
//				                WHERE r.entrada BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') 
//				                GROUP BY to_char(entrada,'ww'), h.num_hab
//				            ) Y 
//				ON X.habitaciones_mas_solicitadas = Y.cantidad_habitacion) Z LEFT JOIN HABITACIONES H ON z.num_hab_mayor = H.NUM_HAB;
		
		
		String sql = "SELECT * ";
		sql += "FROM (SELECT DISTINCT x.semana , x.habitaciones_mas_solicitadas, y.numero_habitacion as num_hab_mayor ";
        sql += "FROM ( ";
        sql += "SELECT A.semana, MAX(A.cantidad_habitacion) as habitaciones_mas_solicitadas ";
        sql += "FROM ( ";
        sql += "SELECT to_char(entrada,'ww') as semana, COUNT(h.num_hab) as cantidad_habitacion, h.num_hab as numero_habitacion ";
        sql += "FROM reservas r INNER JOIN habitaciones h ON r.id_habitacion = h.num_hab ";
        sql += "WHERE r.entrada BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') ";
        sql += "GROUP BY to_char(entrada,'ww'), h.num_hab";
        sql += ") A "; 
        sql += "GROUP BY A.semana ";
        sql += ") X ";
        sql += "INNER JOIN ( ";
        sql += "SELECT to_char(entrada,'ww') as semana, COUNT(h.num_hab) as cantidad_habitacion, h.num_hab as numero_habitacion ";
        sql += "FROM reservas r INNER JOIN habitaciones h ON r.id_habitacion = h.num_hab ";
        sql += "WHERE r.entrada BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') ";
        sql += "GROUP BY to_char(entrada,'ww'), h.num_hab ";
        sql += ") Y "; 
        sql += "ON X.habitaciones_mas_solicitadas = Y.cantidad_habitacion) Z LEFT JOIN HABITACIONES H ON z.num_hab_mayor = H.NUM_HAB " ;
		
		return sql;
	}
}