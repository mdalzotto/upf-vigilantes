package br.upf.App.model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Entity implementation class for Entity: Local
 *
 */
@Entity

public class Local implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IdLocal" )
	@SequenceGenerator(name= "IdLocal", sequenceName = "IdLocal", allocationSize = 1)
	private Long id;
	
	@NotBlank(message = "Informe o nome.")
	@Length(min = 2, max = 80, message = "O nome do local deve ter entre {min} e {max} caracteres.")
	private String nome;

	@NotNull(message = "O local deve ser informado.")
	@ManyToOne(optional = false)
	private Predio predio;

	
	public Local() {
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
	
	public Predio getPredio() {
		return predio;
	}
	
	public void setPredio(Predio predio) {
		this.predio = predio;
	}
	
	@Override
	public String toString() {
		return "Local [id=" + id + ", nome=" + nome + ", predio=" + predio + "]";
	}
   
}
