package app.netlify.leones.gym.back.models.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.netlify.leones.gym.back.models.dao.IProductoDao;
import app.netlify.leones.gym.back.models.dao.IUsuarioDao;
import app.netlify.leones.gym.back.models.dao.IVentaDao;
import app.netlify.leones.gym.back.models.entity.Cliente;
import app.netlify.leones.gym.back.models.entity.Producto;
import app.netlify.leones.gym.back.models.entity.Role;
import app.netlify.leones.gym.back.models.entity.Usuario;
import app.netlify.leones.gym.back.models.entity.Venta;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {
	
	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private IVentaDao ventaDao;
	
	@Autowired
	private IProductoDao productoDao;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null) {
			logger.error("Error: no existe el usuario: '" +username+"' en el sistema");
			throw new UsernameNotFoundException("Error en el login: no existe el usuario '"+username+"' en el sistema!");
		}
		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsername(String username) {
		return usuarioDao.findByUsername(username);
	}

	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioDao.findAll(pageable);
	}

	@Override
	public Usuario findById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	public void delete(Long id) {
		usuarioDao.deleteById(id);
	}

	@Override
	public List<Role> findAllRoles() {
		return usuarioDao.findAllRoles();
	}

	@Override
	public Venta findVentaById(Long id) {
		return ventaDao.findById(id).orElse(null);
	}

	@Override
	public Venta saveVenta(Venta venta) {
		return ventaDao.save(venta);
	}

	@Override
	public void deleteVenta(Long id) {
		ventaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findProductoByNombre(String nomb) {
		return productoDao.findByNombre(nomb);
	}

	@Override
	public Page<Producto> findProductoAll(Pageable pageable) {
		return productoDao.findAll(pageable);
	}

	@Override
	public Producto saveProducto(Producto producto) {
		return productoDao.save(producto);
	}

	@Override
	public Producto findProductoById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	public void deleteProducto(Long id) {
		productoDao.deleteById(id);
	}

	@Override
	public Usuario findByUsuarioCorreo(String correo) {
		return usuarioDao.findByUsuarioCorreo(correo);
	}

	@Override
	public Usuario findByUserId(Long id) {
		return usuarioDao.findByUserId(id);
	}

}
