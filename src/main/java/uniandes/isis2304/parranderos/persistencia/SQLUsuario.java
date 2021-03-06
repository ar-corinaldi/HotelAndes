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
}
