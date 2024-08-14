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
import br.upf.App.utils.RelatorioUtil;
import br.upf.App.model.Local;
import br.upf.App.model.Predio;
import br.upf.App.utils.JSFUtil;

@ManagedBean
@RequestScoped
public class OcorrenciasTotaisMesReportController implements Serializable {

	private Predio predioSelecionado;
	private Local localSelecionado;
	private String dataMes;
	private String dataAno;
	private String nomeUsuario;

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;

	public String init() {
		if (!loginController.getPermissaoSupervisor()) {
			JSFUtil.redirecionarPagina("faces/Views/Dashboard.xhtml");
		}
		return "";
	}

	public OcorrenciasTotaisMesReportController() {
		super();
	}

	public List<Local> locais(String query) throws Exception {
		EntityManager em = JPAUtil.getEntityManager();
		List<Local> results = em.createQuery(
				"from Local where upper(nome) like " + "'%" + query.trim().toUpperCase() + "%' " + "order by nome")
				.getResultList();
		em.close();
		return results;
	}

	public List<Predio> predios(String query) throws Exception {
		EntityManager em = JPAUtil.getEntityManager();
		List<Predio> results = em.createQuery("from Predio where upper(descricao) like " + "'%"
				+ query.trim().toUpperCase() + "%' " + "order by descricao").getResultList();
		em.close();
		return results;
	}


	public void gerar() {

		try {
			HashMap parameters = new HashMap();
			parameters.put("textoPredioSelecionado", predioSelecionado == null ? "Todos" : (predioSelecionado.getDescricao()));
			parameters.put("filtroPredioSelecionado", predioSelecionado == null ? "predio.id is not null" : "predio.id = " + (predioSelecionado.getId()));
			parameters.put("textoLocalSelecionado", localSelecionado == null ? "Todos" : (localSelecionado.getNome()));
			parameters.put("filtroLocalSelecionado",localSelecionado == null ? "local.id is not null" : "local.id = " + (localSelecionado.getId()));
			parameters.put("nomeUsuario", loginController.getUsuarioLogado().getNome());
			RelatorioUtil.rodarRelatorioPDF("WEB-INF/Relatorios/Ocorrencias/OcorrenciasTotalMesReport.jasper",
					parameters);
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

	public Local getLocalSelecionado() {
		return localSelecionado;
	}

	public void setLocalSelecionado(Local localSelecionado) {
		this.localSelecionado = localSelecionado;
	}

	public String getDataMes() {
		return dataMes;
	}

	public void setDataMes(String dataMes) {
		this.dataMes = dataMes;
	}

	public String getDataAno() {
		return dataAno;
	}

	public void setDataAno(String dataAno) {
		this.dataAno = dataAno;
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
