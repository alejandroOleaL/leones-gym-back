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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.netlify.leones.gym.back.models.entity.Cliente;
import app.netlify.leones.gym.back.models.entity.Datos;
import app.netlify.leones.gym.back.models.entity.Historial;
import app.netlify.leones.gym.back.models.entity.Periodo;
import app.netlify.leones.gym.back.models.services.EmailService;
import app.netlify.leones.gym.back.models.services.IClienteService;
import app.netlify.leones.gym.back.models.services.IHistorialService;
import app.netlify.leones.gym.back.models.services.IUploadFileService;
import app.netlify.leones.gym.back.models.services.QRCodeService;

@CrossOrigin(origins = {"http://localhost:4200", "*", "https://leonesgym.web.app"},
methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
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
		datos.setId((long) 1);
		datos.setVisitasHoy(historialService.findClientesVisitas());
		datos.setInactivos(clienteService.findCountClientesVencidos());
		datos.setActivos(clienteService.findCountClientesActivos());
		datos.setTotal(clienteService.findCountClientesTotal());
		return datos;
	}

	@GetMapping("/clientes/vencidos/{page}")
	public Page<Cliente> clientesVencidos(@PathVariable Integer page) {
		return clienteService.findAllClientesVencidos(PageRequest.of(page, 3));
	}

	@GetMapping("/historial/page/{page}")
	public Page<Historial> historial(@PathVariable Integer page) {
		return historialService.findAll(PageRequest.of(page, 8));
	}

	@GetMapping("/clientes/page/{page}")
	public Page<Cliente> index(@PathVariable Integer page) {
		return clienteService.findAll(PageRequest.of(page, 3));
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
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
			
			System.out.println("ID DEL CLIENTE****: " + id);
			cliente = clienteService.findById(id);
			
			System.out.println(cliente);

			Historial historial = new Historial();
			historial.setCliente(cliente);
			Date fechaHoy = new Date();
			historial.setFechaVisita(fechaHoy);
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			historial.setHoraVisita(dateFormat.format(fechaHoy));

			historialService.save(historial);

			boolean estatus = validarEstatus(cliente);
			cliente.setEstatus(estatus);
			String nombreCompleto = cliente.getNombre() + " " + cliente.getApellidos();
			String estado = null;
			if(estatus == true) {
				estado = "Activo";
			}else {
				estado = "Inactivo";
			}
			this.emailService.sendIngresoEmail("alejandro12olea@gmail.com", nombreCompleto, estado, cliente.getNumControl());
			
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
			cliente = clienteService.findByNumControl(numcontrol);

			Historial historial = new Historial();
			historial.setCliente(cliente);
			Date fechaHoy = new Date();
			historial.setFechaVisita(fechaHoy);
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			historial.setHoraVisita(dateFormat.format(fechaHoy));

			historialService.save(historial);

			boolean estatus = validarEstatus(cliente);
			cliente.setEstatus(estatus);
			String nombreCompleto = cliente.getNombre() + " " + cliente.getApellidos();
			String estado = null;
			if(estatus == true) {
				estado = "Activo";
			}else {
				estado = "Inactivo";
			}
			this.emailService.sendIngresoEmail("alejandro12olea@gmail.com", nombreCompleto, estado, cliente.getNumControl());
			
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
			fechaFin = sumarDiasAFecha(cliente.getPeriodo().getPeriodo());
			cliente.setFechaFin(fechaFin);
			cliente.setNumControl(numControl);
			cliente.setEstatus(true);

			clienteNuevo = clienteService.save(cliente);
			generarQR(clienteNuevo, numControl);
		} catch (Exception e) {
			response.put("mensaje", "Error al insertar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente se ha creado con exito");
		response.put("cliente", clienteNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
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
			clienteActual.setPeriodo(cliente.getPeriodo());

			if (cliente.isEstatus() == false) {
				Date fechaActualizar = new Date();
				clienteActual.setFechaInicio(fechaActualizar);
				fechaActualizar = sumarDiasAFecha(cliente.getPeriodo().getPeriodo());

				clienteActual.setFechaFin(fechaActualizar);
				clienteActual.setEstatus(true);
			}

			clienteActualizado = clienteService.save(clienteActual);

		} catch (Exception e) {
			response.put("mensaje", "Error al actualizar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente se ha actualizado con exito");
		response.put("cliente", clienteActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured("ROLE_ADMIN")
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

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("/clientes/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();

		Cliente cliente = clienteService.findById(id);

		if (!archivo.isEmpty()) {
			String nombreArchivo = null;
			try {
				nombreArchivo = uploadService.copiar(archivo);
			} catch (IOException e) {
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

	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {

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

	@GetMapping("/v1/qrcode")
	public void generateQRCode(javax.servlet.http.HttpServletResponse response, @RequestParam String text,
			@RequestParam(defaultValue = "350") int width, @RequestParam(defaultValue = "350") int height)
			throws Exception {

		String path = qrCodeService.generateQRCode(text, width, height);

		this.emailService.sendListEmail("alejandro12olea@gmail.com", path);
	}

	public void generarQR(Cliente cliente, String numControl) throws Exception {
		String text = Long.toString(cliente.getId());
		int width = 350;
		int height = 350;

		String path = qrCodeService.generateQRCode(text, width, height);

		this.emailService.sendListEmail(cliente.getCorreo(), path, numControl);
	}

	public static Date sumarDiasAFecha(int dias) {
		Date fecha = new Date();
		if (dias == 0) {
			return fecha;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);
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
		int numControl = (int) (Math.random() * 9999 + 1);
		String numCadena = String.valueOf(numControl);
		return numCadena;
	}

}
