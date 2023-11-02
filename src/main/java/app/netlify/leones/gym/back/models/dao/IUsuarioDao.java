package app.netlify.leones.gym.back.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.netlify.leones.gym.back.models.entity.Role;
import app.netlify.leones.gym.back.models.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Long>{
	
	public Usuario findByUsername(String username);
	
	@Query("from Role")
	public List<Role> findAllRoles();
	
	@Query("SELECT r FROM Role r WHERE r.nombre = ?1")
	public Role findByNombre(String nombre);
	
}
