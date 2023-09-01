package app.netlify.leones.gym.back.models.services;

import app.netlify.leones.gym.back.models.entity.Usuario;

public interface IUsuarioService {
	
	public Usuario findByUsername(String username);

}
