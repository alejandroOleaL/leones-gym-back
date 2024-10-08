package app.netlify.leones.gym.back.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//@Table(name = "datos")
public class Datos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int visitasHoy;
	
	private int inactivos;
	
	private int activos;
	
	private int total;
	
	private int registros;
	
	private int visitasSaldos;
	
	private int registrosMes;
	
	private int regMesSaldos;
	
	private int visitasHoyReg;

	private int registrosSemana;
	private int semanaSaldos;
	
	private int registrosQuincena;
	private int quincenaSaldos;
	
	private int registrosBimestre;
	private int bimestreSaldos;
	
	private int registrosTrimestre;
	private int trimestreSaldos;
	
	private int registrosSemestre;
	private int semestreSaldos;
	
	private int registrosAnual;
	private int anualSaldos;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVisitasHoy() {
		return visitasHoy;
	}

	public void setVisitasHoy(int visitasHoy) {
		this.visitasHoy = visitasHoy;
	}

	public int getInactivos() {
		return inactivos;
	}

	public void setInactivos(int inactivos) {
		this.inactivos = inactivos;
	}

	public int getActivos() {
		return activos;
	}

	public void setActivos(int activos) {
		this.activos = activos;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRegistros() {
		return registros;
	}

	public void setRegistros(int registros) {
		this.registros = registros;
	}

	public int getVisitasSaldos() {
		return visitasSaldos;
	}

	public void setVisitasSaldos(int visitasSaldos) {
		this.visitasSaldos = visitasSaldos;
	}

	public int getRegistrosMes() {
		return registrosMes;
	}

	public void setRegistrosMes(int registrosMes) {
		this.registrosMes = registrosMes;
	}

	public int getRegMesSaldos() {
		return regMesSaldos;
	}

	public void setRegMesSaldos(int regMesSaldos) {
		this.regMesSaldos = regMesSaldos;
	}

	public int getVisitasHoyReg() {
		return visitasHoyReg;
	}

	public void setVisitasHoyReg(int visitasHoyReg) {
		this.visitasHoyReg = visitasHoyReg;
	}

	public int getRegistrosSemana() {
		return registrosSemana;
	}

	public void setRegistrosSemana(int registrosSemana) {
		this.registrosSemana = registrosSemana;
	}

	public int getSemanaSaldos() {
		return semanaSaldos;
	}

	public void setSemanaSaldos(int semanaSaldos) {
		this.semanaSaldos = semanaSaldos;
	}

	public int getRegistrosQuincena() {
		return registrosQuincena;
	}

	public void setRegistrosQuincena(int registrosQuincena) {
		this.registrosQuincena = registrosQuincena;
	}

	public int getQuincenaSaldos() {
		return quincenaSaldos;
	}

	public void setQuincenaSaldos(int quincenaSaldos) {
		this.quincenaSaldos = quincenaSaldos;
	}

	public int getRegistrosBimestre() {
		return registrosBimestre;
	}

	public void setRegistrosBimestre(int registrosBimestre) {
		this.registrosBimestre = registrosBimestre;
	}

	public int getBimestreSaldos() {
		return bimestreSaldos;
	}

	public void setBimestreSaldos(int bimestreSaldos) {
		this.bimestreSaldos = bimestreSaldos;
	}

	public int getRegistrosTrimestre() {
		return registrosTrimestre;
	}

	public void setRegistrosTrimestre(int registrosTrimestre) {
		this.registrosTrimestre = registrosTrimestre;
	}

	public int getTrimestreSaldos() {
		return trimestreSaldos;
	}

	public void setTrimestreSaldos(int trimestreSaldos) {
		this.trimestreSaldos = trimestreSaldos;
	}

	public int getRegistrosSemestre() {
		return registrosSemestre;
	}

	public void setRegistrosSemestre(int registrosSemestre) {
		this.registrosSemestre = registrosSemestre;
	}

	public int getSemestreSaldos() {
		return semestreSaldos;
	}

	public void setSemestreSaldos(int semestreSaldos) {
		this.semestreSaldos = semestreSaldos;
	}

	public int getRegistrosAnual() {
		return registrosAnual;
	}

	public void setRegistrosAnual(int registrosAnual) {
		this.registrosAnual = registrosAnual;
	}

	public int getAnualSaldos() {
		return anualSaldos;
	}

	public void setAnualSaldos(int anualSaldos) {
		this.anualSaldos = anualSaldos;
	}

	@Override
	public String toString() {
		return "Datos [id=" + id + ", visitasHoy=" + visitasHoy + ", inactivos=" + inactivos + ", activos=" + activos
				+ ", total=" + total + ", registros=" + registros + ", visitasSaldos=" + visitasSaldos
				+ ", registrosMes=" + registrosMes + ", regMesSaldos=" + regMesSaldos + ", visitasHoyReg="
				+ visitasHoyReg + ", registrosSemana=" + registrosSemana + ", semanaSaldos=" + semanaSaldos
				+ ", registrosQuincena=" + registrosQuincena + ", quincenaSaldos=" + quincenaSaldos
				+ ", registrosBimestre=" + registrosBimestre + ", bimestreSaldos=" + bimestreSaldos
				+ ", registrosTrimestre=" + registrosTrimestre + ", trimestreSaldos=" + trimestreSaldos
				+ ", registrosSemestre=" + registrosSemestre + ", semestreSaldos=" + semestreSaldos
				+ ", registrosAnual=" + registrosAnual + ", anualSaldos=" + anualSaldos + "]";
	}

}
