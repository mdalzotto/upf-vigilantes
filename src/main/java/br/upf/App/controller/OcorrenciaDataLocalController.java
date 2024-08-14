package br.upf.App.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import br.upf.App.jpa.JPAUtil;
import br.upf.App.model.OcorrenciaDataLocal;
import br.upf.App.utils.JSFUtil;

@ManagedBean
@ViewScoped
public class OcorrenciaDataLocalController implements Serializable {

	private Boolean editando;
	private List<OcorrenciaDataLocal> lista;
	private OcorrenciaDataLocal selecionado;

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;

	public String init() {
		if (!loginController.getPermissaoVigilante()) {
			JSFUtil.redirecionarPagina("faces/Views/Dashboard.xhtml");
		}
		return "";
	}

	public OcorrenciaDataLocalController() {
		carregarLista();
	}
	
	public void carregarLista() {
		EntityManager em = JPAUtil.getEntityManager();
		lista = em.createQuery("from OcorrenciaDataLocal").getResultList();
		em.close();
	}

	public Boolean getEditando() {
		return editando;
	}

	public void setEditando(Boolean editando) {
		this.editando = editando;
	}

	public List<OcorrenciaDataLocal> getLista() {
		return lista;
	}

	public void setLista(List<OcorrenciaDataLocal> lista) {
		this.lista = lista;
	}

	public OcorrenciaDataLocal getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(OcorrenciaDataLocal selecionado) {
		this.selecionado = selecionado;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}
	
}
