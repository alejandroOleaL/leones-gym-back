package app.netlify.leones.gym.back.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import app.netlify.leones.gym.back.models.entity.Historial;

public interface IHistorialDao extends JpaRepository<Historial, Long> {
	

}
