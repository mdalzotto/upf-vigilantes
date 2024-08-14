package br.upf.App.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import br.upf.App.jpa.JPAUtil;
import br.upf.App.model.Admin;
import br.upf.App.utils.JSFUtil;

@ManagedBean
@ViewScoped
public class AdminController implements Serializable {

	private Boolean editando;
	private List<Admin> lista;
	private Admin selecionado;

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;

	public String init() {
		if (!loginController.getPermissaoAdmin()) {
			JSFUtil.redirecionarPagina("faces/Views/Dashboard.xhtml");
		}
		return "";
	}

	public AdminController() {
		setEditando(false);
		carregarLista();
	}

	public void carregarLista() {
		EntityManager em = JPAUtil.getEntityManager();
		lista = em.createQuery("from Admin").getResultList();
		em.close();
	}

	public void incluir() {
		editando = true;
		selecionado = new Admin();
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
		} catch (Exception e) {
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
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.messagemDeErro(JSFUtil.tratarErro(e));
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

	public List<Admin> getLista() {
		return lista;
	}

	public void setLista(List<Admin> lista) {
		this.lista = lista;
	}

	public Admin getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Admin selecionado) {
		this.selecionado = selecionado;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

}
