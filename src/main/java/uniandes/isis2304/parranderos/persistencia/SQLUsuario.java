package uniandes.isis2304.parranderos.persistencia;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.swing.JOptionPane;

import uniandes.isis2304.parranderos.interfazApp.InterfazHotelAndesApp;
import uniandes.isis2304.parranderos.negocio.Habitaciones;
import uniandes.isis2304.parranderos.negocio.Reservas;
import uniandes.isis2304.parranderos.negocio.Usuarios;

public class SQLUsuario {
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaHotelAndes.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaHotelAndes ph;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLUsuario (PersistenciaHotelAndes ph)
	{
		this.ph = ph;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un Usuario a la base de datos de HotelAndes
	 * @param pm - El manejador de persistencia
	 * @param num_identidad - El numero de identidad del usuario
	 * @param tipo_documento - El tipo de documento del usuario
	 * @param nombre - El nombre del usuario
	 * @param apellido - El apellido del usuario
	 * @param correo - El correo del usuario
	 * @param tipo_usuario - El tipo de usuario del usuario
	 * @param reserva - La reserva del usuario
	 * @return El número de tuplas insertadas
	 */
	public long adicionarUsuario (PersistenceManager pm, long num_identidad,  String tipo_documento,
			String nombre, String apellido, long tipo_usuario, long id_convencion) throws Exception
	{
		Object o;
		if( id_convencion==-1 ){

			Query q = pm.newQuery(SQL, "INSERT INTO " + "USUARIOS"+ "(num_identidad, tipo_documento, nombre, apellido, tipo_usuario, id_convencion) values ("+ num_identidad +", '"+tipo_documento +"', '"
					+ nombre 	+ "', '"
					+ apellido 	+ "', "
					+ tipo_usuario + ", "
					+ null+")");
			return (long) q.executeUnique();
		}
		else{
			Query q = pm.newQuery(SQL, "INSERT INTO " + "USUARIOS"+ "(num_identidad, tipo_documento, nombre, apellido, tipo_usuario, id_convencion) values ("+ num_identidad +", '"+tipo_documento +"', '"
					+ nombre 	+ "', '"
					+ apellido 	+ "', "
					+ tipo_usuario + ", "
					+ id_convencion+")");
			return (long) q.executeUnique();
		}


	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un Usuario de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param num_identidad - El numero de identidad del usuario	 
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarUsuarioPorId (PersistenceManager pm, long num_identidad, String tipo_documento)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " +"USUARIOS" + " WHERE num_identidad = "+num_identidad 
				+ " AND tipo_documento = '"+tipo_documento+"'");
		q.setParameters(num_identidad);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN Usuario de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param num_identidad - El numero de identidad del usuario	 
	 * @return El objeto Usuario que tiene el identificador dado
	 */
	public Object darUsuarioPorId (PersistenceManager pm, long num_identidad, String tipo_documento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + "USUARIOS" + " WHERE num_identidad = "+num_identidad 
				+ " AND tipo_documento = '"+tipo_documento+"'");
		Object o = q.executeUnique();
		return (Object) o;
	}

	public List<Usuarios> darUsuarios(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + "USUARIOS");
		q.setResultClass(Usuarios.class);
		return (List<Usuarios>) q.executeList();
	}
	
	public List<Usuarios> darUsuariosConvencion(PersistenceManager pm, Long idConvencion) {
		String sql = "SELECT * FROM USUARIOS WHERE ID_CONVENCION = " + idConvencion;
		Query q = pm.newQuery(SQL, sql);
		System.out.println(sql);
		List<Object> list= (List<Object>) q.executeList();
		List<Usuarios> listU = new LinkedList<Usuarios>();
		for (Object o : list) {
			Object[] datos = (Object[]) o;
			Usuarios user= null;
			if( o!=null ){
				long numIden = ((BigDecimal) datos[0]).longValue();
				String tipoDoc = datos[1].toString();
				String nombre= datos[2].toString();
				String apellido = datos[3].toString();

				long tipoUsuario= ((BigDecimal) datos[4]).longValue();
				long idConven= ((BigDecimal) datos[5]).longValue();

				user = new Usuarios(numIden, tipoDoc, nombre, apellido, tipoUsuario, idConven);
				System.out.println(user);
				listU.add(user);
			}
		}
		
		System.out.println("Sirven las listas?: "+listU);
		return listU;
	}

		public Object indiceUltimoUsuario(PersistenceManager pm){
//			SELECT max(num_identidad)
//			FROM USUARIOS 
//			WHERE num_identidad <= 1000
			String sql = "SELECT MAX(num_identidad) FROM USUARIOS WHERE num_identidad < 998";
			Query q = pm.newQuery(SQL, sql);
			Object o = q.executeUnique();
			
			return o;
		}

		public List<Usuarios> reqFC9(PersistenceManager pm, String servicio, Timestamp entrada, Timestamp salida, boolean[] tipoClasificacion,
				boolean[] tipoOrdenamiento) throws Exception {
			
			String entradaTS = "TO_TIMESTAMP('"+entrada.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
			String salidaTS = "TO_TIMESTAMP('"+salida.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
			String orderBy = "";
			
			if(tipoClasificacion[1] && (tipoOrdenamiento[0] || tipoOrdenamiento[1] || tipoOrdenamiento[2]) ){
				orderBy = "ORDER BY ";
				if( tipoOrdenamiento[0] ){
					orderBy += "u.num_identidad asc ";
				}
				if( tipoOrdenamiento[1] ){
					orderBy += "";
				}
				if( tipoOrdenamiento[2] ){
					orderBy += "";
				}
			}
//			SELECT u.*
//			FROM (SELECT c.ID_USUARIO, c.TIPO_DOCUMENTO_USUARIO
//			FROM Servicios s, Productos p, Consumos c
//			WHERE s.id = p.id_servicio AND p.id = c.id_producto AND s.tipo_servicios = 4 AND 
//			c.fecha BETWEEN TO_TIMESTAMP('2019-03-15 00:00:00.0', 'YYYY-MM-DD HH24:MI:SS.FF') AND TO_TIMESTAMP('2019-05-18 23:59:59.0', 'YYYY-MM-DD HH24:MI:SS.FF') ) A 
//			INNER JOIN Usuarios u ON A.ID_USUARIO = u.NUM_IDENTIDAD AND A.TIPO_DOCUMENTO_USUARIO = u.TIPO_DOCUMENTO
			
			String sql = "SELECT u.* ";
			sql += "FROM 	(SELECT c.ID_USUARIO, c.TIPO_DOCUMENTO_USUARIO ";
			sql += 			"FROM Servicios s, Productos p, Consumos c ";
			sql += 			"WHERE s.id = p.id_servicio AND p.id = c.id_producto AND s.tipo_servicios = " + servicio + " AND ";
			sql += 			"c.fecha BETWEEN " + entradaTS + " AND " + salidaTS +" ) A ";
			sql += "INNER JOIN Usuarios u ON A.ID_USUARIO = u.NUM_IDENTIDAD AND A.TIPO_DOCUMENTO_USUARIO = u.TIPO_DOCUMENTO ";
			sql += orderBy;
			
			System.out.println(sql);
			
			List<Usuarios> list = new LinkedList<Usuarios>();
			try{
				Query q = pm.newQuery(SQL, sql);
				List<Object> listObject = q.executeList();
				for (Object o : listObject) {
					Object[] datos = (Object[]) o;
					String numIden = datos[0].toString();
					String tipoDoc = (String) datos[1];
					String nombre = (String) datos[2];
					String apellido = (String) datos[3];
					String tipoUsuario = datos[4].toString();
					String tipoConvencion = datos[5].toString();
					list.add( new Usuarios(numIden, tipoDoc, nombre, apellido, tipoUsuario, tipoConvencion));
				}
			}
			catch( Exception e ){
				e.printStackTrace();
				throw new Exception("Error en el SQL\n" + e.getMessage());
			}
			
			return list;
		}

		public List<Object> reqFC10(PersistenceManager pm, String servicio, Timestamp entrada, Timestamp salida, boolean[] tipoClasificacion,
				boolean[] tipoOrdenamiento) {
			

			String entradaTS = "TO_TIMESTAMP('"+entrada.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
			String salidaTS = "TO_TIMESTAMP('"+salida.toString()+"', 'YYYY-MM-DD HH24:MI:SS.FF')";
			
			String sql = "Select us.num_Identidad "
					+ " from usuarios us "
					+ "full outer join  (Select * "
					+ "from consumos con "
					+ "inner join productos pro on "
					+ "pro.id = con.id_producto "
					+ "where pro.id_servicio = "+ servicio
					+ " and con.fecha BETWEEN "+ entradaTS 
					+ " AND "+ salidaTS +")prods "
					+ "on prods.id_Usuario = us.num_identidad "
					+ "where  prods.id_Habitacion is null ";	
			if(tipoClasificacion[0])
			{
				sql += "group by us.num_Identidad ";
			}
			if(tipoClasificacion[1])
			{
				sql += "order by us.num_Identidad ";
			}
			
			Query q = pm.newQuery(SQL, sql);
			System.out.println(sql);
			List<Object> list= (List<Object>) q.executeList();
			return list;
		}
}
