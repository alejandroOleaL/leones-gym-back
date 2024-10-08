package app.netlify.leones.gym.back.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.netlify.leones.gym.back.models.dao.IPrecioDao;
import app.netlify.leones.gym.back.models.entity.Precio;

@Service
public class PrecioServiceImpl implements IPrecioService {
	
	@Autowired
	private IPrecioDao precioDao;

	@Override
	@Transactional(readOnly = true)
	public List<Precio> findAll() {
		return precioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Precio findById(Long id) {
		return precioDao.findById(id).orElse(null);
	}

	@Override
	public Precio save(Precio precio) {
		return precioDao.save(precio);
	}

	@Override
	public void delete(Long id) {
		precioDao.deleteById(id);
	}

}
