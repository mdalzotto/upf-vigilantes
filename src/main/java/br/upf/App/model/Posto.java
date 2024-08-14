package br.upf.App.model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

/**
 * Entity implementation class for Entity: Posto
 *
 */
@Entity

public class Posto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdPosto" )
	@SequenceGenerator(name= "IdPosto", sequenceName = "IdPosto", allocationSize = 1)
	private Long id;

	@NotBlank(message = "Informe o nome.")
	@Length(min = 2, max = 60, message = "O nome do posto deve ter entre {min} e {max} caracteres.")
	private String nome;

	
	public Posto() {
		super();
	}  
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	} 
	
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return "Posto [id=" + id + ", nome=" + nome + "]";
	}
   
}
