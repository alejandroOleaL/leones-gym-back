package app.netlify.leones.gym.back.models.dao;

import org.springframework.data.repository.CrudRepository;

import app.netlify.leones.gym.back.models.entity.Venta;

public interface IVentaDao extends CrudRepository<Venta, Long>{

}
