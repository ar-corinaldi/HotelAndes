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

	private List<Usuarios> usuarios;

	private List<Habitaciones> habitaciones;

	private List<PlanConsumo> planesConsumo;

	private List<Servicios> servicios;

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

	public List<Usuarios> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuarios> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Habitaciones> getHabitaciones() {
		return habitaciones;
	}

	public void setHabitaciones(List<Habitaciones> habitaciones) {
		this.habitaciones = habitaciones;
	}

	public List<PlanConsumo> getPlanesConsumo() {
		return planesConsumo;
	}

	public void setPlanesConsumo(List<PlanConsumo> planesConsumo) {
		this.planesConsumo = planesConsumo;
	}

	public List<Servicios> getServicios() {
		return servicios;
	}

	public void setServicios(List<Servicios> servicios) {
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

	/* ****************************************************************
	 * 			RESERVA
	 *****************************************************************/
	public Reservas adicionarReserva(long id, int numPersonas, Timestamp entrada, Timestamp salida, Timestamp checkIn, Timestamp checkOut, long idUsuario, String tipoDoc, long numHab, Usuarios user, long idPlanCons) throws Exception{
		Habitaciones habitacion = pp.darHabitacionPorId(numHab);
		if( habitacion.getOcupada() == Habitaciones.SE_OCUPA )
			throw new Exception( "Habitacion " + habitacion.getNum_hab() +" ya esta ocupada" );
		else
			habitacion.setOcupada(Habitaciones.SE_OCUPA);

		Reservas reserva = pp.adicionarReserva(id, numPersonas, entrada, salida, checkIn, checkOut, user, habitacion, idPlanCons);
		if( reserva!= null ){
			System.out.println("Habitacion desocupada");
			pp.ocuparHabitacionPorId(Habitaciones.SE_OCUPA, habitacion.getNum_hab());
			System.out.println("Habitacion ocupada");
			System.out.print("Crea la reserva: ");
			System.out.println(reserva);

			System.out.println(pp.darReservaPorId(reserva.getId()));
		}
		else throw new Exception("Reserva nula: "+reserva);
		return reserva;
	}




	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	public Consumo adicionarConsumo(long id, Timestamp fecha, long id_usuario, String tipo_documento_usuario, long idProd, long id_habitacion, Usuarios user) throws Exception
	{
		Consumo con = pp.adicionarConsumo(id, fecha, id_usuario, tipo_documento_usuario, idProd, id_habitacion);
		System.out.println(con);
		if( con != null ){
			System.out.println("Entra");
			user.getConsumos().add(con);
			System.out.println(id_usuario);
			System.out.println(tipo_documento_usuario);
			List<Consumo> l = pp.adicionarConsumoUsuario(con, user);
//			for (Consumo consumo : l) {
//				System.out.println(l);
//			}
		}
		else{
			throw new Exception( "O el usuario no existe, o el id del consumo es nulo, o ya estaba reservado el servicio." );
		}

		return con;
	}

	public Usuarios darUsuario(long id, String tipoDoc) throws Exception{
		if( tipoDoc == Usuarios.CEDULA || tipoDoc == Usuarios.PASAPORTE ){
			throw new Exception("No existe tal tipo de documento "+tipoDoc);
		}
		Usuarios usuario = pp.darUsuarioPorId(id, tipoDoc);
		return usuario;
	}

	/**
	 * Adiciona de manera persistente un tipo de bebida 
	 * Adiciona entradas al log de la aplicaci�n
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepci�n
	 */
	public Usuarios adicionarUsuario(Usuarios user)
	{
		Usuarios userDB = pp.adicionarUsuario(user.getNum_identidad(), user.getTipo_documento(), user.getNombre(), user.getApellido(), user.getTipo_usuario(), user.getId_convencion());
		System.out.println(userDB);
		return userDB;
	}



	public Habitaciones adicionarHabitacion(int numHab , boolean ocupada, double cuenta_habitacion, long tipo_habitacion){
		Habitaciones h = pp.adicionarHabitacion(numHab, ocupada, tipo_habitacion, cuenta_habitacion);		
		return h;
	}

	public TipoHabitacion adicionarTipoHabitacion(long id, String nombre, double costo, int capacidad){
		return pp.adicionarTipoHabitacion(id, nombre, costo, capacidad);		
	}


	public TipoHabitacion darTipoHabitacion(long tipoHabitacion) {

		return pp.darTipoHabitacion(tipoHabitacion);
	}

	public List<Consumo> darConsumos() {

		return pp.darConsumos();
	}

	public Servicios darServicio(long idServicio) {
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

	public ReservaServicio adicionarReservaServicio(long id,
			Timestamp fecha_inicial, Timestamp fecha_final, Long num_identidad,
			String tipo_documento, long idServicio) {
		ReservaServicio rs = pp.adicionarReservaServicio(id, fecha_inicial, fecha_final, num_identidad, tipo_documento, idServicio);
		System.out.println(rs);
		return rs;
	}

	public Convencion adicionarConvencion(long id, String nombre2,
			int cantidadPersonas, long idPlanCons, Long num_identidad, String tipo_documento
			) {
		Convencion conv = pp.adicionarConvencion(id, nombre2, cantidadPersonas, idPlanCons, num_identidad,  tipo_documento);
		System.out.println(conv);
		return conv;
	}

	public boolean verificarHabitacionesDisponibles(long tipo, int cantidad) {
		boolean rta = false;
		List<Habitaciones> l = pp.verificarHabitacionesDisponibles(tipo, cantidad);
		System.out.println("Habitaciones Disponibles: "+l.size());
		rta = l.size() == cantidad;
		return rta;
	}

	public boolean verificarServiciosDisponibles(long tipo, int cantidad) {
		Servicios s = pp.verificarServiciosDisponibles(tipo, cantidad);
		boolean rta = s.getCapacidad() >= cantidad;
		System.out.println(s);
		System.out.println("Capacidad del servicio: "+ s.getCapacidad());
		return rta;
	}
	
	public Object darConvencion(long idConvencion) {
		Object conv = pp.darConvencion(idConvencion);
		return  conv;
		
	}

	public void cancelarReserva(long NumDocumento, String TipoDocumento) {
		pp.cancelarReservas(NumDocumento, TipoDocumento);
	}

	public List<Object> darUsuariosConvencion(long idConvencion) {
		return pp.darUsuariosConvencion(idConvencion);
	}

	public void cancelarReservasServicios(long numIdentidad, String tipoDocumento) {

		pp.cancelarReservasServicios(numIdentidad, tipoDocumento);		
	}
}
