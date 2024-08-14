package br.upf.App.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import br.upf.App.jpa.JPAUtil;
import br.upf.App.model.Posto;
import br.upf.App.model.Supervisor;
import br.upf.App.model.Vigilante;
import br.upf.App.utils.JSFUtil;

@ManagedBean
@ViewScoped
public class VigilanteController implements Serializable{

	private Boolean editando;
	private List<Vigilante> lista;
	private Vigilante selecionado;

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;

	public String init() {
		if (!loginController.getPermissaoSupervisor()) {
			JSFUtil.redirecionarPagina("faces/Views/Dashboard.xhtml");
		}
		return "";
	}

	public VigilanteController() {
		setEditando(false);
		carregarLista();
	}
	
	public void carregarLista() {
		EntityManager em = JPAUtil.getEntityManager();
		lista = em.createQuery("from Vigilante").getResultList();
		em.close();
	}

	public List<Posto> postos(String query) throws Exception {
		EntityManager em = JPAUtil.getEntityManager();
		List<Posto> results = em.createQuery( 
                "from Posto where upper(nome) like "+ 
                "'%"+query.trim().toUpperCase()+"%' "+ 
                "order by nome").getResultList();
		em.close();
		return results;
	}
	
	public List<Supervisor> supervisores(String query) throws Exception {
		EntityManager em = JPAUtil.getEntityManager();
		List<Supervisor> results = em.createQuery( 
                "from Supervisor where upper(nome) like "+ 
                "'%"+query.trim().toUpperCase()+"%' "+ 
                "order by nome").getResultList();
		em.close();
		return results;
	}
	
	public void incluir() {
		editando = true;
		selecionado = new Vigilante();
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

	public List<Vigilante> getLista() {
		return lista;
	}

	public void setLista(List<Vigilante> lista) {
		this.lista = lista;
	}

	public Vigilante getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Vigilante selecionado) {
		this.selecionado = selecionado;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}
	
}
