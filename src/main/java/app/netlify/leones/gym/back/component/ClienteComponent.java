package app.netlify.leones.gym.back.component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.netlify.leones.gym.back.models.entity.Cliente;
import app.netlify.leones.gym.back.models.entity.Historial;
import app.netlify.leones.gym.back.models.services.EmailService;
import app.netlify.leones.gym.back.models.services.IClienteService;
import app.netlify.leones.gym.back.models.services.IHistorialService;

@Component
public class ClienteComponent {
	
	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IHistorialService historialService;
	
	@Autowired
	private EmailService emailService;
	
	public Cliente validarEstatus(String numControl) {
		Cliente cliente = null;
		cliente = clienteService.findByNumControl(numControl);

		//guardar esta visita
		Historial historial = new Historial();
		historial.setNombre(cliente.getNombre());
		historial.setApellidos(cliente.getApellidos());
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
		//mandar un correo que hubo in ingreso
		this.emailService.sendIngresoEmail("alejandro12olea@gmail.com", nombreCompleto, estado, cliente.getNumControl());
		
		return cliente;
	}
	
	public Cliente validarEstatusId(Long id) {
		Cliente cliente = null;
		cliente = clienteService.findById(id);

		Historial historial = new Historial();
		historial.setNombre(cliente.getNombre());
		historial.setApellidos(cliente.getApellidos());
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
		
		return cliente;
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
	
}
