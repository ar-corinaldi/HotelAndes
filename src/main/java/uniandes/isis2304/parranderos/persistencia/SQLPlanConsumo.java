package uniandes.isis2304.parranderos.persistencia;

import java.sql.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.PlanConsumo;


public class SQLPlanConsumo {
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
	public SQLPlanConsumo (PersistenciaHotelAndes ph)
	{
		this.ph = ph;
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un Plan de consumo a la base de datos de HotelAndes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del Plan de consumo
	 * @param descripcion - Descripcion del Plan de consumo
	 * @param descuento - El descuento del Plan de consumo
	 * @param fecha_inicio - La  fecha de inicio del Plan de consumo
	 * @param fecha_final - La fecha final del Plan de consumo
	 * @param tipo_plan_consumo - El tipo del Plan de consumo
	 * @param id_reserva - El id de la reserva del Plan de consumo
	 * @param id_hotel - El id del hotel del Plan de consumo
	 * @return El número de tuplas insertadas
	 */
	public long adicionarPlanConsumo (PersistenceManager pm, long id,	String descripcion, double descuento,	Date fecha_inicio,
			Date fecha_final, long tipo_plan_consumo, long id_reserva ) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + ph.darTablaPlanConsumo() + "(id, descripcion, descuento, fecha_inicio, fecha_final, tipo_plan_consumo, id_reserva) values (?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id,	descripcion, descuento,	fecha_inicio, fecha_final, tipo_plan_consumo, id_reserva);
        return (long) q.executeUnique();
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar un Plan de Consumo de la base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del Plan de Consumo
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPlanConsumoPorId (PersistenceManager pm, long id)
	{
       Query q = pm.newQuery(SQL, "DELETE FROM " + ph.darTablaPlanConsumo() + " WHERE id = ?");
       q.setParameters(id);
       return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN Plan de Consumo de la 
	 * base de datos de HotelAndes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del Plan Consumo
	 * @return El objeto PlanConsumo que tiene el identificador dado
	 */
	public PlanConsumo darPlanConsumoPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + ph.darTablaPlanConsumo () + " WHERE id = ?");
		q.setResultClass(PlanConsumo.class);
		q.setParameters(id);
		return (PlanConsumo) q.executeUnique();
	}
}
