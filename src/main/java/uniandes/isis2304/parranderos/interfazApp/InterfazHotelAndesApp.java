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
import java.sql.Timestamp;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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

	public static final long RECEPCIONISTA = 3;

	public static final long EMPLEADO = 4;

	public static final long CLIENTE = 5;

	public static final long ORGANIZADOR = 6;


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
			JOptionPane.showConfirmDialog(this, "Antes recuerda que hay 5 tipos de usuario:\n1. gerente\n2.administrador\n3. recepcionista\n4. empleado\n5.cliente", "Adicionar cliente",JOptionPane.INFORMATION_MESSAGE);
			String num_identidad = JOptionPane.showInputDialog (this, "id?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
			String tipo_documento = JOptionPane.showInputDialog (this, "tipo doc?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
			String nombre = JOptionPane.showInputDialog (this, "Nombre?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
			String apellido = JOptionPane.showInputDialog (this, "Apellido?", "AAdicionar cliente", JOptionPane.QUESTION_MESSAGE);
			String tipo_usuario = JOptionPane.showInputDialog (this, "tipo usuario?", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
			String id_convencion = JOptionPane.showInputDialog(this, "Convencion? (0)", "Adicionar cliente", JOptionPane.QUESTION_MESSAGE);
			if (num_identidad != null)
			{
				Usuarios usuario = new Usuarios(num_identidad, tipo_documento, nombre, apellido, tipo_usuario, id_convencion);
				Usuarios user = parranderos.adicionarUsuario( usuario );
				if (user == null)
				{
					throw new Exception ("No se pudo crear un tipo de bebida con nombre: " + nombre);
				}
				String resultado = "En adicionarTipoBebida\n\n";
				resultado += "Tipo de bebida adicionado exitosamente: " + user;
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
		System.out.println("Adivionar Reserva");
		Usuarios cliente = verificarUsuario(CLIENTE);

		long id = Long.valueOf(JOptionPane.showInputDialog (this, "id de la reserva?", "Adicionar reserva", JOptionPane.OK_OPTION));
		int numPersonas = Integer.valueOf(JOptionPane.showInputDialog (this, "Cantidad personas?", "Adicionar reserva", JOptionPane.OK_OPTION));
		String entradaStr = JOptionPane.showInputDialog (this, "fecha entrada?\n(Ejm: 2019-09-16)", "Adicionar reserva", JOptionPane.OK_OPTION);
		String salidaStr = JOptionPane.showInputDialog (this, "fecha salida?(Ejm: 2019-09-23)", "Adicionar reserva", JOptionPane.OK_OPTION);
		long numHab = Long.valueOf(JOptionPane.showInputDialog (this, "habitacion id?", "Adicionar reserva", JOptionPane.OK_OPTION));
		long idPlanCons = Long.valueOf(JOptionPane.showInputDialog (this, "plan consumo id?(0 si no tiene)", "Adicionar reserva", JOptionPane.OK_OPTION));
		try {
			Timestamp entrada = Timestamp.valueOf(entradaStr.trim() + " 06:00:00.00");
			Timestamp salida = Timestamp.valueOf(salidaStr.trim() + " 12:00:00.00");
			parranderos.adicionarReserva(id, numPersonas, entrada, salida, null, null, cliente.getNum_identidad(), cliente.getTipo_documento(), numHab, cliente, idPlanCons);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void adicionarServicio(){
		System.out.println("Adicionar Servicio");
		Usuarios cliente = verificarUsuario(CLIENTE);
		int consumoOReservaServicio = Integer.valueOf(JOptionPane.showInputDialog (this, "1. Consumo\n2. Servicio", "Adicionar servicio", JOptionPane.OK_OPTION));
		hacerServicio(consumoOReservaServicio, cliente);



		//		if(ser == null){
		//			JOptionPane.showMessageDialog(this, "No existe el servicio solicitado", "Error", JOptionPane.WARNING_MESSAGE);
		//		} else{
		//			int carHab = Integer.valueOf(JOptionPane.showInputDialog (this, "reservado habitacion?", "Adicionar reserva", JOptionPane.OK_OPTION));
		//			ser.setCargado_habitacion(carHab);
		//			ser.setReservado(Servicios.SI_RESERVADO);
		//
		//		}
	}

	private void hacerServicio(int consumoOReservaServicio, Usuarios cliente) {
		if( consumoOReservaServicio == 1 ){

			try {
				long id = Long.valueOf(JOptionPane.showInputDialog (this, "id del consumo?", "Adicionar servicio", JOptionPane.OK_OPTION));
				long idProd = Long.valueOf(JOptionPane.showInputDialog (this, "id del producto?", "Adicionar servicio", JOptionPane.OK_OPTION));
				long idHab = Long.valueOf(JOptionPane.showInputDialog (this, "id de la habitacion?", "Adicionar servicio", JOptionPane.OK_OPTION));

				Timestamp fecha = new Timestamp(System.currentTimeMillis());
				Consumo cons = parranderos.adicionarConsumo(id, fecha, cliente.getNum_identidad(), cliente.getTipo_documento(), idProd, idHab, cliente);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		else if( consumoOReservaServicio == 2 ){
			long id = Long.valueOf(JOptionPane.showInputDialog (this, "id del servicio?", "Adicionar servicio", JOptionPane.OK_OPTION));
			String inicialStr = JOptionPane.showInputDialog (this, "fecha inicial?\n(Ejm: 2019-09-16)", "Adicionar servicio", JOptionPane.OK_OPTION);
			String finalStr = JOptionPane.showInputDialog (this, "fecha final?(Ejm: 2019-09-23)", "Adicionar servicio", JOptionPane.OK_OPTION);
			long idServicio = Long.valueOf(JOptionPane.showInputDialog(this, "Servicio?", "Adicionar servicio", JOptionPane.OK_OPTION));

			Timestamp fecha_inicial = Timestamp.valueOf(inicialStr.trim() + " 06:00:00.00");
			Timestamp fecha_final = Timestamp.valueOf(finalStr.trim() + " 12:00:00.00");
			ReservaServicio rs = parranderos.adicionarReservaServicio(id, fecha_inicial, fecha_final, cliente.getNum_identidad(), cliente.getTipo_documento(), idServicio);
		}

		

	}

	public void registrarLlegadaCliente(){
		System.out.println("Registrar Llegada Cliente");
		Usuarios recepcionista = verificarUsuario(RECEPCIONISTA);
		Usuarios cliente = verificarUsuario(CLIENTE);

	}

	public void registrarConsumoCliente(){
	}

	public void registrarConvencion(){
		System.out.println("Registrar Convencion");
		Usuarios organizador = verificarUsuario(ORGANIZADOR);
		if( organizador == null){
			JOptionPane.showMessageDialog(this,"Organizador no existe", "Error", JOptionPane.WARNING_MESSAGE);
		} else{
			boolean sePuede = true;
			long id = Long.valueOf(JOptionPane.showInputDialog (this, "id convencion?", "Registrar convencion", JOptionPane.QUESTION_MESSAGE));
			String nombre = JOptionPane.showInputDialog (this, "Nombre?", "Registra convencion", JOptionPane.QUESTION_MESSAGE);
			int cantidadPersonas = Integer.parseInt(JOptionPane.showInputDialog (this, "Cantidad personas?", "Registra convencion", JOptionPane.QUESTION_MESSAGE));
			long idPlanCons = Long.valueOf(JOptionPane.showInputDialog (this, "Plan de consumo?", "Registra convencion", JOptionPane.QUESTION_MESSAGE));
			int tiposHab = Integer.parseInt(JOptionPane.showInputDialog (this, "Cuantos tipos de habitacion?", "Registra convencion", JOptionPane.QUESTION_MESSAGE));
			for( int i=0; i<tiposHab && sePuede; i++ ){
				 long tipo = Long.valueOf(JOptionPane.showInputDialog (this, "Tipo?", "Registra convencion", JOptionPane.QUESTION_MESSAGE));
				 int cantidad = Integer.parseInt(JOptionPane.showInputDialog (this, "Cantidad del tipo "+tipo +"?", "Registra convencion", JOptionPane.QUESTION_MESSAGE));
				 sePuede = parranderos.verificarHabitacionesDisponibles(tipo, cantidad);
			}
			
			Convencion conv = parranderos.adicionarConvencion(id, nombre, cantidadPersonas, idPlanCons, organizador.getNum_identidad(), organizador.getTipo_documento() );
		}
	}
	
	public Usuarios verificarUsuario( long tipoUsuario ){
		Usuarios user =null;
		String numIden = JOptionPane.showInputDialog (this, "numero identificacion?", "Verificacion Usuario", JOptionPane.QUESTION_MESSAGE);
		String tipoDoc = JOptionPane.showInputDialog (this, "tipo documento?\ncedula\npasaporte", "Verificacion Usuario", JOptionPane.QUESTION_MESSAGE);
		try {
			user = parranderos.darUsuario(Integer.valueOf(numIden), tipoDoc);
			if( user == null && user.getTipo_usuario() == tipoUsuario){
				JOptionPane.showMessageDialog(this, "Error solo los recepcionistas tienen acceso o no esta registrado en la base de datos");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
		}
		return user;
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
			// TODO Auto-generated catch block
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
			Method req = InterfazHotelAndesApp.class.getMethod ( evento );	
			System.out.println(evento);
			req.invoke ( this );
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
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
