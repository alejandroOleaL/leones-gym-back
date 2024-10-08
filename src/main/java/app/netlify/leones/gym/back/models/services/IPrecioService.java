package app.netlify.leones.gym.back.models.services;

import java.util.List;

import app.netlify.leones.gym.back.models.entity.Precio;

public interface IPrecioService {

	public List<Precio> findAll();
	
	public Precio findById(Long id);
	
	public Precio save(Precio precio);
	
	public void delete(Long id);
	
}
