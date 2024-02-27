package app.netlify.leones.gym.back.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.netlify.leones.gym.back.models.entity.Producto;

public interface IProductoDao extends JpaRepository<Producto, Long>{

	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String nomb);
	
	public List<Producto> findByNombreContainingIgnoreCase(String nomb);
	
	@Query("select p from Producto p where p.nombre like ?1%")
	public List<Producto> findByNombre1(String nomb);
	
	public List<Producto> findByNombreStartingWithIgnoreCase(String nomb);
	
	
}
