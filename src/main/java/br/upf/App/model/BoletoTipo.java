package br.upf.App.model;

import java.io.File;
import java.io.Serializable;
import java.lang.Long;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: BoletoTipo
 *
 */
@Entity

public class BoletoTipo implements Serializable {
   
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BoletoTipoId")
	@SequenceGenerator(name = "BoletoTipoId", sequenceName = "BoletoTipoId", allocationSize = 1)
	private Long Id;
	private String anexo;
	
	@Column(length = 200)
	private String arquivoTipo;
	@Column(length = 200)
	private String arquivoNome;
	@Lob
	private byte[] arquivoBytes;
	
	
	@ManyToOne(optional = false)
	@NotNull(message = "Boleto da ocorrência deve ser informado")
	private BoletoOcorrencia boletoOcorrencia;
	@ManyToOne
	@NotNull(message = "Um tipo de ocorrência deve ser informado")
	private TipoOcorrencia tipoOcorrencia; 
	

	public BoletoTipo() {
		super();
	}   
	public Long getId() {
		return this.Id;
	}

	public void setId(Long Id) {
		this.Id = Id;
	}   
	public String getAnexo() {
		return this.anexo;
	}
	
	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}
	
	public BoletoOcorrencia getBoletoOcorrencia() {
		return boletoOcorrencia;
	}
	
	public void setBoletoOcorrencia(BoletoOcorrencia boletoOcorrencia) {
		this.boletoOcorrencia = boletoOcorrencia;
	}
	@Override
	public String toString() {
		return "BoletoTipo [Id=" + Id + ", anexo=" + anexo + ", boletoOcorrencia=" + boletoOcorrencia
				+ ", tipoOcorrencia=" + tipoOcorrencia + "]";
	}
	public TipoOcorrencia getTipoOcorrencia() {
		return tipoOcorrencia;
	}
	public void setTipoOcorrencia(TipoOcorrencia tipoOcorrencia) {
		this.tipoOcorrencia = tipoOcorrencia;
	}
	public String getArquivoTipo() {
		return arquivoTipo;
	}
	public void setArquivoTipo(String arquivoTipo) {
		this.arquivoTipo = arquivoTipo;
	}
	public String getArquivoNome() {
		return arquivoNome;
	}
	public void setArquivoNome(String arquivoNome) {
		this.arquivoNome = arquivoNome;
	}
	public byte[] getArquivoBytes() {
		return arquivoBytes;
	}
	public void setArquivoBytes(byte[] arquivoBytes) {
		this.arquivoBytes = arquivoBytes;
	}
}
