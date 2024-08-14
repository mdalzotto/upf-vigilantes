package br.upf.App.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.Query;


import br.upf.App.jpa.JPAUtil;
import br.upf.App.model.Admin;
import br.upf.App.model.Funcionario;
import br.upf.App.model.Supervisor;
import br.upf.App.model.Vigilante;
import br.upf.App.utils.JSFUtil;

@ManagedBean
@SessionScoped
public class LoginController implements Serializable {
	private String login;
	private String senha;
	/**
	 * Atributo para controle do usuário logado. É inicializado quando informados
	 * login e senha válidos. Setado para null quando o usuário sair do sistema.
	 */
	private Funcionario usuarioLogado = null;
	
	private Boolean permissaoAdmin;
	private Boolean permissaoSupervisor;
	private Boolean permissaoVigilante;

	public String entrar() {
		EntityManager em = JPAUtil.getEntityManager();
		Query qry = em.createQuery("from Funcionario where email = :login and senha = :senha");
		qry.setParameter("login", login);
		qry.setParameter("senha", senha);
		List<Funcionario> list = qry.getResultList();
		em.close();
		if (list.size() <= 0) {
			usuarioLogado = null;
			JSFUtil.messagemDeErro("Login ou senha inválidos!");
			return "";
		} else {
			usuarioLogado = list.get(0);

			if(usuarioLogado.getDataInativo() != null){
				usuarioLogado = null;
				JSFUtil.messagemDeAtencao("Seu usuário esta inativo entre em contato com o administrador!");
				return "";
			}

			permissaoAdmin = false;
			permissaoSupervisor = false;
			permissaoVigilante = false;

			if (usuarioLogado instanceof Admin) {
				permissaoAdmin = true;
				permissaoSupervisor = true;
				permissaoVigilante = true;

			}else if (usuarioLogado instanceof Supervisor) {
				permissaoAdmin = false;
				permissaoSupervisor = true;
				permissaoVigilante = true;

			}else if (usuarioLogado instanceof Vigilante) {
				permissaoAdmin = false;
				permissaoSupervisor = false;
				permissaoVigilante = true;
			}
			
			return "/faces/Views/Home.xhtml?faces-redirect=true";
		}
	}

	public String sair() {
		usuarioLogado = null;
		JSFUtil.messagemDeSucesso("Usuário Desconectado!");
		return "/faces/LoginForm.xhtml";
	}

	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Funcionario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Funcionario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Boolean getPermissaoAdmin() {
		return permissaoAdmin;
	}

	public void setPermissaoAdmin(Boolean permissaoAdmin) {
		this.permissaoAdmin = permissaoAdmin;
	}

	public Boolean getPermissaoSupervisor() {
		return permissaoSupervisor;
	}

	public void setPermissaoSupervisor(Boolean permissaoSupervisor) {
		this.permissaoSupervisor = permissaoSupervisor;
	}

	public Boolean getPermissaoVigilante() {
		return permissaoVigilante;
	}

	public void setPermissaoVigilante(Boolean permissaoVigilante) {
		this.permissaoVigilante = permissaoVigilante;
	}

}
