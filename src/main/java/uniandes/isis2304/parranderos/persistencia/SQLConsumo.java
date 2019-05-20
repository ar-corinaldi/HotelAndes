package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Consumo;
import uniandes.isis2304.parranderos.negocio.Usuarios;


public class SQLConsumo {

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
	public SQLConsumo(PersistenciaHotelAndes pp) {
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un Consumo a la base de datos de HotelAndes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del Consumo
	 * @param fecha - La fecha  del  Consumo
	 * @param id_usuario - El identificador del usuario que hace el consumo
	 * @param id_servcio - El identificador del Servicio al que pertenece el Consumo
	 * @param id_habitacion - El id de la habitacion del  Consumo
	 * @return El n�mero de tuplas insertadas
	 */
	public long adicionarConsumo (PersistenceManager pm, long id, Timestamp fecha, long id_usuario, 
			String tipo_documento_usuario,long id_producto, long id_habitacion){
		String fechaTS = "TO_TIMESTAMP('"+fecha.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
//		verificarCheckIn
		Query q = pm.newQuery(SQL, "INSERT INTO " +  "CONSUMOS" + "(id, fecha, id_usuario, tipo_documento_usuario, id_producto, id_habitacion) values ("+ id +", "
				+ fechaTS+", "
				+ id_usuario +", '"
				+ tipo_documento_usuario+"', "
				+ id_producto+", "
				+ id_habitacion+")");
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN Consumo de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idConsumo - El identificador del Consumo
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarConsumoPorId (PersistenceManager pm, long idConsumo)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaConsumo() + " WHERE id = ?");
		q.setParameters(idConsumo);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UN Consumo de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idConsumo - El identificador del bar
	 * @return El objeto Consumo que tiene el identificador dado
	 */
	public Consumo darConsumoPorId (PersistenceManager pm, long idConsumo) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaConsumo () + " WHERE id = ?");
		q.setResultClass(Consumo.class);
		q.setParameters(idConsumo);
		return (Consumo) q.executeUnique();
	}

	public List<Consumo> darConsumos(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaConsumo());
		q.setResultClass(Consumo.class);
		return (List<Consumo>) q.executeList();
	}

	public List<Consumo> darConsumosXUsuario(PersistenceManager pm,
			Consumo con, Usuarios user) {
		//SELECT * FROM CONSUMOS WHERE id_usuario = 1010247782 AND tipo_documento_usuario = 'cedula'
		String sql = "SELECT * "
				+ "FROM CONSUMOS "
				+ "WHERE id_usuario = "+user.getNum_identidad()+ " AND tipo_documento_usuario = '"+user.getTipo_documento() +"'";

		Query q = pm.newQuery(SQL, sql);
		System.out.println(sql);
		return q.executeList();
	}

	public List<Object> buscarBuenosClientes(PersistenceManager pm) {



		String sql = "Select cons.id_usuario "
				+ "from consumos cons inner join productos  prod  on prod.id = cons.id_producto "
				+ "where prod.precio >97000 "
				+ "group by cons.id_usuario";

		Query q = pm.newQuery(SQL, sql);
		System.out.println(sql);
		List<Object> list= (List<Object>) q.executeList();
		return list;

	}

	public long darUltimoId(PersistenceManager pm) {
		String sql = "SELECT MAX(id) FROM CONSUMOS";
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


	public List<Object> reqFC11(PersistenceManager pm, String maxOMin) throws Exception {
		String sql = darSQLReqFC11(maxOMin);
		System.out.println(sql);
		List<Object> listObject = new LinkedList<Object>();
		try{
			Query q = pm.newQuery(SQL, sql);
			listObject = q.executeList();
		}
		catch( Exception e ){
			throw new Exception("Error al cargar las reservas "+maxOMin);
		}
		
		return listObject;
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
		
		
		String sql = "SELECT x.semana , x.servicio_"+ maxOMin +"_consumido, y.servicio as id_servicio_"+maxOMin+" ";
        sql += "FROM ( ";
        sql += "        SELECT A.semana, "+ maxOMin +"(A.cantidad_servicio) as servicio_" + maxOMin + "_consumido ";
        sql += "FROM ( ";
        sql += "SELECT to_char(fecha,'ww') as semana, COUNT(p.id_servicio) as cantidad_servicio, p.id_servicio as servicio ";
        sql += "FROM consumos c INNER JOIN productos p ON c.id_producto = p.id ";
        sql += "WHERE fecha BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') ";
        sql += "GROUP BY to_char(fecha,'ww'), p.id_servicio ";
        sql += ") A "; 
        sql += "GROUP BY A.semana ";
        sql += ") X ";
        sql += "INNER JOIN ( ";
        sql += "SELECT to_char(fecha,'ww') as semana, COUNT(p.id_servicio) as cantidad_servicio, p.id_servicio as servicio ";
        sql += "FROM consumos c INNER JOIN productos p ON c.id_producto = p.id ";
        sql += "WHERE fecha BETWEEN to_date('20190101', 'YYYYMMDD') AND to_date('20191231', 'YYYYMMDD') ";
        sql += "GROUP BY to_char(fecha,'ww'), p.id_servicio ) Y ON X.SERVICIO_"+ maxOMin +"_CONSUMIDO = Y.cantidad_servicio ";
        sql += "ORDER BY x.semana asc";
		
		return sql;
	}

	public List<Object> buscarBuenosClientesPorReservasServicio(PersistenceManager pm) {
		// TODO Auto-generated method stub
		String sql = "Select resser.id_usuario "
				+ "from servicios ser "
				+ "inner join reservas_Servicios resser "
				+ "on ser.id= resser.id_servicio "
				+ "where  (extract (hour from (resser.fecha_inicial - resser.fecha_final)))*-1 >4 "
				+ "and ser.tipo_servicios= 8 or ser.tipo_servicios=11";
		Query q = pm.newQuery(SQL, sql);
		System.out.println(sql);
		List<Object> list= (List<Object>) q.executeList();
		return list;
	}

}