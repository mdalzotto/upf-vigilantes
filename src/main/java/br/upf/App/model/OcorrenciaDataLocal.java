package br.upf.App.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.Long;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

/**
 * Entity implementation class for Entity: OcorrenciaDataLocal
 *
 */
@Entity

public class OcorrenciaDataLocal implements Serializable {

	   
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OcorrenciaDataLocalId")
	@SequenceGenerator(name = "OcorrenciaDataLocalId", sequenceName = "OcorrenciaDataLocalId", allocationSize = 1)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Informe uma data")
	@Past(message="Só é permitido a data no passado!")
	private Date data;
	
	@NotNull
	private Integer ocorrenciaNaData;
	
	@ManyToOne(optional= false)
	@NotNull(message = "Selecione um tipo de ocorrência")
	private TipoOcorrencia tipoOcorrencia; 

	public OcorrenciaDataLocal() {
		super();
	}   
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}   
	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}   
	public Integer getOcorrenciaNaData() {
		return this.ocorrenciaNaData;
	}

	public void setOcorrenciaNaData(Integer ocorrenciaNaData) {
		this.ocorrenciaNaData = ocorrenciaNaData;
	}
	
	@Override
	public String toString() {
		return "OcorrenciaDataLocal [id=" + id + ", data=" + data + ", ocorrenciaNaData=" + ocorrenciaNaData
				+ ", tipoOcorrencia=" + tipoOcorrencia + "]";
	}
	public TipoOcorrencia getTipoOcorrencia() {
		return tipoOcorrencia;
	}
	public void setTipoOcorrencia(TipoOcorrencia tipoOcorrencia) {
		this.tipoOcorrencia = tipoOcorrencia;
	}   
}
