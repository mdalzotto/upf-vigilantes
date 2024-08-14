package br.upf.App.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Admin
 *
 */
@Entity

public class Admin extends Funcionario implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Admin() {
		super();
	}
   
}
