package app.netlify.leones.gym.back.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.netlify.leones.gym.back.models.entity.Cliente;
import app.netlify.leones.gym.back.models.entity.Periodo;

public interface IClienteDao extends JpaRepository<Cliente, Long>{
	
	@Query("from Periodo")
	public List<Periodo> findAllPeriodos();
	
	@Query("from Cliente c where c.fechaFin <= CURDATE()")
	public Page<Cliente> findAllClientesVencidos(Pageable pageable);
	
	@Query("from Cliente c where c.numControl like %?1%")
	public Cliente findByNumControl(String numControl);

}
