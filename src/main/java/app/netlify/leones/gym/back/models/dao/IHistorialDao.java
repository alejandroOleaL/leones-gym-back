package app.netlify.leones.gym.back.models.dao;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import app.netlify.leones.gym.back.models.entity.Historial;

public interface IHistorialDao extends JpaRepository<Historial, Long> {

	@Query("SELECT COUNT(*) FROM Historial c where c.fechaVisita = CURDATE()")
	public int findClientesVisitas();
	
	@Modifying
	@Transactional
	@Query("delete from Historial h where h.fechaVisita < ?1")
	public void deleteHistorial(Date fecha);

}
