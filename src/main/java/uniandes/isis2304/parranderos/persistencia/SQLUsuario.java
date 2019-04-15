package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Usuario;

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
			String nombre, String apellido, long tipo_usuario) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaUsuario() + "(num_identidad, tipo_documento, nombre, apellido, tipo_usuario) values (?, ?, ?, ?, ?)");
        q.setParameters(num_identidad, tipo_documento, nombre, apellido, tipo_usuario);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un Usuario de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param num_identidad - El numero de identidad del usuario	 
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarUsuarioPorId (PersistenceManager pm, long num_identidad, String tipo_doc)
	{
       Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaUsuario() + " WHERE num_identidad = ? AND tipo_documento = ?");
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
	public Usuario darUsuarioPorId (PersistenceManager pm, long num_identidad, String tipoDoc) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaUsuario () + " WHERE num_identidad = ? AND tipo_documento = ?");
		q.setResultClass(Usuario.class);
		q.setParameters(num_identidad, tipoDoc);
		return (Usuario) q.executeUnique();
	}
	
	public List<Usuario> darUsuarios(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaUsuario());
		q.setResultClass(Usuario.class);
		return (List<Usuario>) q.executeList();
	}
}
