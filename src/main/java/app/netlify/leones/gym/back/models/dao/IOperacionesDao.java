package app.netlify.leones.gym.back.models.dao;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import app.netlify.leones.gym.back.models.entity.Operacion;

public interface IOperacionesDao extends JpaRepository<Operacion, Long>{
	
	@Modifying
	@Transactional
	@Query("delete from Operacion h where h.fecha < ?1")
	public void deleteOperacion(Date fecha);

}
