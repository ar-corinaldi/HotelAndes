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

package uniandes.isis2304.parranderos.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.Consumo;
import uniandes.isis2304.parranderos.negocio.Convencion;
import uniandes.isis2304.parranderos.negocio.Habitaciones;
import uniandes.isis2304.parranderos.negocio.HotelAndes;
import uniandes.isis2304.parranderos.negocio.ReservaServicio;
import uniandes.isis2304.parranderos.negocio.Reservas;
import uniandes.isis2304.parranderos.negocio.Servicios;
import uniandes.isis2304.parranderos.negocio.Usuarios;
import uniandes.isis2304.parranderos.negocio.VOReserva;
import uniandes.isis2304.parranderos.negocio.VOUsuario;

/**
 * Clase principal de la interfaz
 * @author Germán Bravo
 */
@SuppressWarnings("serial")

public class InterfazHotelAndesApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazHotelAndesApp.class.getName());

	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 

	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 

	public static final long ADMINISTRADOR = 2;

	public static final long RECEPCIONISTA = 3;

	public static final long EMPLEADO = 4;

	public static final long CLIENTE = 5;

	public static final long ORGANIZADOR = 6;

	public static final long GERENTE = 1;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
	 */
	private JsonObject tableConfig;

	/**
	 * Asociación a la clase principal del negocio.
	 */
	private HotelAndes parranderos;

	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
	/**
	 * Objeto JSON con la configuración de interfaz de la app.
	 */
	private JsonObject guiConfig;

	/**
	 * Panel de despliegue de interacción para los requerimientos
	 */
	private PanelDatos panelDatos;

	/**
	 * Menú de la aplicación
	 */
	private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Construye la ventana principal de la aplicación. <br>
	 * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	 */
	public InterfazHotelAndesApp( )
	{
		// Carga la configuración de la interfaz desde un archivo JSON
		guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz gráfica
		configurarFrame ( );
		if (guiConfig != null) 	   
		{
			crearMenu( guiConfig.getAsJsonArray("menuBar") );
		}

		tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
		parranderos = new HotelAndes (tableConfig);

		String path = guiConfig.get("bannerPath").getAsString();
		panelDatos = new PanelDatos ( );

		setLayout (new BorderLayout());
		add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
		add( panelDatos, BorderLayout.CENTER );        
	}

	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
	/**
	 * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuración deseada
	 * @param archConfig - Archivo Json que contiene la configuración
	 * @return Un objeto JSON con la configuración del tipo especificado
	 * 			NULL si hay un error en el archivo.
	 */
	private JsonObject openConfig (String tipo, String archConfig)
	{
		JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
			//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

	/**
	 * Método para configurar el frame principal de la aplicación
	 */
	private void configurarFrame(  )
	{
		int alto = 0;
		int ancho = 0;
		String titulo = "";	

		if ( guiConfig == null )
		{
			log.info ( "Se aplica configuración por defecto" );			
			titulo = "Parranderos APP Default";
			alto = 300;
			ancho = 500;
		}
		else
		{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
			titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
		}

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocation (50,50);
		setResizable( true );
		setBackground( Color.WHITE );

		setTitle( titulo );
		setSize ( ancho, alto);        
	}

	/**
	 * Método para crear el menú de la aplicación con base em el objeto JSON leído
	 * Genera una barra de menú y los menús con sus respectivas opciones
	 * @param jsonMenu - Arreglo Json con los menùs deseados
	 */
	private void crearMenu(  JsonArray jsonMenu )
	{    	
		// Creación de la barra de menús
		menuBar = new JMenuBar();       
		for (JsonElement men : jsonMenu)
		{
			// Creación de cada uno de los menús
			JsonObject jom = men.getAsJsonObject(); 

			String menuTitle = jom.get("menuTitle").getAsString();        	
			JsonArray opciones = jom.getAsJsonArray("options");

			JMenu menu = new JMenu( menuTitle);

			for (JsonElement op : opciones)
			{       	
				// Creación de cada una de las opciones del menú
				JsonObject jo = op.getAsJsonObject(); 
				String lb =   jo.get("label").getAsString();
				String event = jo.get("event").getAsString();

				JMenuItem mItem = new JMenuItem( lb );
				mItem.addActionListener( this );
				mItem.setActionCommand(event);

				menu.add(mItem);
			}       
			menuBar.add( menu );
		}        
		setJMenuBar ( menuBar );	
	}

	/* ****************************************************************
	 * 			CRUD de Reserva
	 *****************************************************************/

	/**
	 * Adiciona un tipo de bebida con la información dada por el usuario
	 * Se crea una nueva tupla de tipoBebida en la base de datos, si un tipo de bebida con ese nombre no existía
	 */
	public void adicionarUsuario( )
	{
		try 
		{
			String num_identidad = JOptionPane.showInputDialog (this, "Numeor de identificacion?", "Adicionar usuario", JOptionPane.QUESTION_MESSAGE);
			String tipo_documento = (String) JOptionPane.showInputDialog(this,
					"Seleccione el tipo de documento",
					"Adicionar usuario",
					JOptionPane.QUESTION_MESSAGE,
					null,  // null para icono defecto
					new String[] { "cedula", "pasaporte" }, 
					"cedula");			

			String nombre = JOptionPane.showInputDialog (this, "Nombre?", "Adicionar usuario", JOptionPane.QUESTION_MESSAGE);

			String apellido = JOptionPane.showInputDialog (this, "Apellido?", "AAdicionar usuario", JOptionPane.QUESTION_MESSAGE);

			String id_convencion = JOptionPane.showInputDialog(this, "Convencion? (0)", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
			if (num_identidad != null)
			{
				Usuarios usuario = new Usuarios(num_identidad, tipo_documento, nombre, apellido, 5 + "", id_convencion);
				Usuarios user = parranderos.adicionarUsuario( usuario );
				if (user == null)
				{
					throw new Exception ("No se pudo crear un tipo de bebida con nombre: " + nombre);
				}
				String resultado = "En adicionarUsuario\n\n";
				resultado += "Usuario adicionado exitosamente: " + user;
				resultado += "\n Operaci�n terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operaci�n cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
		}
	}

	public void adicionarReserva(){
		try {
			Usuarios cliente = verificarUsuario(CLIENTE);

			int numPersonas = Integer.valueOf(JOptionPane.showInputDialog (this, "Cantidad personas?", "Adicionar reserva", JOptionPane.OK_OPTION));


			int mes1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
			int dia1 = ThreadLocalRandom.current().nextInt(1, 15 + 1);

			int mes2 = ThreadLocalRandom.current().nextInt(mes1, 12 + 1);
			int dia2 = ThreadLocalRandom.current().nextInt(dia1, 28 + 1);

			String entradaStr = JOptionPane.showInputDialog (this, "fecha entrada?\n(Ejm: 2019-"+mes1+"-"+dia1+")", "Adicionar reserva", JOptionPane.OK_OPTION);
			String salidaStr = JOptionPane.showInputDialog (this, "fecha salida?\n(Ejm: 2019-"+mes2+"-"+dia2+")", "Adicionar reserva", JOptionPane.OK_OPTION);

			String tipoStr = (String) JOptionPane.showInputDialog(this,
					"Seleccione el tipo de habitacion",
					"Adicionar reserva",
					JOptionPane.QUESTION_MESSAGE,
					null,  // null para icono defecto
					new String[] { "1. suite presidencial (capacidad: 10)", "2. familiar (capacidad: 4)", "3. doble (capacidad: 2)", 
							"4. sencilla (capacidad: 1)", "5. suite (capacidad: 8)" }, 
					"1. suite presidencial (capacidad: 10)");
			tipoStr = tipoStr.split(". ")[0];

			long tipo = Long.valueOf(tipoStr);
			long idPlanCons = Long.valueOf(JOptionPane.showInputDialog (this, "plan consumo id?(0 si no tiene)", "Adicionar reserva", JOptionPane.OK_OPTION));

			Timestamp entrada = Timestamp.valueOf(entradaStr.trim() + " 06:00:00.00");
			Timestamp salida = Timestamp.valueOf(salidaStr.trim() + " 12:00:00.00");
			String resultado = "Verificando habitaciones disponibles\n";
			panelDatos.actualizarInterfaz(resultado);
			List<Habitaciones> habs = parranderos.verificarHabitacionesDisponibles(tipo, 1, entrada, salida);
			resultado += "Hay "+habs.size() + " habitaciones disponibles\n";
			if( habs.size()==1 ) {
				Habitaciones hab = habs.get(0);
				resultado+="Adicionando reserva\n";
				Reservas r = parranderos.adicionarReserva( numPersonas, entrada, salida, null, null, cliente.getNum_identidad(), cliente.getTipo_documento(), hab.getNum_hab(), cliente, idPlanCons);
				resultado+="Reserva adicionada!\n";
				resultado += r;
				panelDatos.actualizarInterfaz(resultado);
			}else 
				throw new Exception("La no hay habitaciones dispoinbles del tipo: "+tipo);
		} catch (Exception e) {
			String resultado = "Hubo un error adicionando el consumo\n"+e.getMessage();
			panelDatos.actualizarInterfaz("Hubo un error adicionando el consumo\n");
			panelDatos.actualizarInterfaz(e.getMessage());
			panelDatos.actualizarInterfaz(resultado);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void adicionarServicio(){

		try{
			Usuarios cliente = verificarUsuario(CLIENTE);

			String inicialStr = JOptionPane.showInputDialog (this, "fecha inicial?\n(Ejm: 2019-09-16)", "Adicionar servicio", JOptionPane.OK_OPTION);
			String finalStr = JOptionPane.showInputDialog (this, "fecha final?(Ejm: 2019-09-23)", "Adicionar servicio", JOptionPane.OK_OPTION);
			long idServicio = Long.valueOf(JOptionPane.showInputDialog(this, "Servicio?", "Adicionar servicio", JOptionPane.OK_OPTION));
			String resultado = "Adicionando servicio";
			panelDatos.actualizarInterfaz(resultado);
			Timestamp fecha_inicial = Timestamp.valueOf(inicialStr.trim() + " 06:00:00.00");
			Timestamp fecha_final = Timestamp.valueOf(finalStr.trim() + " 12:00:00.00");
			ReservaServicio rs = parranderos.adicionarReservaServicio(fecha_inicial, fecha_final, cliente.getNum_identidad(), cliente.getTipo_documento(), idServicio);
			rs.getId();
			resultado = "Añadido el servicio \n"+ rs+ "\nAl usuario: "+cliente.getNum_identidad() ;
			panelDatos.actualizarInterfaz(resultado);
		}
		catch( Exception e ){
			panelDatos.actualizarInterfaz("Hubo un error adicionando el consumo\n");
			panelDatos.actualizarInterfaz(e.getMessage());
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			//			e.printStackTrace();
		}
	}

	public void adicionarConsumo() {

		try {
			Usuarios empleado = verificarUsuario(EMPLEADO);

			long idProd = Long.valueOf(JOptionPane.showInputDialog (this, "id del producto?", "Adicionar servicio", JOptionPane.OK_OPTION));
			long idHab = Long.valueOf(JOptionPane.showInputDialog (this, "id de la habitacion?", "Adicionar servicio", JOptionPane.OK_OPTION));

			Timestamp fecha = new Timestamp(System.currentTimeMillis());
			String resultado = "Adicionando consumo";
			panelDatos.actualizarInterfaz(resultado);
			Consumo cons = parranderos.adicionarConsumo( fecha, empleado.getNum_identidad(), empleado.getTipo_documento(), idProd, idHab, empleado);
			resultado = "Añadido el consumo \n"+ cons+ "\nAl usuario: "+empleado.getNum_identidad() ;
			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
			panelDatos.actualizarInterfaz("Hubo un error adicionando el consumo\n");
			panelDatos.actualizarInterfaz(e.getMessage());
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			//				e.printStackTrace();
		}

	}

	public void registrarLlegadaCliente(){
		try{
			Usuarios recepcionista = verificarUsuario(RECEPCIONISTA);

			Usuarios cliente = verificarUsuario(CLIENTE);
			String resultado = "El recepcionista "+recepcionista.getNum_identidad() + "\n";
			resultado += "Ingresara al cliente "+cliente.getNum_identidad() + "\n";
			long idRes = Long.valueOf(JOptionPane.showInputDialog(this, "Id de la reserva?", "Llegada Cliente", JOptionPane.OK_OPTION));
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			parranderos.registrarLlegadaReserva(cliente.getNum_identidad(), cliente.getTipo_documento(), ts, idRes);
			resultado += "La fecha de llegada fue: "+ ts.toString();
			panelDatos.actualizarInterfaz(resultado);

		} catch(Exception e){
			panelDatos.actualizarInterfaz("Hubo un error registrando le llegada del cliente\n");
			panelDatos.actualizarInterfaz(e.getMessage());
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			//				e.printStackTrace();
		}

	}

	public void registrarSalidaCliente(){
		try{
			Usuarios recepcionista = verificarUsuario(RECEPCIONISTA);

			Usuarios cliente = verificarUsuario(CLIENTE);
			String resultado = "El recepcionista "+recepcionista.getNum_identidad() + "\n";
			resultado += "Da salida al cliente "+cliente.getNum_identidad() + "\n";
			long idRes = Long.valueOf(JOptionPane.showInputDialog(this, "Id de la reserva?", "Salida Cliente", JOptionPane.OK_OPTION));
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			parranderos.registrarSalidaReserva(cliente.getNum_identidad(), cliente.getTipo_documento(), ts, idRes);
			resultado += "La fecha de llegada fue: "+ ts.toString();
			panelDatos.actualizarInterfaz(resultado);
		} catch(Exception e){
			String resultado = "Hubo un error registrando le llegada del cliente\n"+e.getMessage();
			panelDatos.actualizarInterfaz(resultado);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			//				e.printStackTrace();
		}
	}

	public void registrarConvencion(){

		try {
			Usuarios organizador = verificarUsuario(ORGANIZADOR);

			boolean sePuede = true;
			int[] tiposServ = new int[20];

			//Pidiendo datos necesarios
			int indice = (int)parranderos.indiceUltimoUsuario();
			String nombre = JOptionPane.showInputDialog (this, "Nombre?", "Registra convencion", JOptionPane.QUESTION_MESSAGE);
			int cantidadPersonas = Integer.parseInt(JOptionPane.showInputDialog (this, "Cantidad personas?\nRecuerda que solo se generaran\n"+(1000-indice), "Registra convencion", JOptionPane.QUESTION_MESSAGE));
			long idPlanCons = Long.valueOf(JOptionPane.showInputDialog (this, "Plan de consumo?", "Registra convencion", JOptionPane.QUESTION_MESSAGE));
			String entradaStr = JOptionPane.showInputDialog (this, "fecha entrada?\n(Ejm: 2019-09-16)", "Adicionar convencion", JOptionPane.OK_OPTION);
			String salidaStr = JOptionPane.showInputDialog (this, "fecha salida?(Ejm: 2019-09-23)", "Adicionar convencion", JOptionPane.OK_OPTION);
			int tiposHab = Integer.parseInt(JOptionPane.showInputDialog (this, "Cuantos tipos de habitacion?", "Registra convencion", JOptionPane.QUESTION_MESSAGE));
			Timestamp entrada = Timestamp.valueOf(entradaStr.trim() + " 06:00:00.00");
			Timestamp salida = Timestamp.valueOf(salidaStr.trim() + " 12:00:00.00");

			List<Habitaciones> listaDeListasDeHabs= new LinkedList<Habitaciones>();
			List<ReservaServicio> listaReservaServicio = new LinkedList<ReservaServicio>();

			panelDatos.actualizarInterfaz("Verificando que las habitaciones y los servicios tengan suficiente espacio para la convencion");
			for( int i=0; i<tiposHab && sePuede; i++ ){
				long tipo = Long.valueOf(JOptionPane.showInputDialog (this, "Tipo?", "Registra convencion", JOptionPane.QUESTION_MESSAGE));
				int cantidad = Integer.parseInt(JOptionPane.showInputDialog (this, "Cantidad del tipo "+tipo +"?", "Registra convencion", JOptionPane.QUESTION_MESSAGE));
				List<Habitaciones> actual = parranderos.darHabitacionesDisponibles(cantidad, tipo, entrada, salida);
				listaDeListasDeHabs.addAll(actual);
				sePuede = actual.size() >= cantidad;
			}
			tiposHab = Integer.parseInt(JOptionPane.showInputDialog (this, "Cuantos tipos de servicio?", "Registra convencion", JOptionPane.QUESTION_MESSAGE));
			for( int i=0; i<tiposHab && sePuede; i++ ){
				long tipo = Long.valueOf(JOptionPane.showInputDialog (this, "Tipo?", "Registra convencion", JOptionPane.QUESTION_MESSAGE));
				Servicios s = parranderos.darServicio(tipo);
				boolean b2 = s.getCapacidad()>=cantidadPersonas;
				sePuede = sePuede && b2;
				listaReservaServicio.add(new ReservaServicio(indice+i+1, entrada, salida, organizador.getNum_identidad(), organizador.getTipo_documento(), tipo));
				tiposServ[i]=(int) tipo;
			}
			if(!sePuede)
			{
				JOptionPane.showMessageDialog(this, "No hay suficientes habitaciones o servicios", "Error", JOptionPane.WARNING_MESSAGE);
				panelDatos.actualizarInterfaz("No hay suficientes habitaciones o servicios");
			} else{

				Convencion conv = parranderos.adicionarConvencion( nombre, cantidadPersonas, idPlanCons, organizador.getNum_identidad(), organizador.getTipo_documento() );

				for(int i=0; i<cantidadPersonas-1; i++){
					Usuarios user = new Usuarios((long) (indice+i+1), "cedula", nombre, nombre, 5, conv.getId());
					parranderos.adicionarUsuario(user);
					Reservas r = parranderos.adicionarReserva( 1, entrada, salida, null, null, (long)(indice+i+1), "cedula", listaDeListasDeHabs.get(i).getNum_hab(), user, idPlanCons);				
				} 
				for(int j=0; j<tiposHab; j++){
					parranderos.reservarServicios(listaReservaServicio, tiposServ[j]);
				}

			}

		}
		catch (Exception e) {
			//e.printStackTrace();
			JOptionPane.showMessageDialog(this,e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
		}


	}

	public void cancelarReservaConvencion()
	{
		String idConvencionStr = JOptionPane.showInputDialog (this, "id convencion a cancelar", "Cancelar convencion", JOptionPane.QUESTION_MESSAGE);
		Long idConvencion = Long.valueOf(idConvencionStr);
		parranderos.darConvencion(idConvencion);
		String cancelarTodo = JOptionPane.showInputDialog (this, "Desea cancelar toda la convencion", "Cancelar convencion", JOptionPane.QUESTION_MESSAGE);
		if(cancelarTodo.equals("si"))
		{
			List<Usuarios> users = parranderos.darUsuariosConvencion(idConvencion);
			int contador = 0;
			for (Usuarios object : users) {
				long NUM_IDENTIDAD = object.getNum_identidad();
				String TIPO_DOCUMENTO = object.getTipo_documento();
				parranderos.eliminarReserva(NUM_IDENTIDAD, TIPO_DOCUMENTO);
				parranderos.cancelarReservasServicios(NUM_IDENTIDAD, TIPO_DOCUMENTO);
				parranderos.eliminarUsuario(NUM_IDENTIDAD, TIPO_DOCUMENTO);
				System.out.println("eliminando... "+  contador ++);
			}
			parranderos.cancelarConvencion(idConvencion);
		}
		else 
		{
			String cuantos = JOptionPane.showInputDialog (this, "Cuantas personas desean cancelar?", "Cancelar convencion", JOptionPane.QUESTION_MESSAGE);
			for (int i = 0; i < Integer.valueOf(cuantos); i++) {
				String numIdentidadStr = JOptionPane.showInputDialog (this, "Ingrese el numero de identidad del cliente a cancelar", "Cancelar convencion", JOptionPane.QUESTION_MESSAGE);
				String TipoDoc = JOptionPane.showInputDialog (this, "Ingrese el tipo de documento", "Cancelar convencion", JOptionPane.QUESTION_MESSAGE);
				Long numIdentidad = Long.valueOf(numIdentidadStr);
				parranderos.eliminarReserva(numIdentidad, TipoDoc);
				parranderos.cancelarReservasServicios(numIdentidad, TipoDoc);
				parranderos.eliminarUsuario(numIdentidad, TipoDoc);

				System.out.println("eliminando... "+  i);

			}
		}
	}

	public void registrarLlegadaConvencion(){
		System.out.println("Registrar Llegada Convencion");

	}




	public void registrarSalidaConvencion(){
		try{
			Usuarios organizador = verificarUsuario(ORGANIZADOR);
			long idConv = Long.valueOf(JOptionPane.showInputDialog (this, "Id convencion?", "Terminar convencion", JOptionPane.QUESTION_MESSAGE));
			Convencion c = (Convencion) parranderos.darConvencion(idConv);
			if( c.getNum_personas() == organizador.getNum_identidad() && c.getTipo_documento_usuario().equals(organizador.getTipo_documento()))
				parranderos.registrarSalidaConvencion(idConv);
			else 
				JOptionPane.showMessageDialog(this, "El organizador no creo tal convencion", "Error", JOptionPane.WARNING_MESSAGE);
		} catch(Exception e){
			panelDatos.actualizarInterfaz("Hubo un error registrando le llegada del cliente\n");
			panelDatos.actualizarInterfaz(e.getMessage());
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			//				e.printStackTrace();
		}

	}

	public Usuarios verificarUsuario( long tipoUsuario ) throws NumberFormatException, Exception{
		try{
			Usuarios user =null;

			String numIden = JOptionPane.showInputDialog (this, "numero identificacion?", "Verificacion Usuario", JOptionPane.QUESTION_MESSAGE);
			String tipoDoc = (String) JOptionPane.showInputDialog(this,
					"Seleccione la unidad de tiempo",
					"Analizar la operacion de Hotel Andes",
					JOptionPane.QUESTION_MESSAGE,
					null,  // null para icono defecto
					new String[] { "cedula", "pasaporte" }, 
					"cedula");

			user = parranderos.darUsuario(Long.valueOf(numIden), tipoDoc);

			if( user.getTipo_usuario() == tipoUsuario ){
				return user;
			} 
			else throw new Exception("El usuario no es del tipo "+tipoUsuario );
		}
		catch( Exception e ){
			throw new Exception(e.getMessage());
		}
	}

	public Usuarios verificarUsuario( long tipoUsuario, long tipoUsuario2, long tipoUsuario3 ) throws NumberFormatException, Exception{
		try{
			Usuarios user =null;

			String numIden = JOptionPane.showInputDialog (this, "numero identificacion?", "Verificacion Usuario", JOptionPane.QUESTION_MESSAGE);
			String tipoDoc = (String) JOptionPane.showInputDialog(this,
					"Seleccione la unidad de tiempo",
					"Analizar la operacion de Hotel Andes",
					JOptionPane.QUESTION_MESSAGE,
					null,  // null para icono defecto
					new String[] { "cedula", "pasaporte" }, 
					"cedula");

			user = parranderos.darUsuario(Long.valueOf(numIden), tipoDoc);

			if( user.getTipo_usuario() == tipoUsuario || user.getTipo_usuario() == tipoUsuario2 || user.getTipo_usuario() == tipoUsuario3 ){
				return user;
			} 
			else throw new Exception("El usuario no es del tipo "+tipoUsuario );
		}
		catch( Exception e ){
			throw new Exception(e.getMessage());
		}
	}

	public void crearMantenimiento() throws Exception {

		Usuarios admin = verificarUsuario(ADMINISTRADOR);
		if( admin == null )throw new Exception("Admin es null");
		int num = Integer.parseInt(JOptionPane.showInputDialog(this, "1. Servicio\n2. Habitacion", "Mantenimiento", JOptionPane.OK_OPTION));
		long servOHab = -1;
		if( num==1 ){
			servOHab = Long.valueOf(JOptionPane.showInputDialog(this, "id del servicio?", "Mantenimiento", JOptionPane.OK_OPTION));
		}else if( num==2 ){
			servOHab = Long.valueOf(JOptionPane.showInputDialog(this, "Numero de la habitacion?", "Mantenimiento", JOptionPane.OK_OPTION));
		}
		String entradaStr = JOptionPane.showInputDialog (this, "fecha entrada?\n(Ejm: 2019-09-16)", "Mantenimiento", JOptionPane.OK_OPTION);
		String salidaStr = JOptionPane.showInputDialog (this, "fecha salida?(Ejm: 2019-09-23)", "Mantenimiento", JOptionPane.OK_OPTION);

		Timestamp entrada = Timestamp.valueOf(entradaStr.trim() + " 06:00:00.00");
		Timestamp salida = Timestamp.valueOf(salidaStr.trim() + " 12:00:00.00");
		if( servOHab==-1 ){
			JOptionPane.showMessageDialog(this, "Error ponga 1 o 2");
		} else{
			try {
				parranderos.crearMantenimiento( num, admin, entrada, salida, servOHab );
			} catch (Exception e) {
				//				e.printStackTrace();
			}
		}
	}

	public void terminarMantenimiento()
	{
		try{
			Usuarios administrador = verificarUsuario(ADMINISTRADOR);
			String datos = JOptionPane.showInputDialog (this, "Terminar mantenimiento de que habitaciones o servicios, ingrese los datos de la siguiente manera: \n H201, (para las habitaciones) S1(para los servicios) (con coma y espacio entre los datos) ", "Terminar mantenimiento", JOptionPane.QUESTION_MESSAGE);
			String[] arreglo = datos.split(", ");
			for (int i = 0; i < arreglo.length; i++) {
				String actual = arreglo[i];

				if(actual.contains("H"))
				{
					String numHabStr = actual.substring(1);
					int numHab = Integer.valueOf(numHabStr);
					parranderos.terminarMantenimientoHab(administrador.getNum_identidad(), administrador.getTipo_documento(), numHab);
				}
				else
				{
					String idServSTR = actual.substring(1);
					int idServ = Integer.valueOf(idServSTR);
					parranderos.terminarMantenimientoServ(administrador.getNum_identidad(), administrador.getTipo_documento(), idServ);
				}
			}
		} catch(Exception e){
			String resultado = "Hubo un error registrando le llegada del cliente\n" + e.getMessage();
			panelDatos.actualizarInterfaz(resultado);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			//				e.printStackTrace();
		}
	}

	public void analizarOperacionesDeHoteles(){
		String resultado = "";
		try{
			Object seleccion = JOptionPane.showInputDialog(this,
					"Seleccione la unidad de tiempo",
					"Analizar la operacion de Hotel Andes",
					JOptionPane.QUESTION_MESSAGE,
					null,  // null para icono defecto
					new Object[] { "Semana", "Mes" }, 
					"Semana");
			System.out.println("El usuario ha elegido "+seleccion);
			resultado = "El usuario ha elegido "+seleccion;

			String tipoTiempo = seleccion.equals("Semana") ? "WW" : "MM";

			seleccion = JOptionPane.showInputDialog(this,
					"Tipo de habitacion",
					"Analizar la operacion de Hotel Andes",
					JOptionPane.QUESTION_MESSAGE,
					null,  // null para icono defecto
					new Object[] { 1, 2, 3, 4, 5 }, 
					1);

			int tipoHab = (int) seleccion;
			System.out.println("El usuario ha elegido "+seleccion);
			resultado = "El usuario ha elegido el tipo de habitacion"+seleccion;

			long idServicio = Long.valueOf(JOptionPane.showInputDialog(this, "Servicio (Ejm: 3. (1-12))?", "Adicionar servicio", JOptionPane.OK_OPTION));
			System.out.println("El usuario ha elegido "+idServicio);
			resultado = "El usuario ha elegido "+idServicio;

			Object[] o = parranderos.reqFC6( tipoHab, idServicio, tipoTiempo );
			resultado = "Con un total de reservas de "+o[0] + ", \nla semana "+o[2] + " tiene la mayor cantidad de demanda";
		} 
		catch( Exception e ){
			resultado = "Hubo un error registrando le llegada del cliente\n" + e.getMessage();
			panelDatos.actualizarInterfaz(resultado);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			JOptionPane.showMessageDialog(this, "Hubo un error registrando le llegada del cliente", "Error", JOptionPane.WARNING_MESSAGE);

			//				e.printStackTrace();
		}

		panelDatos.actualizarInterfaz(resultado);
	}

	public void buenosClientes()
	{
		try 
		{
			System.out.println("buscando buenos clientes...");

			for (Object id : parranderos.buscarBuenosClientesPorConsumo()) 
			{
				Object[] cosa = (Object[])id;

				Usuarios bueno = parranderos.darUsuario(((Long)cosa[0]), (String)cosa[1]);
				System.out.println("usuario bueno :");
				System.out.println(bueno);
			}
		}catch (Exception e) {
			//			e.printStackTrace();
		}
	}

	public void consultarConsumoEnHotelAndes(){

		String resultado = "";
		try{

			verificarUsuario(GERENTE, ORGANIZADOR, RECEPCIONISTA);

			String servicio = (String) JOptionPane.showInputDialog(this, "Seleccione el servicio", "Consultar consumo en Hotel Andes", JOptionPane.QUESTION_MESSAGE, null,  // null para icono defecto
					new String[] { "1. Piscina", "2. Gimnasio", 
							"3. Internet", "4. Bar", "5. Restaurante", "6. Supermercad", "7. Tienda", "8. Spa",
							"9. Lavado", "10. Utensilio", "11. Salon reunion", "12. Salon conferencia", "13. Planchado", "14. Embolada"}, 
					"1. Piscina");
			servicio = servicio.split(". ")[0];

			int mes1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
			int dia1 = ThreadLocalRandom.current().nextInt(1, 15 + 1);

			int mes2 = ThreadLocalRandom.current().nextInt(mes1, 12 + 1);
			int dia2 = ThreadLocalRandom.current().nextInt(dia1, 28 + 1);

			String entradaStr = JOptionPane.showInputDialog (this, "fecha entrada?\n(Ejm: 2019-"+mes1+"-"+dia1+")", "Consultar consumo en Hotel Andes", JOptionPane.OK_OPTION);
			String salidaStr = JOptionPane.showInputDialog (this, "fecha salida?\n(Ejm: 2019-"+mes2+"-"+dia2+")", "Consultar consumo en Hotel Andes", JOptionPane.OK_OPTION);

			Timestamp entrada = Timestamp.valueOf(entradaStr.trim() + " 00:00:00.00");
			Timestamp salida = Timestamp.valueOf(salidaStr.trim() + " 23:59:59.00");

			JCheckBox cbAgrup = new JCheckBox("Agrupamiento"); 
			JCheckBox cbOrden = new JCheckBox("Ordenamiento");
			String message = "Citerio de ordenamiento (se puede seleccionar mas de uno)";
			Object[] params1 = {message, cbAgrup, cbOrden};
			JOptionPane.showConfirmDialog(this, params1, "Consultar consumo en Hotel Andes", JOptionPane.YES_NO_OPTION);

			JCheckBox cb1 = new JCheckBox("Datos del cliente", false); 
			JCheckBox cb2 = new JCheckBox("Fecha", false);
			JCheckBox cb3 = new JCheckBox("Numero de veces que usa el servicio", false);

			if( cbOrden.isSelected() ){
				message = "Citerio de ordenamiento (se puede seleccionar mas de uno)";
				Object[] params = {message, cb1, cb2, cb3};
				JOptionPane.showConfirmDialog(this, params, "Co0nsultar consumo en Hotel Andes", JOptionPane.YES_NO_OPTION);
			}

			boolean[] tipoClasificacion = {cbAgrup.isSelected(), cbOrden.isSelected() };
			boolean[] tipoOrdenamiento = {cb1.isSelected(), cb2.isSelected(), cb3.isSelected()};
			List<Usuarios> clientes = parranderos.reqCF9( servicio, entrada, salida, tipoClasificacion, tipoOrdenamiento );
			resultado += "Servicio = "+servicio + "\n";
			resultado += "---------------------------------------\n";
			resultado += "--------------Clientes-----------------\n";
			for (Usuarios usuario : clientes) {
				resultado += "-  " + usuario + "\n\n";
			}
			panelDatos.actualizarInterfaz(resultado);
		}
		catch( Exception e ){
			resultado = "Hubo un error registrando le llegada del cliente\n" + e.getMessage();
			panelDatos.actualizarInterfaz(resultado);
			JOptionPane.showMessageDialog(this, "Hubo un error en el sistema", "Error", JOptionPane.WARNING_MESSAGE);

			//							e.printStackTrace();
		}


	}

	public void consultarFuncionamiento(){
		String resultado = "";

		try {
			verificarUsuario(GERENTE);
			Object[] listaO = parranderos.reqCF11();
			
			resultado += "CANTIDAD MAXIMOS HABITACIONES SOLICITADAS POR SEMANA\n";
			resultado += "Semana-cantidad de consumos-numero de la habitacion\n";
			//Max
			List<Object> habitacionSolicitadas = (List<Object>)listaO[0];
			for (Object o : habitacionSolicitadas) {
				Object[] datos = (Object[]) o;
				resultado += datos[0].toString() + " - ";
				resultado += datos[1].toString() + " - ";
				resultado += datos[2].toString() + "\n\n";
			}
			
			resultado += "CANTIDAD MINIMA HABITACIONES SOLICITADAS POR SEMANA\n";
			resultado += "Semana-cantidad de consumos-numero de la habitacion\n";
			//Max
			habitacionSolicitadas = (List<Object>)listaO[1];
			for (Object o : habitacionSolicitadas) {
				Object[] datos = (Object[]) o;
				resultado += datos[0].toString() + " - ";
				resultado += datos[1].toString() + " - ";
				resultado += datos[2].toString() + "\n\n";
			}
			
			resultado += "CANTIDAD MAXIMOS CONSUMOS POR SEMANA\n";
			resultado += "Semana-cantidad de consumos-id del servicio\n";
			//Max
			List<Object> cons = (List<Object>)listaO[2];
			for (Object o : cons) {
				Object[] datos = (Object[]) o;
				resultado += datos[0].toString() + " - ";
				resultado += datos[1].toString() + " - ";
				resultado += datos[2].toString() + "\n\n";
			}
			
			resultado += "CANTIDAD MINIMOS CONSUMOS POR SEMANA\n";
			resultado += "Semana-cantidad de consumos-id del servicio\n";
			//Min
			cons = (List<Object>)listaO[3];
			for (Object o : cons) {
				Object[] datos = (Object[]) o;
				resultado += datos[0].toString() + " - ";
				resultado += datos[1].toString() + " - ";
				resultado += datos[2].toString() + "\n\n";
			}
			
			panelDatos.actualizarInterfaz(resultado);
			
		} catch (Exception e) {

			resultado = "Hubo un error registrando le llegada del cliente\n" + e.getMessage();
			panelDatos.actualizarInterfaz(resultado);
			JOptionPane.showMessageDialog(this, "Hubo un error en el sistema", "Error", JOptionPane.WARNING_MESSAGE);

			//			e.printStackTrace();
		}


	}

	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogParranderos ()
	{
		mostrarArchivo ("parranderos.log");
	}

	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}

	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogParranderos ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("parranderos.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
			// Ejecución de la demo y recolección de los resultados
			long eliminados [] = parranderos.limpiarParranderos();

			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Gustan eliminados\n";
			resultado += eliminados [1] + " Sirven eliminados\n";
			resultado += eliminados [2] + " Visitan eliminados\n";
			resultado += eliminados [3] + " Bebidas eliminadas\n";
			resultado += eliminados [4] + " Tipos de bebida eliminados\n";
			resultado += eliminados [5] + " Bebedores eliminados\n";
			resultado += eliminados [6] + " Bares eliminados\n";
			resultado += "\nLimpieza terminada";

			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}

	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual Parranderos.pdf");
	}

	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Esquema BD Parranderos.pdf");
	}

	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/EsquemaParranderos.sql");
	}

	/**
	 * Muestra la arquitectura de referencia para Parranderos
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}

	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}

	/**
	 * Muestra la información acerca del desarrollo de esta apicación
	 */
	public void acercaDe ()
	{
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versión 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: Parranderos Uniandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @author Allan Corinaldi y Andres Gonzalez\n";
		resultado += " * Julio de 2018\n";
		resultado += " * \n";
		resultado += " * Revisado por: Claudia Jiménez, Christian Ariza\n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
	}


	/* ****************************************************************
	 * 			Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/


	/**
	 * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
	 * @param e - La excepción recibida
	 * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
			//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
	/**
	 * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
	 * Invoca al método correspondiente según el evento recibido
	 * @param pEvento - El evento del usuario
	 */
	@Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
		try 
		{
			System.out.println(evento);
			Method req = InterfazHotelAndesApp.class.getMethod ( evento );	
			req.invoke ( this );
		} 
		catch( Exception e ){
			String resultado = "El metodo invocado no existe\n" + e.getMessage();
			panelDatos.actualizarInterfaz(resultado);
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
			//				e.printStackTrace();
		}
	}



	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
	/**
	 * Este método ejecuta la aplicación, creando una nueva interfaz
	 * @param args Arreglo de argumentos que se recibe por línea de comandos
	 */
	public static void main( String[] args )
	{
		try
		{

			// Unifica la interfaz para Mac y para Windows.
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
			InterfazHotelAndesApp interfaz = new InterfazHotelAndesApp( );
			interfaz.setVisible( true );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}
}
