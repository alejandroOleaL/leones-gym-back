package app.netlify.leones.gym.back.models.services;

import java.util.List;

import app.netlify.leones.gym.back.models.entity.Cliente;

public interface IClienteService {
	
	public List<Cliente> findAll();
	
	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void delete(Long id);

}
