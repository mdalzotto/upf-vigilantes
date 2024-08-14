package br.upf.App.model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

/**
 * Entity implementation class for Entity: TipoOcorrencia
 *
 */
@Entity

public class TipoOcorrencia implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TipoOcorrenciaId")
	@SequenceGenerator(name = "TipoOcorrenciaId", sequenceName = "TipoOcorrenciaId", allocationSize = 1)
	private Long Id;
	
	@NotBlank
	@Length(min=2, max=2000, message = "A descrição do tipo de ocorrência deve ter entre {min} e {max} caracteres.")
	private String descricao;

	public TipoOcorrencia() {
		super();
	}   
	public Long getId() {
		return this.Id;
	}

	public void setId(Long Id) {
		this.Id = Id;
	}   
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return "TipoOcorrencia [Id=" + Id + ", descricao=" + descricao + "]";
	}   
}
