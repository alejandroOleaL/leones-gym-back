package app.netlify.leones.gym.back.models.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import app.netlify.leones.gym.back.models.entity.Cliente;
import app.netlify.leones.gym.back.models.entity.Periodo;

public interface IClienteService {
	
	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public Page<Cliente> findAllClientesTodos(Pageable pageable);
	
	public Page<Cliente> findAllClientesRegistros(Pageable pageable);
	
	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void delete(Long id);
	
	public List<Periodo> findAllPeriodos();
	
	public Page<Cliente> findAllClientesVencidos(Pageable pageable);
	
	public Page<Cliente> findAllClientesActivos(Pageable pageable);
	
	public Cliente findByNumControl(String id);
	
	public Cliente findByTelefono(String telefono);
	
	public int findCountClientesVencidos();
	
	public int findCountClientesActivos();
	
	public int findCountClientesTotal();
	
	public int findCountClientesHoyRegistros();
	
	public int findByNumeroControl(String numControl);
	
	public int findCountVisitasHoy();
	
	public int findCountMes(Date fecha);
	
	public int findCountSemana(Date fecha);

	public int findCountQuincena(Date fecha);
	
	public int findCountBimestre(Date fecha);
	
	public int findCounttrimestre(Date fecha);
	
	public int findCountSemestre(Date fecha);
	
	public int findCountAnual(Date fecha);
	
	public Cliente findByClienteCorreo(String correo);
}
