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

package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.persistencia.PersistenciaHotelAndes;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Germán Bravo
 */
public class HotelAndes 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(HotelAndes.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaHotelAndes pp;

	private int capacidad;

	private String nombre;

	private String pais;

	private String ciudad;

	private List<Usuario> usuarios;

	private List<Habitacion> habitaciones;

	private List<PlanConsumo> planesConsumo;

	private List<Servicio> servicios;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public HotelAndes ()
	{
		pp = PersistenciaHotelAndes.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public HotelAndes (JsonObject tableConfig)
	{
		pp = PersistenciaHotelAndes.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}
	
	/******************************************************************************
	 * METODOS
	 ******************************************************************************/

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Habitacion> getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(List<Habitacion> habitaciones) {
		this.habitaciones = habitaciones;
	}

	public List<PlanConsumo> getPlanesConsumo() {
		return planesConsumo;
	}

	public void setPlanesConsumo(List<PlanConsumo> planesConsumo) {
		this.planesConsumo = planesConsumo;
	}

	public List<Servicio> getServicios() {
		return servicios;
	}

	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}

	@Override
	public String toString() {
		return "HotelAndes [capacidad=" + capacidad + ", nombre=" + nombre
				+ ", pais=" + pais + ", ciudad=" + ciudad + ", usuarios="
				+ usuarios + ", habitaciones=" + habitaciones
				+ ", planesConsumo=" + planesConsumo + ", servicios="
				+ servicios + "]";
	}

	public Consumo adicionarConsumo(long id, Timestamp fecha, long id_usuario, String tipo_documento_usuario, long id_servicio, long id_habitacion) throws Exception
	{
		Consumo con = pp.adicionarConsumo(id, fecha, id_usuario, tipo_documento_usuario, id_servicio, id_habitacion);
		Usuario user = pp.darUsuarioPorId(id_habitacion,tipo_documento_usuario);
		Servicio ser = pp.darServicioPorId(id_servicio);
		if( user != null && ser.isReservado() ){
			user.getConsumos().add(con);
		}
		else{
			throw new Exception( "O el usuario no existe, o el id del consumo es nulo, o ya estaba reservado el servicio." );
			//excepcion 
		}
		
		return con;
	}
	
	public Usuario darUsuario(long id, String tipoDoc){
		return pp.darUsuarioPorId(id, tipoDoc);
	}

	/**
	 * Adiciona de manera persistente un tipo de bebida 
	 * Adiciona entradas al log de la aplicaci�n
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepci�n
	 */
	public Usuario adicionarUsuario(long num_identidad, String tipo_documento, String nombre, String apellido, String correo, long tipo_usuario, long id_reserva, long id_hotel)
	{
		Usuario usuario = pp.adicionarUsuario(num_identidad, tipo_documento, nombre, apellido, correo, tipo_usuario, id_reserva, id_hotel);		
		//pp.adicionarReserva(id_reserva, 1, new Timestamp(0), new Timestamp(0), pc, h);
		return usuario;
	}

	public Reserva adicionarReserva(long id, int numPersonas, Timestamp entrada, Timestamp salida, PlanConsumo pc, Habitacion h){
		Reserva reserva = pp.adicionarReserva(id, numPersonas, entrada, salida, pc, h);		
		return reserva;
	}

	public Habitacion adicionarHabitacion(long id, double cuenta_habitacion, long tipo_habitacion, long id_hotel){
		Habitacion h = pp.adicionarHabitacion(id, cuenta_habitacion, tipo_habitacion, id_hotel);		
		return h;
	}

	public TipoHabitacion adicionarTipoHabitacion(long id, String nombre, double costo, int capacidad){
		return pp.adicionarTipoHabitacion(id, nombre, costo, capacidad);		
	}
	
	public Check adicionarCheck(long idCliente, String tipoDocumentoCliente,long idRecepcion, String tipoDocumentoRecepcion, boolean entrada) throws Exception
	{
		Check retornable = null;
		Usuario recepcionista = pp.darUsuarioPorId(idRecepcion, tipoDocumentoRecepcion);
		Usuario cliente = pp.darUsuarioPorId(idCliente, tipoDocumentoCliente);
		Timestamp fechaActual = Timestamp.valueOf(LocalDateTime.now());
		Reserva reserva = cliente.getReserva();
	

		if(pp.darReservaPorId(reserva.getId())==null)
				throw new Exception("No se encuentra reserva del cliente"); 
		if(!(fechaActual.after(reserva.getEntrada())&& fechaActual.before(reserva.getSalida())))
				throw new Exception("la reserva Termino");
		
		retornable =recepcionista.crearCheck(idCliente, tipoDocumentoCliente, fechaActual, entrada, reserva.getHabitacion().getId());
		int ingreso =1;
		if(!retornable.isIngreso())
			ingreso = 0;
		pp.adicionarCheck(retornable.getId(), retornable.getFecha(), ingreso, retornable.getIdUsuario(), retornable.getTipoDocumentoUsuario(), retornable.getIdHabitacion());
		
		return retornable;
		
	}

	public TipoHabitacion darTipoHabitacion(long tipoHabitacion) {
		
		return pp.darTipoHabitacion(tipoHabitacion);
	}

	public List<Consumo> darConsumos() {

		return pp.darConsumos();
	}

	public Servicio darServicio(long idServicio) {
		return pp.darServicioPorId(idServicio);
	}

	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarParranderos ()
	{
        log.info ("Limpiando la BD de Parranderos");
        long [] borrrados = pp.limpiarParranderos();	
        log.info ("Limpiando la BD de Parranderos: Listo!");
        return borrrados;
	}
}
