package app.netlify.leones.gym.back.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import app.netlify.leones.gym.back.models.entity.Cliente;
import app.netlify.leones.gym.back.models.entity.Periodo;

public interface IClienteService {
	
	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void delete(Long id);
	
	public List<Periodo> findAllPeriodos();
	
	public List<Cliente> findAllClientesVencidos();

}
