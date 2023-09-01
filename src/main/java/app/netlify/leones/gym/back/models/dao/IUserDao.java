package app.netlify.leones.gym.back.models.dao;

import org.springframework.data.repository.CrudRepository;

import app.netlify.leones.gym.back.models.entity.Usuario;


public interface IUserDao extends CrudRepository<Usuario, Long>{

}