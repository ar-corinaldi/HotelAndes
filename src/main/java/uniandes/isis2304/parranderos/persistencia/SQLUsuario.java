package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.swing.JOptionPane;

import uniandes.isis2304.parranderos.interfazApp.InterfazHotelAndesApp;
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
	public Usuarios darUsuarioPorId (PersistenceManager pm, long num_identidad, String tipo_documento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + "USUARIOS" + " WHERE num_identidad = "+num_identidad 
				+ " AND tipo_documento = '"+tipo_documento+"'");
		q.setResultClass(Usuarios.class);
		Object o = q.executeUnique();
		return (Usuarios) o;
	}

	public List<Usuarios> darUsuarios(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + "USUARIOS");
		q.setResultClass(Usuarios.class);
		return (List<Usuarios>) q.executeList();
	}
}
