package app.netlify.leones.gym.back.models.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import app.netlify.leones.gym.back.models.entity.Historial;

public interface IHistorialService {
	
	public Page<Historial> findAll(Pageable pageable);
	
	public Historial save(Historial historial);
	
	public int findClientesVisitas();

}
