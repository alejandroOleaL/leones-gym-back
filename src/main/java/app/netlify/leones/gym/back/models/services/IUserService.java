package app.netlify.leones.gym.back.models.services;

import java.util.List;

import app.netlify.leones.gym.back.models.entity.User;

public interface IUserService {
	
	public List<User> findAll();
	
	public User findById(Long id);
	
	public User save(User Usuario);
	
	public void delete(Long id);

}