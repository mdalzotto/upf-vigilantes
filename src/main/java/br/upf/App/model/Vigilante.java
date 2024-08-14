package br.upf.App.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Vigilante
 *
 */
@Entity

public class Vigilante extends Funcionario implements Serializable {
	
	@ManyToOne()
	@NotNull(message = "Informe um supervisor")
	private Supervisor supervisor;
	@ManyToOne() 
	@NotNull(message = "Informe o posto")
	private Posto posto;
	
	private static final long serialVersionUID = 1L;

	public Vigilante() {
		super();
	}   
	public Supervisor getSupervisor() {
		return this.supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}   
	public Posto getPosto() {
		return this.posto;
	}

	public void setPosto(Posto posto) {
		this.posto = posto;
	}
   
}