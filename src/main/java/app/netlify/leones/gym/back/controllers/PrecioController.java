package app.netlify.leones.gym.back.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.netlify.leones.gym.back.models.entity.Precio;
import app.netlify.leones.gym.back.models.services.IPrecioService;

@CrossOrigin(origins = { "http://localhost:4200", "https://leonesgym.web.app", "http://localhost:8090" })
@RestController
@RequestMapping("/leonesgym")
public class PrecioController {
	
	@Autowired
	private IPrecioService precioService;
	
	@GetMapping("/precios")
	public List<Precio> listar(){
		return precioService.findAll();
	}
	
	@GetMapping("/precios/{id}")
	public ResponseEntity<?> verPrecio(@PathVariable Long id){
		Precio precio = null;
		Map<String, Object> response = new HashMap<>();
		try {
			precio = precioService.findById(id);

		} catch (Exception e) {
			response.put("mensaje", "Error al consultar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Precio>(precio, HttpStatus.OK);
	}
	
	@PostMapping("/precios")
	public ResponseEntity<?> crear(@Valid @RequestBody Precio precio) {
		Precio precioNuevo = null;
		Map<String, Object> response = new HashMap<>();
		try {
			precioNuevo = precioService.save(precio);

		} catch (Exception e) {
			response.put("mensaje", "Error al insertar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El Precio se ha creado con exito");
		response.put("producto", precioNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/precios/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Precio precio, @PathVariable Long id) {

		Precio precioActual = precioService.findById(id);
		Map<String, Object> response = new HashMap<>();
		Precio precioActualizado = null;

		if (precioActual == null) {
			response.put("mensaje", "Error no se pudo editar el precio");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			precioActual.setPeriodo(precio.getPeriodo());
			precioActual.setPrecio(precio.getPrecio());

			precioActualizado = precioService.save(precioActual);

		} catch (Exception e) {
			response.put("mensaje", "Error al actualizar la base de datos");
			response.put("error", e.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El precio se ha actualizado con exito");
		response.put("usuario", precioActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/precios/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		precioService.delete(id);
	}
	
}
