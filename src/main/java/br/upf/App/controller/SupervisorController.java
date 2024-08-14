package br.upf.App.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import br.upf.App.jpa.JPAUtil;
import br.upf.App.model.Supervisor;
import br.upf.App.utils.JSFUtil;

@ManagedBean
@ViewScoped
public class SupervisorController implements Serializable{

	private Boolean editando;
	private List<Supervisor> lista;
	private Supervisor selecionado;

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;

	public String init() {
		System.out.println(loginController.getPermissaoAdmin());
		 if(!loginController.getPermissaoAdmin()) {
			 JSFUtil.redirecionarPagina("faces/Views/Dashboard.xhtml");
		 }
		 return "";
	}

	public SupervisorController() {
		setEditando(false);
		carregarLista();
	}
	
	public void carregarLista() {
		EntityManager em = JPAUtil.getEntityManager();
		lista = em.createQuery("from Supervisor").getResultList();
		em.close();
	}

	public void incluir() {
		editando = true;
		selecionado = new Supervisor();
	}
	
	public void salvar() {
		try {
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.merge(selecionado);
		em.getTransaction().commit();
		em.close();		
		selecionado = null;
		carregarLista();
		editando = false;
		JSFUtil.messagemDeSucesso("Registro salvo com sucesso!");
		}catch(Exception e) {
			e.printStackTrace();
			JSFUtil.messagemDeErro(JSFUtil.tratarErro(e));
		}
	}
	
	public void alterar() {
		editando = true;
	}
	
	public void excluir() {
		try {
			
		editando = false;
		EntityManager em = JPAUtil.getEntityManager();
		em.getTransaction().begin();
		em.remove(em.merge(selecionado));
		em.getTransaction().commit();
		em.close();		
		selecionado = null;
		carregarLista();
		JSFUtil.messagemDeSucesso("Registro excluido com sucesso!");
		}catch(Exception e) {
			e.printStackTrace();
			JSFUtil.messagemDeErro("Orreu um erro ao excluir.");
		}
	}
	
	public void cancelar() {
		editando = false;
		selecionado = null;
	}

	public Boolean getEditando() {
		return editando;
	}

	public void setEditando(Boolean editando) {
		this.editando = editando;
	}

	public List<Supervisor> getLista() {
		return lista;
	}

	public void setLista(List<Supervisor> lista) {
		this.lista = lista;
	}

	public Supervisor getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Supervisor selecionado) {
		this.selecionado = selecionado;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}
	
}
