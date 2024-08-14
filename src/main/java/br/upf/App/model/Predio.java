package br.upf.App.model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Entity implementation class for Entity: Predio
 *
 */
@Entity

public class Predio implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdPredio" )
	@SequenceGenerator(name= "IdPredio", sequenceName = "IdPredio", allocationSize = 1)
	private Long id;

	@NotBlank(message = "Informe a sigla.")
	@Length(min = 2, max = 10, message = "A sigla do predio deve ter entre {min} e {max} caracteres.")
	private String sigla;

	@NotBlank(message = "Informe a descrição.")
	@Length(max = 2000, message = "A descrição deve ter entre {min} e {max} caracteres.")
	private String descricao;

	@NotNull(message = "Posto dever ser informado.")
	@ManyToOne(optional = false)
	private Posto posto;
	
	
	public Predio() {
		super();
	}   
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	} 
	
	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}  
	
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Posto getPosto() {
		return posto;
	}
	
	public void setPosto(Posto posto) {
		this.posto = posto;
	}
	
	@Override
	public String toString() {
		return "Predio [id=" + id + ", sigla=" + sigla + ", descricao=" + descricao + ", posto=" + posto + "]";
	}
   
}
