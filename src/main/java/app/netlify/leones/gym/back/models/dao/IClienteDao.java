package app.netlify.leones.gym.back.models.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.netlify.leones.gym.back.models.entity.Cliente;
import app.netlify.leones.gym.back.models.entity.Periodo;

public interface IClienteDao extends JpaRepository<Cliente, Long>{
	
	@Query("from Periodo p where p.nombre != 'Visita'")
	public List<Periodo> findAllPeriodos();
	
	@Query("from Cliente c where c.nombre != 'Visita'")
	public Page<Cliente> findAllClientesTodos(Pageable pageable);
	
	@Query("from Cliente c where c.fechaFin <= CURDATE() AND c.nombre != 'Visita'")
	public Page<Cliente> findAllClientesVencidos(Pageable pageable);
	
	@Query("from Cliente c where c.fechaFin > CURDATE() AND c.nombre != 'Visita'")
	public Page<Cliente> findAllClientesActivos(Pageable pageable);
	
	@Query("from Cliente c where c.fechaRegistro = CURDATE()")
	public Page<Cliente> findAllClientesRegistros(Pageable pageable);
	
	@Query("SELECT COUNT(*) FROM Cliente c where c.fechaFin <= CURDATE()")
	public int findCountClientesVencidos();
	
	@Query("SELECT COUNT(*) FROM Cliente c where c.fechaFin > CURDATE()")
	public int findCountClientesActivos();
	
	@Query("SELECT COUNT(*) FROM Cliente c where c.fechaRegistro = CURDATE()")
	public int findCountClientesHoyRegistros();
	
	@Query("SELECT COUNT(*) FROM Cliente")
	public int findCountClientesTotal();
	
	@Query("SELECT c FROM Cliente c WHERE c.numControl = ?1")
	public Cliente findByNumControl(String numControl);
	
	@Query("SELECT c FROM Cliente c WHERE c.telefono = ?1")
	public Cliente findByTelefono(String telefono);

	@Query("SELECT COUNT(*) FROM Cliente c WHERE c.numControl = ?1")
	public int findByNumeroControl(String numControl);
	
	//Query para precios
	//0btener mes
	@Query("SELECT COUNT(*) FROM Historial h where h.fechaVisita = CURDATE() AND h.nombre = 'Visita'")
	public int findCountVisitasHoy();
	
	@Query("SELECT COUNT(*) FROM Cliente c where c.periodo = 3 AND c.fechaRegistro > ?1")
	public int findCountMes(Date fecha);
	
	//0btener semana
	@Query("SELECT COUNT(*) FROM Cliente c where c.periodo = 1 AND c.fechaRegistro > ?1")
	public int findCountSemana(Date fecha);
	
	//0btener quincena
	@Query("SELECT COUNT(*) FROM Cliente c where c.periodo = 2 AND c.fechaRegistro > ?1")
	public int findCountQuincena(Date fecha);
	
	//0btener bimestre
	@Query("SELECT COUNT(*) FROM Cliente c where c.periodo = 4 AND c.fechaRegistro > ?1")
	public int findCountBimestre(Date fecha);
	
	//0btener trimestre
	@Query("SELECT COUNT(*) FROM Cliente c where c.periodo = 5 AND c.fechaRegistro > ?1")
	public int findCounttrimestre(Date fecha);
		
	//0btener semestre
	@Query("SELECT COUNT(*) FROM Cliente c where c.periodo = 6 AND c.fechaRegistro > ?1")
	public int findCountSemestre(Date fecha);
	
	//0btener anual
	@Query("SELECT COUNT(*) FROM Cliente c where c.periodo = 7 AND c.fechaRegistro > ?1")
	public int findCountAnual(Date fecha);
	
	//Busqueda correo
	@Query("SELECT c FROM Cliente c WHERE c.correo = ?1")
	public Cliente findByClienteCorreo(String correo);
}
