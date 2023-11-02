package app.netlify.leones.gym.back.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import app.netlify.leones.gym.back.models.entity.Role;
import app.netlify.leones.gym.back.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);

	public List<Usuario> findAll();

	public Page<Usuario> findAll(Pageable pageable);

	public Usuario findById(Long id);

	public Usuario save(Usuario cliente);

	public void delete(Long id);
	
	public List<Role> findAllRoles();

}
