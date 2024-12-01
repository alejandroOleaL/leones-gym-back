package app.netlify.leones.gym.back.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.netlify.leones.gym.back.component.ClienteComponent;
import app.netlify.leones.gym.back.models.entity.Cliente;
import app.netlify.leones.gym.back.models.entity.Datos;
import app.netlify.leones.gym.back.models.entity.Historial;
import app.netlify.leones.gym.back.models.entity.Operacion;
import app.netlify.leones.gym.back.models.entity.Periodo;
import app.netlify.leones.gym.back.models.services.EmailService;
import app.netlify.leones.gym.back.models.services.IClienteService;
import app.netlify.leones.gym.back.models.services.IHistorialService;
import app.netlify.leones.gym.back.models.services.IOperacionesService;
import app.netlify.leones.gym.back.models.services.IUploadFileService;
import app.netlify.leones.gym.back.models.services.QRCodeService;

@CrossOrigin(origins = { "http://localhost:4200", "https://leonesgym.web.app", "http://localhost:8090" })
@RestController
@RequestMapping("/leonesgym")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IHistorialService historialService;

	@Autowired
	private IUploadFileService uploadService;

	@Autowired
	private QRCodeService qrCodeService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ClienteComponent component;
	
	@Autowired
	private IOperacionesService operaciones;

	@GetMapping("/telefono/{telefono}")
	public Cliente buscarPorTelefono(@PathVariable String telefono) {
		return clienteService.findByTelefono(telefono);
	}

	@GetMapping("/clientes")
	public List<Cliente> index() {
		return clienteService.findAll();
	}

	@GetMapping("/clientes/datos")
	public Datos datos() {
		Datos datos = new Datos();
		datos.setVisitasHoy(historialService.findClientesVisitas());
		datos.setInactivos(clienteService.findCountClientesVencidos());
		datos.setActivos(clienteService.findCountClientesActivos());
		datos.setTotal(clienteService.findCountClientesTotal());
		datos.setRegistros(clienteService.findCountClientesHoyRegistros());
		datos.setVisitasHoyReg(clienteService.findCountVisitasHoy());
		
		int count = clienteService.findCountMes(restarDiasAFecha(40));
		System.out.println("REGISTROS MES: " + count);
		datos.setRegistrosMes(count);
		
		datos.setRegistrosSemana(clienteService.findCountSemana(restarDiasAFecha(40)));
		datos.setRegistrosQuincena(clienteService.findCountQuincena(restarDiasAFecha(40)));
		datos.setRegistrosBimestre(clienteService.findCountBimestre(restarDiasAFecha(40)));
		datos.setRegistrosTrimestre(clienteService.findCounttrimestre(restarDiasAFecha(40)));
		datos.setRegistrosSemestre(clienteService.findCounttrimestre(restarDiasAFecha(40)));
		datos.setRegistrosAnual(clienteService.findCountAnual(restarDiasAFecha(40)));
		datos = component.obtenerSaldos(datos);
		return datos;
	}

	@GetMapping("/clientes/vencidos/page/{page}")
	public Page<Cliente> clientesVencidos(@PathVariable Integer page) {
		return clienteService.findAllClientesVencidos(PageRequest.of(page, 8));
	}
	
	@GetMapping("/clientes/activos/page/{page}")
	public Page<Cliente> clientesActivos(@PathVariable Integer page) {
		return clienteService.findAllClientesActivos(PageRequest.of(page, 8));
	}

	@GetMapping("/historial/page/{page}")
	public Page<Historial> historial(@PathVariable Integer page) {
		historialService.deleteHistorial(restarDiasAFecha(3));
		
		Page<Historial> historial = historialService.findAll(PageRequest.of(page, 8));
		//Eliminar historial
		
		return historial;
	}

	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page) {
		return clienteService.findAllClientesTodos(PageRequest.of(page, 8));
	}
	
	@GetMapping("/clientes/registros/{page}")
	public Page<Cliente> clientesRegistros(@PathVariable Integer page) {
		return clienteService.findAllClientesRegistros(PageRequest.of(page, 8));
	}
	
	@GetMapping("/operaciones/page/{page}")
	public Page<Operacion> obtenerOperaciones(@PathVariable Integer page) {
		operaciones.deleteOperacion(restarDiasAFecha(31));
		
		return operaciones.findAll(PageRequest.of(page, 8));
	}

	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> verCliente(@PathVariable Long id) {
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		try {
			cliente = clienteService.findById(id);

			boolean estatus = validarEstatus(cliente);
			cliente.setEstatus(estatus);

		} catch (Exception e) {
			response.put("mensaje", "Error al consultar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@GetMapping("/clientes/qr/{id}")
	public ResponseEntity<?> mostrarCliente(@PathVariable Long id) {
		Cliente cliente;
		Map<String, Object> response = new HashMap<>();
		try {

			cliente = clienteService.findById(id);
			if(cliente == null || Objects.isNull(cliente)) {
				cliente = new Cliente();
				cliente.setExiste(false);
				cliente.setId((long) 0);
			}
			else {
				cliente.setExiste(true);
			}

		} catch (Exception e) {
			System.out.println(e);
			response.put("mensaje", "Error al consultar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@GetMapping("/clientes/numero/control/{numcontrol}")
	public ResponseEntity<?> mostrarClienteNumControl(@PathVariable String numcontrol) {
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		try {
			
			cliente = component.validarEstatus(numcontrol);
			if(cliente == null || Objects.isNull(cliente)) {
				cliente = new Cliente();
				cliente.setExiste(false);
				cliente.setId((long) 0);
			}
			else {
				cliente.setExiste(true);
			}
		} catch (Exception e) {
			response.put("mensaje", "Error al consultar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	@GetMapping("/clientes/enviar/{id}")
	public ResponseEntity<?> operaciones(@PathVariable Long id){
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();
		try {
			System.out.println("ENTRA clientes enviar");
			cliente = component.realizarOperaciones(id);

		} catch (Exception e) {
			response.put("mensaje", "Error al consultar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	@PostMapping("/clientes")
	public ResponseEntity<?> crear(@RequestBody Cliente cliente) {
		Cliente clienteNuevo = null;
		Map<String, Object> response = new HashMap<>();
		try {
			String numControl = obtenerNumeroControl();		

			Date fechaFin = new Date();

			if (cliente.getFechaInicio() != null) {
				System.out.println("ENTRA A como ADMIN SUPER: " + cliente);
				cliente.setFechaInicio(sumarHoras(cliente.getFechaInicio()));
				cliente.setFechaFin(sumarHoras(cliente.getFechaFin()));
				Periodo periodo = new Periodo();
				periodo.setId(8L);
				cliente.setPeriodo(periodo);
			} else {
				System.out.println("ENTRA COMO USER: " + cliente);
				cliente.setFechaInicio(fechaFin);
				switch (cliente.getPeriodo().getPeriodo()) {
				case 7:
					fechaFin = sumarDiasAFecha(cliente.getPeriodo().getPeriodo(), cliente);
					break;
				case 15:
					fechaFin = sumarDiasAFecha(cliente.getPeriodo().getPeriodo(), cliente);
					break;
				case 30:
					fechaFin = sumarMesAFecha(1, cliente);
					break;
				case 60:
					fechaFin = sumarMesAFecha(2, cliente);
					break;
				case 90:
					fechaFin = sumarMesAFecha(3, cliente);
					break;
				case 180:
					fechaFin = sumarMesAFecha(6, cliente);
					break;
				case 365:
					fechaFin = sumarMesAFecha(12, cliente);
					break;
				default:
				}
				System.out.println("FechaFinSumar: " + fechaFin);
				cliente.setFechaFin(fechaFin);
			}
			System.out.println("Datos cliente al: " + cliente);
			cliente.setNumControl(numControl);
			cliente.setEstatus(true);

			clienteNuevo = clienteService.save(cliente);
			generarQR(clienteNuevo, numControl);
			
			String nombreCompleto = cliente.getNombre() + " " + cliente.getApellidos();
			guardarOperacion("Registro", cliente, nombreCompleto, fechaFin);
		} catch (Exception e) {
			System.out.println("ERRR: " + e);
			if(e.toString().contains("com.sun.mail.util.MailConnectException")) {
				response.put("mensaje", "Error al enviar el correo!");
				response.put("error", "Se registro el cliente pero no se le pudo enviar correo!");
			}
			else if(e.toString().contains("org.springframework.dao.DataIntegrityViolationException")) {
				Cliente clienteCorreo = clienteService.findByClienteCorreo(cliente.getCorreo());
				response.put("mensaje", "Error al insertar la base de datos");
				response.put("error", "Ya existe un cliente con el correo que se ingreso: " + clienteCorreo.getNombre() + " " + clienteCorreo.getApellidos());
			}
			else {
				response.put("mensaje", "Error al insertar la base de datos");
				response.put("error", e.getMessage().concat(": "));
			}
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente se ha creado con exito");
		response.put("cliente", clienteNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id) {

		Cliente clienteActual = clienteService.findById(id);
		Map<String, Object> response = new HashMap<>();
		Cliente clienteActualizado = null;

		if (clienteActual == null) {
			response.put("mensaje", "Error no se pudo editar al cliente");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setApellidos(cliente.getApellidos());
			clienteActual.setCorreo(cliente.getCorreo());
			System.out.println("FechaInicio: " + cliente.getFechaInicio());
			
			if (cliente.getUsername().equals("admin")) {
				System.out.println("ENTRA A ACTUALIZA COMO SUPER ADMIN");
				clienteActual.setFechaInicio(sumarHoras(cliente.getFechaInicio()));
				clienteActual.setFechaFin(sumarHoras(cliente.getFechaFin()));
				clienteActual.setEstatus(true);
				Periodo periodo = new Periodo();
				periodo.setId(8L);
				clienteActual.setPeriodo(periodo);
				System.out.println("Cliente: " + clienteActual);
			} 
			else if (cliente.isEstatus() == false || cliente.getRoleUser().equals("ROLE_ADMIN")) {
				Date fechaActualizar = new Date();
				clienteActual.setPeriodo(cliente.getPeriodo());
				if(cliente.getRoleUser().equals("ROLE_USER") || cliente.getFechaFin().before(fechaActualizar)) {
					clienteActual.setFechaInicio(fechaActualizar);
				}
				
				switch (cliente.getPeriodo().getPeriodo()) {
				case 7:
					fechaActualizar = sumarDiasAFecha(cliente.getPeriodo().getPeriodo(), cliente);
					break;
				case 15:
					fechaActualizar = sumarDiasAFecha(cliente.getPeriodo().getPeriodo(), cliente);
					break;
				case 30:
					fechaActualizar = sumarMesAFecha(1, cliente);
					break;
				case 60:
					fechaActualizar = sumarMesAFecha(2, cliente);
					break;
				case 90:
					fechaActualizar = sumarMesAFecha(3, cliente);
					break;
				case 180:
					fechaActualizar = sumarMesAFecha(6, cliente);
					break;
				case 365:
					fechaActualizar = sumarMesAFecha(12, cliente);
					break;
				default:
				}

				clienteActual.setFechaFin(fechaActualizar);
				
			}

			clienteActual.setEstatus(true);
			clienteActual.setFechaRegistro(new Date());
			clienteActualizado = clienteService.save(clienteActual);
			
			String nombreCompleto = clienteActualizado.getNombre() + " " + clienteActualizado.getApellidos();
			guardarOperacion("Renovacion", cliente, nombreCompleto, cliente.getFechaFin());
		} catch (Exception e) {
			response.put("mensaje", "Error al actualizar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente se ha actualizado con exito");
		response.put("cliente", clienteActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			Cliente cliente = clienteService.findById(id);
			String nombreFotoAnterior = cliente.getFoto();
			uploadService.eliminar(nombreFotoAnterior);

			clienteService.delete(id);

		} catch (Exception e) {
			response.put("mensaje", "Error al eliminar de la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PostMapping("/clientes/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();

		Cliente cliente = clienteService.findById(id);

		if (!archivo.isEmpty()) {
			String nombreArchivo = null;
			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
				System.out.println("ERR: " + e);
				response.put("mensaje", "Error al subir la imagen: ");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreFotoAnterior = cliente.getFoto();

			uploadService.eliminar(nombreFotoAnterior);

			cliente.setFoto(nombreArchivo);
			clienteService.save(cliente);
			response.put("cliente", cliente);
			response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);

		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/uploads/img/{nombreFoto}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
		System.out.println("ENTRA A verFoto: " + nombreFoto);
		Resource recurso = null;

		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

		return new ResponseEntity<Resource>(recurso, HttpStatus.OK);
	}

	@GetMapping("/clientes/periodos")
	public List<Periodo> listarPeriodos() {
		return clienteService.findAllPeriodos();
	}
	
	@GetMapping("/registra/operacion/{username}")
	public ResponseEntity<?> listarPeriodos(@PathVariable String username) {
		Map<String, Object> response = new HashMap<>();
		Cliente cliente = component.validarEstatus("101");
		guardarOperacionVisita("Visita pagada", cliente, username, new Date());
		response.put("mensaje", "Se ha registrado operacion con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

//	@GetMapping("/v1/qrcode")
//	public void generateQRCode(javax.servlet.http.HttpServletResponse response, @RequestParam String text,
//			@RequestParam(defaultValue = "350") int width, @RequestParam(defaultValue = "350") int height)
//			throws Exception {
//
//		String path = qrCodeService.generateQRCode(text, width, height);
//
//		this.emailService.sendListEmail("alejandro12olea@gmail.com", path);
//	}
	
	@GetMapping("/enviar/qr/{id}")
	public ResponseEntity<?> enviarQR(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			Cliente cliente = clienteService.findById(id);
			String text = Long.toString(cliente.getId());
			int width = 350;
			int height = 350;

			String path = qrCodeService.generateQRCode(text, width, height);

			this.emailService.reenviarQREmail(cliente, path);

		} catch (Exception e) {
			response.put("mensaje", "Error al enviar el codigo QR");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "Se ha enviado el código QR con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);		
	}

	public void generarQR(Cliente cliente, String numControl) throws Exception {
		String text = Long.toString(cliente.getId());
		int width = 350;
		int height = 350;

		String path = qrCodeService.generateQRCode(text, width, height);

		this.emailService.sendListEmail(cliente, path, numControl);
		
	}

	public static Date sumarDiasAFecha(int dias, Cliente cliente) {
		System.out.println("ENTRA A SUMAR FECHA: " + cliente);
		Date fecha = new Date();
		if (dias == 0) {
			return fecha;
		}
		if(cliente.getRoleUser().equals("ROLE_ADMIN")) {
			if(cliente.getFechaFin() !=null && cliente.getFechaFin().after(fecha)) {
				fecha = cliente.getFechaFin();
			}
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);
		return calendar.getTime();
	}
	
	public static Date restarDiasAFecha(int dias) {
		Date fecha = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, -dias);
		return calendar.getTime();
	}

	public static Date sumarMesAFecha(int meses, Cliente cliente) {
		Date fecha = new Date();
		if(cliente.getRoleUser().equals("ROLE_ADMIN")) {
			System.out.println("ENTRA como ROLE_ADMIN: " + cliente);
			if(cliente.getFechaFin() !=null && cliente.getFechaFin().after(fecha)) {
				System.out.println("FECHA FIN ES ANTERIOR: " + cliente);
				fecha = sumarHoras(cliente.getFechaFin());
			}
		}
		System.out.println("FECHA A SUMAR: " + fecha);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.MONTH, meses);
		System.out.println("FECHA FINAL: " + calendar.getTime());
		return calendar.getTime();
	}
	
	public static Date sumarHoras(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.HOUR, 12);
		return calendar.getTime();
	}

	public boolean validarEstatus(Cliente cliente) {
		boolean estatus = true;
		Date fechaHoy = new Date();
		if (cliente.getFechaFin().equals(fechaHoy)) {
			System.out.println("Fecha del cliente fin es igual a hoy");
			estatus = false;
		} else if (fechaHoy.after(cliente.getFechaFin())) {
			System.out.println("Fecha del cliente fin es mayor a hoy");
			estatus = false;
		}
		return estatus;
	}

	public String obtenerNumeroControl() {
		double numCuatro = 1000 + Math.random() * 9000;
		int numControl = (int) numCuatro;
		String numCadena = String.valueOf(numControl);
		int contador = clienteService.findByNumeroControl(numCadena);
		if (contador > 0) {
			System.out.println("Ya se encuentra o es menor a 1000");
			obtenerNumeroControl();
		}
		return numCadena;
	}
	
	public void guardarOperacion(String tipoOperacion, Cliente cliente, String nombre, Date fechaFin) {
		Operacion operacion = new Operacion();
		operacion.setTipoOperacion(tipoOperacion);
		if(nombre.equals("101")) {
			operacion.setUsername(nombre);
		}
		else {
			operacion.setUsername(cliente.getUsername());
		}
		Date fechaHoy = new Date();
		operacion.setFecha(fechaHoy);
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		operacion.setHora(dateFormat.format(fechaHoy));
		operacion.setCliente(cliente.getNombre() + " " + cliente.getApellidos());
		operacion.setClienteActual(nombre);
		operacion.setFechaFin(cliente.getFechaFin());
		operacion.setNuevaFecFin(fechaFin);
		operaciones.save(operacion);
	}
	
	public void guardarOperacionVisita(String tipoOperacion, Cliente cliente, String username, Date fechaFin) {
		Operacion operacion = new Operacion();
		operacion.setTipoOperacion(tipoOperacion);
		operacion.setUsername(username);
		Date fechaHoy = new Date();
		operacion.setFecha(fechaHoy);
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		operacion.setHora(dateFormat.format(fechaHoy));
		operacion.setCliente("Visita Pagada");
		operacion.setClienteActual("");
		operacion.setFechaFin(cliente.getFechaFin());
		operacion.setNuevaFecFin(fechaFin);
		operaciones.save(operacion);
	}

}
