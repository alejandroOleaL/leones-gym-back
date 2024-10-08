package app.netlify.leones.gym.back.models.dao;

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
	
	@Query("SELECT COUNT(*) FROM Cliente c where c.periodo = 3")
	public int findCountMes();
	
	//0btener semana
	@Query("SELECT COUNT(*) FROM Cliente c where c.periodo = 1")
	public int findCountSemana();
	
	//0btener quincena
	@Query("SELECT COUNT(*) FROM Cliente c where c.periodo = 2")
	public int findCountQuincena();
	
	//0btener bimestre
	@Query("SELECT COUNT(*) FROM Cliente c where c.periodo = 4")
	public int findCountBimestre();
	
	//0btener trimestre
	@Query("SELECT COUNT(*) FROM Cliente c where c.periodo = 5")
	public int findCounttrimestre();
		
	//0btener semestre
	@Query("SELECT COUNT(*) FROM Cliente c where c.periodo = 6")
	public int findCountSemestre();
	
	//0btener anual
	@Query("SELECT COUNT(*) FROM Cliente c where c.periodo = 7")
	public int findCountAnual();
}
