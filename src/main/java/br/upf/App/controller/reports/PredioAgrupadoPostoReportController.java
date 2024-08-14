package br.upf.App.controller.reports;

import java.io.Serializable;
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
import br.upf.App.utils.RelatorioUtil;
import br.upf.App.model.Posto;
import br.upf.App.utils.JSFUtil;

@ManagedBean
@RequestScoped
public class PredioAgrupadoPostoReportController implements Serializable {

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

	public PredioAgrupadoPostoReportController() {
		super();
		// TODO Auto-generated constructor stub
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

	public void gerar() {
		try {
			HashMap parameters = new HashMap();
			parameters.put("textoPostoSelecionado", postoSelecionado == null ? "Todos" : (postoSelecionado.getNome()));
			parameters.put("filtroPostoSelecionado", postoSelecionado == null ? "posto.id is not null" : "posto.id = " + (postoSelecionado.getId()) );
			parameters.put("nomeUsuario", loginController.getUsuarioLogado().getNome());
			RelatorioUtil.rodarRelatorioPDF("WEB-INF/Relatorios/Predio/PredioAgrupadoPostoReport.jasper", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("Erro", new FacesMessage(e.getMessage()));
		}

	}

	public Posto getPostoSelecionado() {
		return postoSelecionado;
	}

	public void setPostoSelecionado(Posto postoSelecionado) {
		this.postoSelecionado = postoSelecionado;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

}
