package app.netlify.leones.gym.back.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.netlify.leones.gym.back.LeonesGymBackApplication;
import app.netlify.leones.gym.back.auth.AuthorizationServerConfig;
import app.netlify.leones.gym.back.models.entity.Role;
import app.netlify.leones.gym.back.models.entity.Usuario;
import app.netlify.leones.gym.back.models.services.IUserServiceImpl;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/leonesgym")
public class UsuarioController {
	
	@Autowired
	private IUserServiceImpl usuarioService;
	private Usuario usuarioNuevo;
	private AuthorizationServerConfig server;
	
	@GetMapping("/usuarios")
	public List<Usuario> index(){
		return usuarioService.findAll();
	}
	
	@GetMapping("/usuarios/roles")
	public List<Role> listarRoles() {
		return usuarioService.findAllRoles();
	}
	
	@GetMapping("/usuarios/page/{page}")
	public Page<Usuario> index(@PathVariable Integer page) {
		return usuarioService.findAll(PageRequest.of(page, 3));
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<?> mostrarUsuario(@PathVariable Long id){
		Usuario usuario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			usuario = usuarioService.findById(id);
			System.out.println("************************************************");
			System.out.println(usuario);
		} catch (Exception e) {
			response.put("mensaje", "Error al consultar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(usuario == null) {
			response.put("mensaje", "El usuario no existe en la base");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("mensaje", "El usuario se encontro con exito");
		response.put("usuario", usuario);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/usuarios")
	public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario) {
		usuarioNuevo = null;
		Map<String, Object> response = new HashMap<>();
		try {
			System.out.println("*************************");
			System.out.println(usuario);
			
			Role role = new Role();
			if(usuario.getTipo().equals("ROLE_USER")) {
				role.setId((long) 1);
			}
			else {
				role.setId((long) 2);
			}
			role.setNombre(usuario.getTipo());
			ArrayList<Role> roles = new ArrayList<>();
			roles.add(role);
			usuario.setRoles(roles);
			//usuario.setUsername("vvargas");
			String passwordBcrypt = server.crearPass(usuario.getPassword());
			usuario.setPassword(passwordBcrypt);
			
			usuario.setEnabled(true);
			usuarioNuevo = usuarioService.save(usuario);
			System.out.println("*************************NUEVO:");
			System.out.println(usuarioNuevo);
		} catch (Exception e) {
			response.put("mensaje", "Error al insertar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario se ha creado con exito");
		response.put("usuario", usuarioNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, @PathVariable Long id) {
		
		Usuario usuarioActual = usuarioService.findById(id);
		Map<String, Object> response = new HashMap<>();
		Usuario usuarioActualizado = null;
		
		if(usuarioActual == null) {
			response.put("mensaje", "Error no se pudo editar al usuario");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			usuarioActual.setNombre(usuario.getNombre());
			usuarioActual.setApellidos(usuario.getApellidos());
			
			usuarioActualizado = usuarioService.save(usuarioActual);
			
		} 
		catch (Exception e) {
			response.put("mensaje", "Error al actualizar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario se ha actualizado con exito");
		response.put("usuario", usuarioActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			usuarioService.delete(id);
		} catch (Exception e) {
			response.put("mensaje", "Error al eliminar de la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El usuario se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}