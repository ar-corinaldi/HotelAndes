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
	public Reservas adicionarReserva(int numPersonas, Timestamp entrada, Timestamp salida, Timestamp checkIn, Timestamp checkOut, long idUsuario, String tipoDoc, long numHab, Usuarios user, long idPlanCons) throws Exception{
		Habitaciones habitacion = pp.darHabitacionPorId(numHab);
		Reservas reserva = pp.adicionarReserva(numPersonas, entrada, salida, checkIn, checkOut, user, habitacion, idPlanCons);
		return reserva;
	}

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	
	public Consumo adicionarConsumo( Timestamp fecha, long id_usuario, String tipo_documento_usuario, long idProd, long id_habitacion, Usuarios user) throws Exception
	{
		Producto prod = pp.darProductoPorId(idProd);
		Consumo con = null;
		System.out.println(prod);
		if( prod!= null ){
			Habitaciones hab = pp.darHabitacionPorId(id_habitacion);
			double nuevaCuenta = hab.getCuenta_habitacion() + prod.getCosto();
			con = pp.adicionarConsumo( fecha, id_usuario, tipo_documento_usuario, idProd, id_habitacion, nuevaCuenta);
			System.out.println(con);
			System.out.println("Cambio de la cuenta de la hab: "+hab.getNum_hab() + " cuenta: "+hab.getCuenta_habitacion());
		}
		else{
			throw new Exception("Producto "+ idProd +" no existe");
		}
		return con;
	}

	public Usuarios darUsuario(long id, String tipoDoc) throws Exception{
		if( !(tipoDoc.equals( Usuarios.CEDULA ) || tipoDoc.equals(Usuarios.PASAPORTE)) ){
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
		System.out.println("Adiciona usuario: "+userDB.getNum_identidad());
		return userDB;
	}



	public Habitaciones adicionarHabitacion(int numHab ,  double cuenta_habitacion, long tipo_habitacion){
		Habitaciones h = pp.adicionarHabitacion(numHab,  tipo_habitacion, cuenta_habitacion);		
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

	public ReservaServicio adicionarReservaServicio( Timestamp fecha_inicial, 
			Timestamp fecha_final, Long num_identidad,
			String tipo_documento, long idServicio) throws Exception {
		ReservaServicio rs = pp.adicionarReservaServicio(fecha_inicial, fecha_final, num_identidad, tipo_documento, idServicio);
		System.out.println(rs);
		return rs;
	}

	public Convencion adicionarConvencion( String nombre2,
			int cantidadPersonas, long idPlanCons, Long num_identidad, String tipo_documento
			) {
		Convencion conv = pp.adicionarConvencion( nombre2, cantidadPersonas, idPlanCons, num_identidad,  tipo_documento);
		System.out.println(conv);
		return conv;
	}

	public List<Habitaciones> verificarHabitacionesDisponibles(long tipo, int cantidad, Timestamp entrada, Timestamp salida) {
		List<Habitaciones> l = pp.verificarHabitacionesDisponibles(tipo, cantidad, entrada, salida);
		return l;
	}

	public boolean verificarServiciosDisponibles(long tipo, int cantidad, Timestamp entrada, Timestamp salida) {
		return pp.verificarServiciosDisponibles(tipo, cantidad, entrada, salida);
	}

	public Object darConvencion(long idConvencion) {
		Object conv = pp.darConvencion(idConvencion);
		return  conv;

	}

	public void eliminarReserva(long NumDocumento, String TipoDocumento) {
		pp.eliminarReservas(NumDocumento, TipoDocumento);
	}

	public List<Usuarios> darUsuariosConvencion(long idConvencion) {
		return pp.darUsuariosConvencion(idConvencion);
	}

	public void cancelarReservasServicios(long numIdentidad, String tipoDocumento) {

		pp.cancelarReservasServicios(numIdentidad, tipoDocumento);		
	}

	public void reservarServicios(List<ReservaServicio> lrs, long idServ) {
		pp.reservarServicioPorId(lrs, idServ);
	}

	public List<Usuarios> registrarSalidaConvencion(long idConv) {
		List<Usuarios> listU = pp.darUsuariosConvencion(idConv);
		registrarSalidaUsuarios(listU);
		return listU;
	}
	
	public void registrarSalidaUsuarios(List<Usuarios> lu){
		pp.registrarSalidaUsuarios(lu);
	}
	
	public void eliminarUsuario(Long numIdentidad, String tipoDoc) {

		pp.eliminarUsuarioPorId(numIdentidad, tipoDoc);		
	}

	public List<Habitaciones> darHabitacionesDisponibles(int cantidad, long tipoHab, Timestamp entrada, Timestamp salida){
		return pp.darHabitacionesDisponibles(cantidad, tipoHab, entrada, salida);
	}

	public long indiceUltimoUsuario() {
		return pp.indiceUltimoUsuario();
	}

	public void cancelarConvencion(Long idConvencion) {
		pp.cancelarConvencion(idConvencion);
	}

	public void crearMantenimiento(int num, Usuarios admin, Timestamp entrada,
			Timestamp salida, long id) throws Exception {
		if( num==1 ){
			Servicios s = pp.darServicioPorId(id);
			ReservaServicio rs = pp.darReservaServicioXFechasYidSer(entrada, salida, id);
			if( rs==null ){
				adicionarReservaServicio( entrada, salida, admin.getNum_identidad(), admin.getTipo_documento(), id);
			}
			else{
			}
		}
		else if( num==2 ){
			Habitaciones h = pp.darHabitacionPorId(id);
			Reservas r = pp.darReservaXFechasYNumHab(entrada, salida, id);
			System.out.println("Reserva: "+r);
			if( r==null ){
				adicionarReserva( 1, entrada, salida, entrada, salida, admin.getNum_identidad(), admin.getTipo_documento(), id, admin, 0);
			}
			else if( r.getCheck_in() == null ){
				System.out.println(indiceUltimoUsuario());
				adicionarReserva( 1, entrada, salida, entrada, salida, admin.getNum_identidad(), admin.getTipo_documento(), id, admin, 0);
			}
			else if( r.getCheck_in() != null && r.getCheck_out() == null ){
				moverUsuario( h, entrada, salida );
				adicionarReserva( 1, entrada, salida, entrada, salida, admin.getNum_identidad(), admin.getTipo_documento(), id, admin, 0);
			}
		}
	}

	public void moverUsuario( Habitaciones h, Timestamp entrada, Timestamp salida ) throws Exception{
		List<Habitaciones> list = verificarHabitacionesDisponibles(h.getTipo_habitacion(), 1, entrada, salida);
		long i = h.getTipo_habitacion();
		while( list.isEmpty() && i > 1 ){
			list = verificarHabitacionesDisponibles(i--, 1, entrada, salida);
		}
		if( i==1 )
			throw new Exception("No hay habitaciones disponibles");
		else{
			Habitaciones nueva = list.get(0);
			System.out.println(nueva);
			pp.moverUsuario(nueva, h);
			System.out.println("Su habitacion nueva "+ nueva.getNum_hab());
		}
	}

	public void registrarLlegadaReserva(long idUser, String tipoDoc,Timestamp ingreso, long idRes) {
		pp.registrarLlegadaReserva(idUser, tipoDoc, ingreso, idRes);
	}

	public void registrarSalidaReserva(Long num_identidad,
			String tipo_documento, Timestamp salida, long idRes) throws Exception {
		pp.registrarSalidaReserva(num_identidad, tipo_documento, salida, idRes);

	}

	public void terminarMantenimientoHab(Long num_identidad, String tipo_documento, int numHab) {

		pp.terminarMantenimientoHab(num_identidad, tipo_documento, numHab);
	}

	public void terminarMantenimientoServ(Long num_identidad, String tipo_documento, int idServ) {

		pp.terminarMantenimientoServ(num_identidad, tipo_documento, idServ);

	}

	public List<Object> buscarBuenosClientesPorConsumo() {
		return  pp.buscarBuenosClientesPorConsumo();
		
	}

	public Object[] reqFC6(int tipoHab, long idServicio, String tipoTiempo) throws Exception {
		return pp.reqFC6(tipoHab, idServicio, tipoTiempo);
	}

	public List<Usuarios> reqCF9(String servicio, Timestamp entrada, Timestamp salida, boolean[] tipoClasificacion,
			boolean[] tipoOrdenamiento) throws Exception {
		List<Usuarios> usuarios = null;
		usuarios = pp.reqFC9(servicio, entrada, salida, tipoClasificacion, tipoOrdenamiento);
		return usuarios;
	}

	public void reqCF10(String servicio, Timestamp entrada, Timestamp salida, boolean[] tipoClasificacion) {
		pp.reqFC10(servicio, entrada, salida, tipoClasificacion);
		
	}

	public Object[] reqCF11() throws Exception {
		
		return pp.reqFC11();
	}

	public List<Object>  buscarBuenosClientesPorReservasServicio() {
		
		return pp.buscarBuenosClientesPorReservasServicio();
	}
}
