package app.netlify.leones.gym.back.models.dao;

import org.springframework.data.repository.CrudRepository;

import app.netlify.leones.gym.back.models.entity.User;


public interface IUserDao extends CrudRepository<User, Long>{

}