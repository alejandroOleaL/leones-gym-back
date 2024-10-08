package app.netlify.leones.gym.back.component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.netlify.leones.gym.back.models.entity.Cliente;
import app.netlify.leones.gym.back.models.entity.Datos;
import app.netlify.leones.gym.back.models.entity.Historial;
import app.netlify.leones.gym.back.models.entity.Precio;
import app.netlify.leones.gym.back.models.services.EmailService;
import app.netlify.leones.gym.back.models.services.IClienteService;
import app.netlify.leones.gym.back.models.services.IHistorialService;
import app.netlify.leones.gym.back.models.services.IPrecioService;

@Component
public class ClienteComponent {
	
	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IHistorialService historialService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private IPrecioService precioService;
	
	public Cliente validarEstatus(String numControl) {
		Cliente cliente = null;
		cliente = clienteService.findByNumControl(numControl);

		System.out.println("CLIENTE: " + cliente);
		
		return cliente;
	}
	
	public Cliente realizarOperaciones(Long id) {
		
		Cliente cliente = null;
		cliente = clienteService.findById(id);
		System.out.println("ENTRA realizarOperaciones: " + cliente);
		
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
	
	public Datos obtenerSaldos(Datos datos) {
		Precio precioVisita = precioService.findById(1L);
		int saldoVisita = (int) (datos.getVisitasHoyReg() * precioVisita.getPrecio());
		datos.setVisitasSaldos(saldoVisita);
		
		Precio precioMes = precioService.findById(3L);
		int saldoMes = (int) (datos.getRegistrosMes() * precioMes.getPrecio());
		datos.setRegMesSaldos(saldoMes);
		
		Precio precioSemana = precioService.findById(2L);
		int saldoSemana = (int) (datos.getRegistrosSemana() * precioSemana.getPrecio());
		datos.setSemanaSaldos(saldoSemana);
		
		Precio precioQuincena = precioService.findById(4L);
		int saldoQuincena = (int) (datos.getRegistrosQuincena() * precioQuincena.getPrecio());
		datos.setQuincenaSaldos(saldoQuincena);
		
		Precio precioBimestre = precioService.findById(5L);
		int saldoBimestre = (int) ((int) (datos.getRegistrosBimestre()) * precioBimestre.getPrecio());
		datos.setBimestreSaldos(saldoBimestre);
		
		Precio precioTrimestre = precioService.findById(6L);
		int saldoQTri = (int) (datos.getRegistrosTrimestre() * precioTrimestre.getPrecio());
		datos.setTrimestreSaldos(saldoQTri);
		
		Precio precioSemestre = precioService.findById(7L);
		int saldoSe = (int) (datos.getRegistrosSemestre() * precioSemestre.getPrecio());
		datos.setSemestreSaldos(saldoSe);
		
		Precio precioAnual = precioService.findById(8L);
		int saldoAnual = (int) (datos.getRegistrosAnual() * precioAnual.getPrecio());
		datos.setAnualSaldos(saldoAnual);
		
		
		return datos;
	}
	
}
