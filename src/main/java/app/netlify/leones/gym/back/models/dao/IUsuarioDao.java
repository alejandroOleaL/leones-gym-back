package app.netlify.leones.gym.back.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.netlify.leones.gym.back.models.entity.Cliente;
import app.netlify.leones.gym.back.models.entity.Role;
import app.netlify.leones.gym.back.models.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Long>{
	
	@Query("SELECT u FROM Usuario u WHERE u.username != 'admin'")
	public Page<Usuario> findAllUsers(Pageable pageable);
	
	public Usuario findByUsername(String username);
	
	@Query("SELECT u FROM Usuario u WHERE u.id = ?1")
	public Usuario findByUserId(Long id);
	
	@Query("from Role")
	public List<Role> findAllRoles();
	
	@Query("SELECT r FROM Role r WHERE r.nombre = ?1")
	public Role findByNombre(String nombre);
	
	//Busqueda correo
	@Query("SELECT u FROM Usuario u WHERE u.email = ?1")
	public Usuario findByUsuarioCorreo(String correo);
	
}
