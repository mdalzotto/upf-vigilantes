package br.upf.App.model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.InheritanceType.JOINED;

/**
 * Entity implementation class for Entity: Funcionario
 *
 */
@Entity
@Inheritance(strategy = JOINED)
public abstract class Funcionario implements Serializable {

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "FuncionarioId")
	@SequenceGenerator(name = "FuncionarioId", allocationSize = 1)
	private Long id;
	@NotBlank(message = "Informe o nome")
	@Length(min = 2, max = 60, message = "O nome deve ter de 2 a 60 caracteres")
	private String nome;
	@Length(min = 9, max = 15, message = "Confirme o celular")
	private String celular;
	@Email(message = "Preencha um e-mail válido")
	@NotBlank(message = "Informe o e-mail")
	@Length(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
	private String email;
	@Length(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
	@NotBlank(message = "Preencha a senha")
	private String senha;
	@Temporal(TemporalType.DATE)
	private Date dataInativo;
	@CPF(message = "Preencha um cpf válido")
	@NotBlank(message = "Preencha o CPF")
	@Column(unique = true, length = 14)
	private String cpf;
	@NotBlank(message = "Preencha o RG")
	@Column(unique = true, length = 15)
	private String rg;
	
	private static final long serialVersionUID = 1L;

	public Funcionario() {
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

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataInativo() {
		return this.dataInativo;
	}

	public void setDataInativo(Date dataInativo) {
		this.dataInativo = dataInativo;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return this.rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	@Override
	public String toString() {
		return nome;
	}

}
