package br.upf.App.controller;

import br.upf.App.jpa.JPAUtil;
import br.upf.App.model.BoletoOcorrencia;
import br.upf.App.model.BoletoTipo;
import br.upf.App.model.TipoOcorrencia;
import br.upf.App.utils.JSFUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class BoletoTipoController implements Serializable{

	private Boolean editando;
	private List<BoletoTipo> lista;
	private BoletoTipo selecionado;

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;

	public String init() {
		if (!loginController.getPermissaoVigilante()) {
			JSFUtil.redirecionarPagina("faces/Views/Dashboard.xhtml");
		}
		return "";
	}

	public BoletoTipoController() {
		setEditando(false);
		carregarLista();
	}
	
	public void carregarLista() {
		EntityManager em = JPAUtil.getEntityManager();
		lista = em.createQuery("from BoletoTipo").getResultList();
		em.close();
	}

	public List<BoletoOcorrencia> boletoOcorrencias(String query) throws Exception {
		EntityManager em = JPAUtil.getEntityManager();
		List<BoletoOcorrencia> results = em.createQuery(
				"from BoletoOcorrencia where upper(descricao) like "+
						"'%"+query.trim().toUpperCase()+"%' "+
						"order by descricao").getResultList();
		em.close();
		return results;
	}

	public List<TipoOcorrencia> tipoOcorrencias(String query) throws Exception {
		EntityManager em = JPAUtil.getEntityManager();
		List<TipoOcorrencia> results = em.createQuery(
				"from TipoOcorrencia where upper(descricao) like "+
						"'%"+query.trim().toUpperCase()+"%' "+
						"order by descricao").getResultList();
		em.close();
		return results;
	}

	public void incluir() {
		editando = true;
		selecionado = new BoletoTipo();
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

	public List<BoletoTipo> getLista() {
		return lista;
	}

	public void setLista(List<BoletoTipo> lista) {
		this.lista = lista;
	}

	public BoletoTipo getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(BoletoTipo selecionado) {
		this.selecionado = selecionado;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}
	
}
