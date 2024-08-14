package br.upf.App.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.Long;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import static javax.persistence.CascadeType.ALL;

/**
 * Entity implementation class for Entity: BoletoOcorrencia
 *
 */ 
@Entity

public class BoletoOcorrencia implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BoletoOcorrenciaId")
	@SequenceGenerator(name = "BoletoOcorrenciaId", allocationSize = 1)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@NotNull
	@Past(message="Só é permitido a data no passado!")
	private Date dataHora;
	
	@Column(columnDefinition = "text")
	private String descricao;
	
	@OneToMany(cascade = ALL, orphanRemoval = true, 
			   mappedBy = "boletoOcorrencia", fetch = FetchType.EAGER)
	@Size(min=1, message= "Dever ter no mínimo {min} tipo de boleto")
	private List<BoletoTipo> boletoTipos; 
	
	@ManyToOne
	@NotNull(message = "Selecione um funcionário")
	private Funcionario funcionario;
	
	@ManyToOne
	@NotNull(message = "Selecione um local")
	private Local local;

	public BoletoOcorrencia() {
		super();
	}   
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public Date getDataHora() {
		return this.dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}   
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}   
	public List<BoletoTipo> getBoletoTipos() {
		return this.boletoTipos;
	}

	public void setBoletoTipos(List<BoletoTipo> boletoTipos) {
		this.boletoTipos = boletoTipos;
	}   
	public Funcionario getFuncionario() {
		return this.funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}   
	public Local getLocal() {
		return this.local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}
	@Override
	public String toString() {
		return "BoletoOcorrencia [id=" + id + ", dataHora=" + dataHora + ", descricao=" + descricao + ", boletoTipos="
				+ boletoTipos + ", funcionario=" + funcionario + ", local=" + local + "]";
	}
}
