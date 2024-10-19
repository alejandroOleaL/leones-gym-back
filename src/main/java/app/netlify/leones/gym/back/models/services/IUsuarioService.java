package app.netlify.leones.gym.back.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import app.netlify.leones.gym.back.models.entity.Cliente;
import app.netlify.leones.gym.back.models.entity.Producto;
import app.netlify.leones.gym.back.models.entity.Role;
import app.netlify.leones.gym.back.models.entity.Usuario;
import app.netlify.leones.gym.back.models.entity.Venta;

public interface IUsuarioService {

	public Usuario findByUsername(String username);

	public List<Usuario> findAll();

	public Page<Usuario> findAll(Pageable pageable);

	public Usuario findById(Long id);

	public Usuario save(Usuario cliente);

	public void delete(Long id);
	
	public List<Role> findAllRoles();
	
	public Venta findVentaById(Long id);
	
	public Venta saveVenta(Venta venta);
	
	public void deleteVenta(Long id);
	
	public List<Producto> findProductoByNombre(String nomb);
	
	public Page<Producto> findProductoAll(Pageable pageable);
	
	public Producto saveProducto(Producto producto);
	
	public Producto findProductoById(Long id);
	
	public void deleteProducto(Long id);
	
	public Usuario findByUsuarioCorreo(String correo);
	
	public Usuario findByUserId(Long id);

}
