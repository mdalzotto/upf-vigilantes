package br.upf.App.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Supervisor
 *
 */
@Entity

public class Supervisor extends Funcionario implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Supervisor() {
		super();
	}
   
}
