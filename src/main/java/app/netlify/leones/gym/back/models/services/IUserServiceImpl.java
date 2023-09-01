//package app.netlify.leones.gym.back.models.services;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import app.netlify.leones.gym.back.models.dao.IUserDao;
//import app.netlify.leones.gym.back.models.entity.Usuario;
//
//
//@Service
//public class IUserServiceImpl implements IUserService {
//	
//	@Autowired
//	private IUserDao usuarioDao;
//
//	@Override
//	@Transactional(readOnly = true)
//	public List<Usuario> findAll() {
//		
//		return (List<Usuario>) usuarioDao.findAll();
//	}
//
//	@Override
//	@Transactional(readOnly = true)
//	public Usuario findById(Long id) {
//		return usuarioDao.findById(id).orElse(null);
//	}
//
//	@Override
//	public Usuario save(Usuario usuario) {
//		return usuarioDao.save(usuario);
//	}
//
//	@Override
//	public void delete(Long id) {
//		usuarioDao.deleteById(id);
//	}
//
//}
