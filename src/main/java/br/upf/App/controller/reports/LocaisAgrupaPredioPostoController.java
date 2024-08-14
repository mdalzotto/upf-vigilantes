package br.upf.App.controller.reports;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.upf.App.controller.LoginController;
import br.upf.App.jpa.JPAUtil;
import br.upf.App.model.Posto;
import br.upf.App.utils.JSFUtil;
import br.upf.App.utils.RelatorioUtil;
import br.upf.App.model.Predio;

@ManagedBean
@RequestScoped
public class LocaisAgrupaPredioPostoController implements Serializable {
	
	private Predio predioSelecionado;
	private Posto postoSelecionado;
	private String nomeUsuario;
	
	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;

	public String init() {
		if (!loginController.getPermissaoSupervisor()) {
			JSFUtil.redirecionarPagina("faces/Views/Dashboard.xhtml");
		}
		return "";
	}

	public LocaisAgrupaPredioPostoController() {
		super();
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
	
	public List<Predio> predios(String query) throws Exception {
		EntityManager em = JPAUtil.getEntityManager();
		List<Predio> results = em.createQuery(
				"from Predio where upper(descricao) like "+
						"'%"+query.trim().toUpperCase()+"%' "+
						"order by descricao").getResultList();
		em.close();
		return results;
	}


	public void gerar() {

		try {
			HashMap parameters = new HashMap();
			parameters.put("textoPredioSelecionado", predioSelecionado == null ? "Todos" : (predioSelecionado.getDescricao()));
			parameters.put("filtroPredioSelecionado", predioSelecionado == null ? "predio.id is not null" : "predio.id = " + (predioSelecionado.getId()) );
			parameters.put("textoPostoSelecionado", postoSelecionado == null ? "Todos" : (postoSelecionado.getNome()));
			parameters.put("filtroPostoSelecionado", postoSelecionado == null ? "posto.id is not null" : "posto.id = " + (postoSelecionado.getId()) );
			parameters.put("nomeUsuario", loginController.getUsuarioLogado().getNome());
			RelatorioUtil.rodarRelatorioPDF("WEB-INF/Relatorios/Local/LocalPredioPosto.jasper", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("Erro", new FacesMessage(e.getMessage()));
		}

	}

	public Predio getPredioSelecionado() {
		return predioSelecionado;
	}

	public void setPredioSelecionado(Predio predioSelecionado) {
		this.predioSelecionado = predioSelecionado;
	}

	public Posto getPostoSelecionado() {
		return postoSelecionado;
	}

	public void setPostoSelecionado(Posto postoSelecionado) {
		this.postoSelecionado = postoSelecionado;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}
}
