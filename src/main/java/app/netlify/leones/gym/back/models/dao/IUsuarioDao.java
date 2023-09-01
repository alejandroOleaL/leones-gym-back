package app.netlify.leones.gym.back.models.dao;

import org.springframework.data.repository.CrudRepository;

import app.netlify.leones.gym.back.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	
	public Usuario findByUsername(String username);
	
}
