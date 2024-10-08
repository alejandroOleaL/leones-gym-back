package app.netlify.leones.gym.back.models.services;

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
	
	public int findCountMes();
	
	public int findCountSemana();

	public int findCountQuincena();
	
	public int findCountBimestre();
	
	public int findCounttrimestre();
	
	public int findCountSemestre();
	
	public int findCountAnual();
}
