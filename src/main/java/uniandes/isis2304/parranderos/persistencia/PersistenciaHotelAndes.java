/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.persistencia;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;

import uniandes.isis2304.parranderos.negocio.Consumo;
import uniandes.isis2304.parranderos.negocio.Convencion;
import uniandes.isis2304.parranderos.negocio.Habitaciones;
import uniandes.isis2304.parranderos.negocio.PlanConsumo;
import uniandes.isis2304.parranderos.negocio.ReservaServicio;
import uniandes.isis2304.parranderos.negocio.Reservas;
import uniandes.isis2304.parranderos.negocio.Servicios;
import uniandes.isis2304.parranderos.negocio.TipoHabitacion;
import uniandes.isis2304.parranderos.negocio.TipoPlanConsumo;
import uniandes.isis2304.parranderos.negocio.TipoServicio;
import uniandes.isis2304.parranderos.negocio.TipoUsuario;
import uniandes.isis2304.parranderos.negocio.Usuarios;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Clase para el manejador de persistencia del proyecto Parranderos
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLBar, SQLBebedor, SQLBebida, SQLGustan, SQLSirven, SQLTipoBebida y SQLVisitan, que son 
 * las que realizan el acceso a la base de datos
 * 
 * @author Germán Bravo
 */
public class PersistenciaHotelAndes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaHotelAndes.class.getName());

	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaHotelAndes instance;

	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;

	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;


	/**
	 * Atributo para el acceso a la tabla Consumo de la base de datos
	 */
	private SQLConsumo sqlConsumo;

	/**
	 * Atributo para el acceso a la tabla Habitacion de la base de datos
	 */
	private SQLHabitacion sqlHabitacion;

	/**
	 * Atributo para el acceso a la tabla Hotel de la base de datos
	 */
	private SQLHotel sqlHotel;

	/**
	 * Atributo para el acceso a la tabla PlanConsumo de la base de datos
	 */
	private SQLPlanConsumo sqlPlanConsumo;

	/**
	 * Atributo para el acceso a la tabla Reserva de la base de datos
	 */
	private SQLReserva sqlReserva;

	/**
	 * Atributo para el acceso a la tabla Servicio de la base de datos
	 */
	private SQLServicio sqlServicio;

	/**
	 * Atributo para el acceso a la tabla TipoHabitacion de la base de datos
	 */
	private SQLTipoHabitacion sqlTipoHabitacion;

	/**
	 * Atributo para el acceso a la tabla TipoPlanConsumo de la base de datos
	 */
	private SQLTipoPlanConsumo sqlTipoPlanConsumo;

	/**
	 * Atributo para el acceso a la tabla TipoUsuario de la base de datos
	 */
	private SQLTipoUsuario sqlTipoUsuario;

	/**
	 * Atributo para el acceso a la tabla TipoServicio de la base de datos
	 */
	private SQLTipoServicio sqlTipoServicio;


	/**
	 * Atributo para el acceso a la tabla Usuario de la base de datos
	 */
	private SQLUsuario sqlUsuario;

	/**
	 * Atributo para el acceso a la tabla convencion de la base de datos
	 */
	private SQLConvencion sqlConvencion;

	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaHotelAndes
	 */
	private SQLUtil sqlUtil;


	private SQLReservaServicio sqlReservaServicio;

	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaHotelAndes ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Parranderos");		
		crearClasesSQL ();

		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("Hotel_sequence"); //No se que poner
		tablas.add ("CONSUMOS");
		tablas.add("CONVENCIONES");
		tablas.add ("HABITACIONES");
		tablas.add ("HOTELES");
		tablas.add ("PLANES_DE_CONSUMO");
		tablas.add ("PRODUCTOS");
		tablas.add ("RESERVAS");
		tablas.add ("RESERVAS_SERVICIOS");
		tablas.add ("SERVICIOS");
		tablas.add ("TIPO_HABITACIONES");
		tablas.add ("TIPO_PLANES_DE_CONSUMO");
		tablas.add("TIPO_SERVICIOS");
		tablas.add ("TIPO_USUARIOS");
		tablas.add ("USUARIOS");
	}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaHotelAndes (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);

		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaHotelAndes getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaHotelAndes ();
		}
		return instance;
	}

	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaHotelAndes getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaHotelAndes (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}

		return resp;
	}

	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{
		sqlConsumo = new SQLConsumo(this);
		sqlConvencion = new SQLConvencion(this);
		sqlHabitacion = new SQLHabitacion(this);
		sqlHotel = new SQLHotel(this);
		sqlPlanConsumo = new SQLPlanConsumo (this);
		sqlReserva = new SQLReserva(this);		
		sqlReservaServicio = new SQLReservaServicio(this);
		sqlServicio = new SQLServicio(this);
		sqlTipoHabitacion = new SQLTipoHabitacion(this);
		sqlTipoPlanConsumo = new SQLTipoPlanConsumo(this);		
		sqlTipoUsuario = new SQLTipoUsuario(this);
		sqlTipoServicio = new SQLTipoServicio(this);
		sqlUsuario = new SQLUsuario(this);

		sqlUtil = new SQLUtil(this);
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de Hotelandes
	 */
	public String darSeqHotelAndes() {
		return tablas.get(0);
	}


	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Consumo de Hotelandes
	 */
	public String darTablaConsumo() {
		return tablas.get(1);
	}

	public String darTablaConvencion() {
		return tablas.get(2);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Habitacion de Hotelandes
	 */
	public String darTablaHabitacion() {
		return tablas.get(3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Hotel de Hotelandes
	 */
	public String darTablaHotel()
	{
		return tablas.get(4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de PlanConsumo de Hotelandes
	 */
	public String darTablaPlanConsumo() {
		return tablas.get(5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Producto de Hotelandes
	 */
	public String darTablaProducto ()
	{
		return tablas.get(6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Reserva de Hotelandes
	 */
	public String darTablaReserva() {
		return tablas.get(7);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Reserva de Hotelandes
	 */
	public String darTablaReservaServicio() {
		return tablas.get(8);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Servicio de Hotelandes
	 */
	public String darTablaServicio() {
		return tablas.get(9);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de TipoHabitacion de Hotelandes
	 */
	public String darTablaTipoHabitacion() {
		return tablas.get(10);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de TipoPlanConsumo de Hotelandes
	 */
	public String darTablaTipoPlanConsumo() {
		return tablas.get(11);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla TipoServicio de HotelAndes
	 */
	public String darTablaTipoServicio(){
		return tablas.get(12);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla TipoUsuario de HotelAndes
	 */
	public String darTablaTipoUsuario(){
		return tablas.get(13);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Usuario de Hotelandes
	 */
	public String darTablaUsuario() {
		return tablas.get(14);
	}

	/**
	 * Transacción para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Parranderos
	 */
	private long nextval ()
	{
		long resp = sqlUtil.nextval (pmf.getPersistenceManager());
		log.trace ("Generando secuencia: " + resp);
		return resp;
	}

	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}


	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla GUSTAN
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor - Debe haber un bebedor con ese identificador
	 * @param idBebida - El identificador de la bebida - Debe haber una bebida con ese identificador
	 * @return Un objeto GUSTAN con la información dada. Null si ocurre alguna Excepción
	 */
	public TipoHabitacion adicionarTipoHabitacion(long id, String nombre, double costo, int capacidad) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlTipoHabitacion.adicionarTipoHabitacion(pm, id, costo, nombre,  capacidad);
			tx.commit();

			return new TipoHabitacion(id, nombre, costo, capacidad);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla GUSTAN, dados los identificadores de bebedor y bebida
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarTipoHabitacion(long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlTipoHabitacion.eliminarTipoHabitacionPorId(pm, id)  ;      
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}


	public TipoHabitacion darTipoHabitacion(long idTipo) {
		return sqlTipoHabitacion.darTipoHabitacionPorId(pmf.getPersistenceManager(), idTipo);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla GUSTAN
	 * @return La lista de objetos GUSTAN, construidos con base en las tuplas de la tabla GUSTAN
	 */
	public List<TipoHabitacion> darTipoHabitacion()
	{
		return sqlTipoHabitacion.darTiposHabitacion(pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			Métodos para manejar los TIPO_PLAN_CONSUMO
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla GUSTAN
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor - Debe haber un bebedor con ese identificador
	 * @param idBebida - El identificador de la bebida - Debe haber una bebida con ese identificador
	 * @return Un objeto GUSTAN con la información dada. Null si ocurre alguna Excepción
	 */
	public TipoPlanConsumo adicionarTipoPlanConsumo(long id, String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlTipoPlanConsumo.adicionarTipoPlanConsumo(pm, id, nombre);
			tx.commit();

			return new TipoPlanConsumo(id, nombre);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla GUSTAN, dados los identificadores de bebedor y bebida
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarTipoPlanConsumo(long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlTipoPlanConsumo.eliminarTipoPlanConsumoPorId(pm, id)  ;      
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla GUSTAN
	 * @return La lista de objetos GUSTAN, construidos con base en las tuplas de la tabla GUSTAN
	 */
	public List<PlanConsumo> darTipoPlanConsumo()
	{
		return sqlTipoPlanConsumo.darTiposPlanConsumo(pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			Métodos para manejar los TIPO_SERVICIO
	 *****************************************************************/
	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla GUSTAN
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor - Debe haber un bebedor con ese identificador
	 * @param idBebida - El identificador de la bebida - Debe haber una bebida con ese identificador
	 * @return Un objeto GUSTAN con la información dada. Null si ocurre alguna Excepción
	 */
	public TipoServicio adicionarTipoServicio(long id, String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlTipoServicio.adicionarTipoServicio(pm, id, nombre);
			tx.commit();

			return new TipoServicio(id, nombre);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla GUSTAN, dados los identificadores de bebedor y bebida
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarTipoServicio(long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlTipoServicio.eliminarTipoServicioPorId(pm, id)  ;      
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla GUSTAN
	 * @return La lista de objetos GUSTAN, construidos con base en las tuplas de la tabla GUSTAN
	 */
	public List<TipoServicio> darTiposServicio()
	{
		return sqlTipoServicio.darTiposServicio(pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			Métodos para manejar los TIPO_USUARIO
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla GUSTAN
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor - Debe haber un bebedor con ese identificador
	 * @param idBebida - El identificador de la bebida - Debe haber una bebida con ese identificador
	 * @return Un objeto GUSTAN con la información dada. Null si ocurre alguna Excepción
	 */
	public TipoUsuario adicionarTipoUsuario(long id, String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlTipoUsuario.adicionarTipoUsuario(pm, id, nombre);
			tx.commit();

			return new TipoUsuario(id, nombre);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla GUSTAN, dados los identificadores de bebedor y bebida
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarTipoUsuario (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlTipoUsuario .eliminarTipoUsuarioPorId(pm, id)  ;      
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla GUSTAN
	 * @return La lista de objetos GUSTAN, construidos con base en las tuplas de la tabla GUSTAN
	 */
	public List<TipoUsuario> darTiposUsuario ()
	{
		return sqlTipoUsuario.darTiposUsuario(pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			Métodos para manejar los HABITACIONES
	 *****************************************************************/

	/**
	 * M�todo que inserta, de manera transaccional, una tupla en la tabla Bebida
	 * Adiciona entradas al log de la aplicaci�n
	 * @param id 
	 * @param fecha 
	 * @param id_usuario 
	 * @param tipo_documento_usuario 
	 * @param id_servicio 
	 * @param id_habitacion 
	 * @param nombre - El nombre de la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida (Debe existir en la tabla TipoBebida)
	 * @param gradoAlcohol - El grado de alcohol de la bebida (mayor que 0)
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepci�n
	 */

	public Habitaciones adicionarHabitacion(int num_hab,  long tipo_habitacion, double cuenta_habitacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            

			long tuplasInsertadas = sqlHabitacion.adicionarHabitacion(pm, num_hab, cuenta_habitacion, tipo_habitacion);
			tx.commit();

			log.trace ("insercionconsumo: " + num_hab + ": " + tuplasInsertadas + " tuplas insertadas");
			Habitaciones habitacion = sqlHabitacion.darHabitacionPorId(pm, num_hab);

			return  new Habitaciones(num_hab, cuenta_habitacion, tipo_habitacion );
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public long ocuparHabitacionPorId( int ocupada, long numHab ){
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			//long resp = sqlHabitacion.ocuparHabitacionPorId(pm, ocupada, numHab);
			tx.commit();

			return 0;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Bebida, dado el identificador de la bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarHabitacionPorId (long idHabitacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlHabitacion.eliminarHabitacionPorId (pm, idHabitacion);
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Bebida
	 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla BEBIDA
	 */
	public List<Habitaciones> darHabitaciones ()
	{
		return sqlHabitacion.darHabitaciones(pmf.getPersistenceManager());
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Consuom con un identificador dado
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El objeto TipoBebida, construido con base en las tuplas de la tabla TIPOBEBIDA con el identificador dado
	 */
	public Habitaciones darHabitacionPorId (long idHab)
	{
		Habitaciones hab = sqlHabitacion.darHabitacionPorId(pmf.getPersistenceManager(), idHab);
		System.out.println(hab);
		return hab;
	}

	/* ****************************************************************
	 * 			M�todos para manejar los CONSUMOS
	 *****************************************************************/
	/**
	 * M�todo que inserta, de manera transaccional, una tupla en la tabla Bebida
	 * Adiciona entradas al log de la aplicaci�n
	 * @param id 
	 * @param fecha 
	 * @param id_usuario 
	 * @param tipo_documento_usuario 
	 * @param id_servicio 
	 * @param id_habitacion 
	 * @param nombre - El nombre de la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida (Debe existir en la tabla TipoBebida)
	 * @param gradoAlcohol - El grado de alcohol de la bebida (mayor que 0)
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepci�n
	 */

	public Consumo adicionarConsumo(long id, Timestamp fecha, long id_usuario, String tipo_documento_usuario, long idProd, long id_habitacion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long tuplasInsertadas = sqlConsumo.adicionarConsumo(pm, id, fecha, id_usuario, tipo_documento_usuario, idProd, id_habitacion);
			tx.commit();

			log.trace ("insercionconsumo: " + id + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Consumo(id, fecha, id_usuario, tipo_documento_usuario, idProd, id_habitacion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Bebida, dado el identificador de la bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarConsumoPorId (long idConsumo) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlConsumo.eliminarConsumoPorId (pm, idConsumo);
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Bebida
	 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla BEBIDA
	 */
	public List<Consumo> darConsumos ()
	{
		return sqlConsumo.darConsumos(pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Consuom con un identificador dado
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El objeto TipoBebida, construido con base en las tuplas de la tabla TIPOBEBIDA con el identificador dado
	 */
	public Consumo darConsumoPorId (long idConsumo)
	{
		return sqlConsumo.darConsumoPorId (pmf.getPersistenceManager(), idConsumo);
	}

	/* ****************************************************************
	 * 			Métodos para manejar los USUARIOS
	 *****************************************************************/
	/**
	 * M�todo que inserta, de manera transaccional, una tupla en la tabla Bebida
	 * Adiciona entradas al log de la aplicaci�n
	 * @param id 
	 * @param fecha 
	 * @param id_usuario 
	 * @param tipo_documento_usuario 
	 * @param id_servicio 
	 * @param id_habitacion 
	 * @param nombre - El nombre de la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida (Debe existir en la tabla TipoBebida)
	 * @param gradoAlcohol - El grado de alcohol de la bebida (mayor que 0)
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepci�n
	 */

	public Usuarios adicionarUsuario(long num_identidad, String tipo_documento, String nombre, String apellido, long tipo_usuario, long id_convencion) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long tuplasInsertadas = sqlUsuario.adicionarUsuario(pm, num_identidad, tipo_documento, nombre, apellido, tipo_usuario, id_convencion);
			tx.commit();
			Usuarios usuario = new Usuarios(num_identidad, tipo_documento, nombre, apellido, tipo_usuario, id_convencion);
			return usuario;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Bebida, dado el identificador de la bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarUsuarioPorId (long id, String tipoDoc) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlUsuario.eliminarUsuarioPorId (pm, id, tipoDoc);
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Bebida
	 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla BEBIDA
	 */
	public List<Usuarios> darUsuarios ()
	{
		return sqlUsuario.darUsuarios(pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Consuom con un identificador dado
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El objeto TipoBebida, construido con base en las tuplas de la tabla TIPOBEBIDA con el identificador dado
	 */
	public Usuarios darUsuarioPorId (long id, String tipoDoc)
	{
		Usuarios usuario = sqlUsuario.darUsuarioPorId (pmf.getPersistenceManager(), id, tipoDoc);
		System.out.println(usuario);
		return usuario;
	}


	/* ****************************************************************
	 * 			M�todos para manejar los RESERVAS
	 *****************************************************************/
	/**
	 * M�todo que inserta, de manera transaccional, una tupla en la tabla Bebida
	 * Adiciona entradas al log de la aplicaci�n
	 * @param id 
	 * @param fecha 
	 * @param id_usuario 
	 * @param tipo_documento_usuario 
	 * @param id_servicio 
	 * @param id_habitacion 
	 * @param nombre - El nombre de la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida (Debe existir en la tabla TipoBebida)
	 * @param gradoAlcohol - El grado de alcohol de la bebida (mayor que 0)
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepci�n
	 */
	public Reservas adicionarReserva(long id, int numPersonas, Timestamp entrada, Timestamp salida, Timestamp checkIn, Timestamp checkOut, Usuarios user, Habitaciones hab, long idPlanCons) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long tuplasInsertadas = sqlReserva.adicionarReserva(pm, id, numPersonas, entrada, salida, checkIn, checkOut, user.getNum_identidad(), user.getTipo_documento(), hab.getNum_hab(), idPlanCons);
			tx.commit();
			return new Reservas(id, numPersonas, entrada, salida, checkIn, checkOut, user.getNum_identidad(), user.getTipo_documento(), hab.getNum_hab(), idPlanCons);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Bebida, dado el identificador de la bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarReservaPorId (long id) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlReserva.eliminarReservaPorId (pm, id);
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Bebida
	 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla BEBIDA
	 */
	public List<Reservas> darReservas()
	{
		return sqlReserva.darReservas(pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Consuom con un identificador dado
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El objeto TipoBebida, construido con base en las tuplas de la tabla TIPOBEBIDA con el identificador dado
	 */
	public Reservas darReservaPorId (long id)
	{
		return sqlReserva.darReservaPorId (pmf.getPersistenceManager(), id);
	}

	/* ****************************************************************
	 * 			M�todos para manejar los SERVICIOS
	 *****************************************************************/

	/**
	 * Método que consulta todas las tuplas en la tabla Bebida
	 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla BEBIDA
	 */
	public List<Servicios> darServicios()
	{
		return sqlServicio.darServicios(pmf.getPersistenceManager());
	}


	/**
	 * Método que consulta todas las tuplas en la tabla Consuom con un identificador dado
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El objeto TipoBebida, construido con base en las tuplas de la tabla TIPOBEBIDA con el identificador dado
	 */
	public Servicios darServicioPorId (long id)
	{
		return sqlServicio.darServicioPorId (pmf.getPersistenceManager(), id);
	}


	public List<Object []> darReservaPorUsuario(long idUser)
	{
		return sqlReserva.darReservaUsuarios(pmf.getPersistenceManager(), idUser);
	}




	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarParranderos ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long [] resp = sqlUtil.limpiarHotelAndes(pm);
			tx.commit ();
			log.info ("Borrada la base de datos");
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return new long[] {-1, -1, -1, -1, -1, -1, -1};
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public List<Consumo> adicionarConsumoUsuario(Consumo con, Usuarios user) {
		PersistenceManager pm = pmf.getPersistenceManager();

		List<Consumo> list = sqlConsumo.darConsumosXUsuario(pm, con, user);
		System.out.println(list);
		return list;
	}

	public ReservaServicio adicionarReservaServicio(long id,
			Timestamp fecha_inicial, Timestamp fecha_final, long num_identidad,
			String tipo_documento, long idServicio) {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long tuplasInsertadas = sqlReservaServicio.adicionarReservaServicio(pm, id, fecha_inicial, fecha_final, num_identidad, tipo_documento, idServicio);
			tx.commit();
			ReservaServicio rs = new ReservaServicio(id, fecha_inicial, fecha_final, num_identidad, tipo_documento, idServicio);
			return rs;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public Convencion adicionarConvencion(long id, String nombre2,
			int cantidadPersonas, long idPlanCons, long idUsuario, 
			String tipo_documento) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long tuplasInsertadas = sqlConvencion.adicionarConvencion(pm, id, nombre2, cantidadPersonas, idPlanCons, idUsuario, tipo_documento);
			tx.commit();
			Convencion con = new Convencion(id, nombre2, cantidadPersonas, idPlanCons, idUsuario, tipo_documento);
			return con;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	public List<Habitaciones> verificarHabitacionesDisponibles(long tipo, int cantidad) {
		List<Object> l = sqlHabitacion.darHabitacionesDisponibles(pmf.getPersistenceManager(), tipo, cantidad);
		LinkedList<Habitaciones> h = new LinkedList<Habitaciones>();
		for (Object object : l) {
			Object[] datos = (Object[]) object;
			long numHab = ((BigDecimal) datos [0]).longValue ();
			double cuentaHab = ((BigDecimal) datos[1]).doubleValue();
			long tipoHab = ((BigDecimal)datos[2]).longValue();
			h.add(new Habitaciones(numHab,  cuentaHab, tipoHab));
		}
		return h;
	}

	public Servicios verificarServiciosDisponibles(long tipo, int cantidad) {
		Object object = sqlServicio.darServiciosDisponibles(pmf.getPersistenceManager(), tipo, cantidad);
		Object[] datos = (Object[]) object;
		long id = ((BigDecimal) datos [0]).longValue ();
		String nombre = datos[1].toString();
		String descripcion = datos[2]==null ? null: datos[2].toString();
		double costo = ((BigDecimal) datos[3]).doubleValue();
		int cargadoHab = ((BigDecimal)datos[4]).intValue();
		int capacidad = ((BigDecimal)datos[5]).intValue();
		long tipoServicios = ((BigDecimal) datos [6]).longValue();

		Servicios s = new Servicios(id, nombre, descripcion, costo, cargadoHab, capacidad,  tipoServicios);
		return s;
	}

	public Object darConvencion(long idConvencion) {
		return sqlConvencion.darConvencionPorId(pmf.getPersistenceManager(), idConvencion);
	}

	public void cancelarReservas(long NUM_IDENTIDAD, String TIPO_DOCUMENTO ) 
	{
		sqlReserva.cancelarReservasUsuario( pmf.getPersistenceManager(), NUM_IDENTIDAD, TIPO_DOCUMENTO);
	}

	public List<Object> darUsuariosConvencion(Long idConvencion) {
		return sqlUsuario.darUsuariosConvencion( pmf.getPersistenceManager(), idConvencion);
	}

	public long reservarServicioPorId(int reservado, long id) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			//long resp = sqlServicio.reservarServicioPorId(pm, reservado, id);
			tx.commit();

			return 0;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public void cancelarReservasServicios(long numIdentidad, String tipoDocumento) {

		sqlReservaServicio.cancelarReservasServicios(pmf.getPersistenceManager(), numIdentidad, tipoDocumento);
	}

	public void cancelarConvencion(Long idConvencion) {
		sqlConvencion.eliminaConvencionPorId(pmf.getPersistenceManager(), idConvencion);
	}
}
