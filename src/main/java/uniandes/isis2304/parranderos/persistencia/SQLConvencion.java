package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Convencion;
import uniandes.isis2304.parranderos.negocio.Habitaciones;
import uniandes.isis2304.parranderos.negocio.Usuarios;

public class SQLConvencion {
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
	public SQLConvencion(PersistenciaHotelAndes ph) 
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
	public long adicionarConvencion(PersistenceManager pm, long id  , String nombre,  int cantidadPersonas,  long idPlanConsumo, long id_organizador, String tipo_documento)
	{
		Query q = pm.newQuery(SQL, "INSERT INTO "+ "CONVENCIONES" + "(id, nombre, cantidad_personas, "
				+ "id_plan_consumo, id_usuario, tipo_documento_usuario) values("+ id+", '"
				+ nombre+"', "
				+ cantidadPersonas+", "
				+ idPlanConsumo+", "
				+ id_organizador+", '"
				+ tipo_documento+"')");
		return (long)q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar una Habitacion de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idHotel - El identificador del hotel
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminaConvencionPorId (PersistenceManager pm, long idConvencion)
	{
       Query q = pm.newQuery(SQL, "DELETE FROM " +"CONVENCIONES" + " WHERE id = " + idConvencion);
       return (long) q.executeUnique();
	}
	

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UNA Habitacion de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idHabitacion - El identificador de la Habitacion
	 * @return El objeto Habitacion que tiene el identificador dado
	 */
	public Object darConvencionPorId (PersistenceManager pm, long idConvencion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + "CONVENCIONES" + " WHERE id = " + idConvencion);
		q.setResultClass(Convencion.class);
		Object o = q.execute();
		return  o;
	}
	
	public List<Convencion> darHabitaciones(PersistenceManager pm){
		Query q = pm.newQuery(SQL, "SELECT * FROM " +"CONVENCIONES");
		q.setResultClass(Convencion.class);
		return (List<Convencion>) q.executeList();
	}

}
