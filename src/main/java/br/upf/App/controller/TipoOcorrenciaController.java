package br.upf.App.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import br.upf.App.jpa.JPAUtil;
import br.upf.App.model.TipoOcorrencia;
import br.upf.App.utils.JSFUtil;

@ManagedBean
@ViewScoped
public class TipoOcorrenciaController implements Serializable{

	private Boolean editando;
	private List<TipoOcorrencia> lista;
	private TipoOcorrencia selecionado;

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;

	public String init() {
		if (!loginController.getPermissaoSupervisor()) {
			JSFUtil.redirecionarPagina("faces/Views/Dashboard.xhtml");
		}
		return "";
	}
	
	public TipoOcorrenciaController() {
		setEditando(false);
		carregarLista();
	}
	
	public void carregarLista() {
		EntityManager em = JPAUtil.getEntityManager();
		lista = em.createQuery("from TipoOcorrencia").getResultList();
		em.close();
	}

	public void incluir() {
		editando = true;
		selecionado = new TipoOcorrencia();
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

	public List<TipoOcorrencia> getLista() {
		return lista;
	}

	public void setLista(List<TipoOcorrencia> lista) {
		this.lista = lista;
	}

	public TipoOcorrencia getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(TipoOcorrencia selecionado) {
		this.selecionado = selecionado;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}
	
}
