package app.netlify.leones.gym.back.models.dao;

import org.springframework.data.repository.CrudRepository;

import app.netlify.leones.gym.back.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{

}
