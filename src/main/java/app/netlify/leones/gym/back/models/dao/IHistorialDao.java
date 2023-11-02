package app.netlify.leones.gym.back.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.netlify.leones.gym.back.models.entity.Historial;

public interface IHistorialDao extends JpaRepository<Historial, Long> {

	@Query("SELECT COUNT(*) FROM Historial c where c.fechaVisita = CURDATE()")
	public int findClientesVisitas();

}
