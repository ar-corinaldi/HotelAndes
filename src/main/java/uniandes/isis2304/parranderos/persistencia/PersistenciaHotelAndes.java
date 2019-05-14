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
import uniandes.isis2304.parranderos.negocio.Producto;
import uniandes.isis2304.parranderos.negocio.ReservaServicio;
import uniandes.isis2304.parranderos.negocio.Reservas;
import uniandes.isis2304.parranderos.negocio.Servicios;
import uniandes.isis2304.parranderos.negocio.TipoHabitacion;
import uniandes.isis2304.parranderos.negocio.TipoServicio;
import uniandes.isis2304.parranderos.negocio.TipoUsuario;
import uniandes.isis2304.parranderos.negocio.Usuarios;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.org.apache.xml.internal.security.encryption.AgreementMethod;

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

	private SQLProducto sqlProducto;
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
		sqlTipoUsuario = new SQLTipoUsuario(this);
		sqlTipoServicio = new SQLTipoServicio(this);
		sqlUsuario = new SQLUsuario(this);
		sqlProducto = new SQLProducto(this);
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
	 * @return La cadena de caracteres con el nombre de la tabla TipoServicio de HotelAndes
	 */
	public String darTablaTipoServicio(){
		return tablas.get(11);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla TipoUsuario de HotelAndes
	 */
	public String darTablaTipoUsuario(){
		return tablas.get(12);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Usuario de Hotelandes
	 */
	public String darTablaUsuario() {
		return tablas.get(13);
	}

	/**
	 * Transacción para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Parranderos
	 */
	private long nextval ()
	{
		System.out.println("Prueba");
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
		try {
			return sqlTipoHabitacion.darTipoHabitacionPorId(pmf.getPersistenceManager(), idTipo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla GUSTAN, dados los identificadores de bebedor y bebida
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */



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

	public Consumo adicionarConsumo( Timestamp fecha, long id_usuario, String tipo_documento_usuario, long idProd, long id_habitacion, double consumo) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long id = sqlConsumo.darUltimoId(pm);
			long tuplasInsertadas = sqlConsumo.adicionarConsumo(pm, id, fecha, id_usuario, tipo_documento_usuario, idProd, id_habitacion);
			sqlHabitacion.agregarConsumoHabitacion(pm, id_habitacion, consumo);
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
			sqlUsuario.adicionarUsuario(pm, num_identidad, tipo_documento, nombre, apellido, tipo_usuario, id_convencion);
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

	public long eliminarUsuarios ( List<Usuarios> lu ) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long resp = 0;
			tx.begin();
			for (Usuarios user : lu) {
				resp += sqlUsuario.eliminarUsuarioPorId (pm, user.getNum_identidad(), user.getTipo_documento());

			}
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
		Object[] data = (Object[])sqlUsuario.darUsuarioPorId (pmf.getPersistenceManager(), id, tipoDoc);
		String nombre = data[2].toString();
		String apellido = data[3].toString();
		long tipoUsuario =  ((BigDecimal)data[4]).longValue();
		long idConvencion = ((BigDecimal) data[5]).longValue();
		Usuarios u = new Usuarios(id, tipoDoc, nombre, apellido, tipoUsuario, idConvencion);

		return u;
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
	 * @throws Exception 
	 */
	public Reservas adicionarReserva(int numPersonas, Timestamp entrada, Timestamp salida, Timestamp checkIn, Timestamp checkOut, Usuarios user, Habitaciones hab, long idPlanCons) throws Exception 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long newId = sqlReserva.darUltimoId(pm);
			sqlReserva.adicionarReserva(pm, newId, numPersonas, entrada, salida, checkIn, checkOut, user.getNum_identidad(), user.getTipo_documento(), hab.getNum_hab(), idPlanCons);
			TipoHabitacion th = sqlTipoHabitacion.darTipoHabitacionPorId(pm, hab.getTipo_habitacion());
			
			int cap = th.getCapacidad();
			if(cap < numPersonas){
				throw new Exception("Demasiadas personas para el tipo de habitacion: "+numPersonas + "\nSolo caben "+ cap);
			}

			sqlHabitacion.agregarConsumoHabitacion(pm, hab.getNum_hab(), th.getCosto());
			tx.commit();
			return new Reservas(newId, numPersonas, entrada, salida, checkIn, checkOut, user.getNum_identidad(), user.getTipo_documento(), hab.getNum_hab(), idPlanCons);
		}
		catch (Exception e)
		{
//			        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			throw new Exception( e.getMessage() );
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

	public Reservas darReservaXFechasYNumHab( Timestamp entrada, Timestamp salida, long numHab ){

		return sqlReserva.darReservaXFechasYNumHab(pmf.getPersistenceManager(), entrada, salida, numHab);
	}
	
	public ReservaServicio darReservaServicioXFechasYidSer(Timestamp entrada,
			Timestamp salida, long id) {
		return sqlReservaServicio.darReservaServicioXFechasYidSer(pmf.getPersistenceManager(), entrada, salida, id);
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
	public Servicios darServicioPorId (long idServ)
	{
		Object o = sqlServicio.darServicioPorId (pmf.getPersistenceManager(), idServ);
		Object[] datos = (Object[]) o;
		long id = ((BigDecimal) datos [0]).longValue ();
		String nombre = datos [1].toString();
		String descripcion = datos[2]==null? null:datos[2].toString();
		double costo = ((BigDecimal)datos[3]).doubleValue();
		int cargadoHab = ((BigDecimal) datos[4]).intValue();
		int capacidad = ((BigDecimal) datos[5]).intValue();
		long tipoServs = ((BigDecimal)datos[6]).longValue();
		return new Servicios(id, nombre, descripcion, costo, cargadoHab, capacidad, tipoServs);
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

	public ReservaServicio adicionarReservaServicio(
			Timestamp fecha_inicial, Timestamp fecha_final, long num_identidad,
			String tipo_documento, long idServicio) throws Exception {

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long id = sqlReservaServicio.darUltimoId(pm);
			sqlReservaServicio.adicionarReservaServicio(pm, id, fecha_inicial, fecha_final, num_identidad, tipo_documento, idServicio);
			tx.commit();
			ReservaServicio rs = new ReservaServicio(id, fecha_inicial, fecha_final, num_identidad, tipo_documento, idServicio);
			return rs;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			throw new Exception(e.getMessage());
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

	public Convencion adicionarConvencion( String nombre2,
			int cantidadPersonas, long idPlanCons, long idUsuario, 
			String tipo_documento) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();     
			long id = sqlConvencion.darUltimoId(pm);
			sqlConvencion.adicionarConvencion(pm, id, nombre2, cantidadPersonas, idPlanCons, idUsuario, tipo_documento);
			
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

	public List<Habitaciones> verificarHabitacionesDisponibles(long tipo, int cantidad, Timestamp entrada, Timestamp salida) {
		List<Object> l = sqlHabitacion.darHabitacionesDisponibles(pmf.getPersistenceManager(), tipo, cantidad, entrada, salida);
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

	public boolean verificarServiciosDisponibles(long tipo, int cantidad, Timestamp entrada, Timestamp salida) {
		Object object = sqlReservaServicio.verificarDisponibilidad(pmf.getPersistenceManager(), tipo, cantidad, entrada, salida);
		System.out.println("Verificacion de servicios disponibles");
		//sqlServicio.darServiciosDisponibles(pmf.getPersistenceManager(), tipo, cantidad);
		Object[] datos = (Object[]) object;
		return object==null? true:false;
	}

	public Object darConvencion(long idConvencion) {
		return sqlConvencion.darConvencionPorId(pmf.getPersistenceManager(), idConvencion);
	}

	public void eliminarReservas(long NUM_IDENTIDAD, String TIPO_DOCUMENTO ) 
	{
		sqlReserva.eliminarReservasUsuario( pmf.getPersistenceManager(), NUM_IDENTIDAD, TIPO_DOCUMENTO);
	}

	public List<Usuarios> darUsuariosConvencion(Long idConvencion) {
		return sqlUsuario.darUsuariosConvencion( pmf.getPersistenceManager(), idConvencion);
	}

	public long reservarServicioPorId( List<ReservaServicio> lrs, long idServ) {
		PersistenceManager pm = pmf.getPersistenceManager();
		System.out.println("Reserva Servicio para el servicio: "+idServ);
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			for (ReservaServicio rs : lrs) {
				long id = sqlReservaServicio.darUltimoId(pm);
				sqlReservaServicio.adicionarReservaServicio(pm, id, rs.getFecha_inicial(), rs.getFecha_final(), rs.getId_usuario(), rs.getTipo_documento_usuario(), idServ);
			}

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

	public List<Habitaciones> darHabitacionesDisponibles(int cantidad, long tipoHab, Timestamp entrada, Timestamp salida){
		List<Habitaciones> rta = new LinkedList<Habitaciones>();
		List<Object> objects = sqlReserva.darHabitacionesDisponibles( pmf.getPersistenceManager(), cantidad, tipoHab, entrada, salida);
		for (Object object : objects) {
			Object[] data = (Object[]) object;
			long numHab = ((BigDecimal)data[0]).longValue();
			double cuentaHab = ((BigDecimal) data[1]).doubleValue();
			Habitaciones hab = new Habitaciones(numHab, cuentaHab, tipoHab);
			rta.add(hab);
			System.out.println(hab);
		}
		System.out.println(rta.size());
		return rta;
	}

	public long indiceUltimoUsuario() {
		String s = sqlUsuario.indiceUltimoUsuario(pmf.getPersistenceManager())==null ? "1":sqlUsuario.indiceUltimoUsuario(pmf.getPersistenceManager()).toString() ;
		return Long.valueOf(s);
	}

	public void cancelarConvencion(Long idConvencion) {
		
		sqlConvencion.eliminaConvencionPorId(pmf.getPersistenceManager(), idConvencion);
	}

	public void moverUsuario(Habitaciones nueva, Habitaciones anterior) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long resp = 0;
			tx.begin();
			sqlHabitacion.moverUsuario(pm, nueva.getNum_hab(), anterior.getCuenta_habitacion(), nueva.getTipo_habitacion());
			sqlHabitacion.moverUsuario(pm, anterior.getNum_hab(), 0.0, anterior.getTipo_habitacion());
			tx.commit();
			return;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return;
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

	public void registrarLlegadaReserva(long idUsuario, String tipoDoc, Timestamp ingreso, long idRes) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			long resp = 0;
			tx.begin();
			System.out.println("Antes del sql");
			sqlReserva.registrarLlegadaReserva(pm, idUsuario, tipoDoc,ingreso, idRes);
			System.out.println("Reserva con id: "+idRes+" ha sido editada");
			tx.commit();

			return;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return;
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

	public void registrarSalidaReserva(Long num_identidad,
			String tipo_documento, Timestamp salida, long idRes) throws Exception {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			sqlReserva.registrarSalidaReserva(pm, num_identidad, tipo_documento, salida, idRes);
			Reservas res = sqlReserva.darReservaPorId(pm, idRes);
			
			System.out.println("Reserva con id: "+idRes+" ha sido editada");
			sqlHabitacion.agregarConsumoHabitacion(pm, res.getId_habitacion(), 0);

			tx.commit();

			return;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			throw new Exception(e.getMessage());
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

	public void terminarMantenimientoHab(Long num_identidad, String tipo_documento, int numHab) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			sqlReserva.terminarMantenimiento(pmf.getPersistenceManager(), num_identidad, tipo_documento, numHab);

			tx.commit();
			System.out.println("Se registra con exito la salida de todos los usuarios");

			return;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return;
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

	public void terminarMantenimientoServ(Long num_identidad, String tipo_documento, int idServ) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			sqlReservaServicio.terminarMantenimiento(pm, num_identidad, tipo_documento, idServ);

			tx.commit();
			System.out.println("Se registra con exito la salida de todos los usuarios");

			return;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return;
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

	public Producto darProductoPorId(long idProd) {

		return sqlProducto.darProductoPorId(pmf.getPersistenceManager(), idProd);
	}

	public void registrarSalidaUsuarios(List<Usuarios> lu) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			for (Usuarios user : lu) {
				sqlReserva.registrarSalidaReserva(pm, user.getNum_identidad(), user.getTipo_documento(), new Timestamp(System.currentTimeMillis()), user.getNum_identidad());
				Reservas r = darReservaPorId(user.getNum_identidad());
				sqlHabitacion.agregarConsumoHabitacion(pm, r.getId_habitacion(), 0.0);
			}
			tx.commit();
			System.out.println("Se registra con exito la salida de todos los usuarios");

			return;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return;
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

	public List<Object> buscarBuenosClientesPorConsumo() {


		List<Object> buenos = sqlConsumo.buscarBuenosClientes(pmf.getPersistenceManager());
		LinkedList<Object> clientes = new LinkedList<Object>();
		for (Object object : buenos) {
			Object[] cliente = (Object[]) object;
			if(((BigDecimal) cliente [1]).intValue() >= 15000000)
			{
				long id = ((BigDecimal) cliente [1]).longValue();
				String tipoDoc = ((String) cliente [2]) ;
				Object[] rest = {id,tipoDoc};
				clientes.add(rest);
			}
		}
		return clientes;	
	}
}

