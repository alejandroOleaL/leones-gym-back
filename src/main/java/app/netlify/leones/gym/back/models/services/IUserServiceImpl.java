package app.netlify.leones.gym.back.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.netlify.leones.gym.back.models.dao.IUserDao;
import app.netlify.leones.gym.back.models.entity.User;


@Service
public class IUserServiceImpl implements IUserService {
	
	@Autowired
	private IUserDao usuarioDao;

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		
		return (List<User>) usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public User findById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	public User save(User usuario) {
		return usuarioDao.save(usuario);
	}

	@Override
	public void delete(Long id) {
		usuarioDao.deleteById(id);
	}

}
