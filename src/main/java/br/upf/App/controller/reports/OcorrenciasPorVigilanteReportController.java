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

import br.upf.App.jpa.JPAUtil;
import br.upf.App.model.Funcionario;
import br.upf.App.controller.LoginController;
import br.upf.App.model.Vigilante;
import br.upf.App.utils.RelatorioUtil;

@ManagedBean
@RequestScoped
public class OcorrenciasPorVigilanteReportController implements Serializable {

	private Date dataIni;
	private Date dataFim;
	private Funcionario funcionarioSelecionado;

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;

	public OcorrenciasPorVigilanteReportController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void gerar() {
		try {
			HashMap parameters = new HashMap();
			parameters.put("dataIni", dataIni);
			parameters.put("dataFim", dataFim);
			parameters.put("vigilante", funcionarioSelecionado == null ? loginController.getUsuarioLogado().getId() : funcionarioSelecionado.getId());

			RelatorioUtil.rodarRelatorioPDF("WEB-INF/Relatorios/Vigilante/OcorrenciasPorVigilanteReport.jasper", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("Erro", new FacesMessage(e.getMessage()));
		}
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	

	

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public List<Vigilante> funcionarios(String query) throws Exception {
		EntityManager em = JPAUtil.getEntityManager();
		List<Vigilante> results = em.createQuery(
				"from Vigilante where upper(nome) like " + "'%" + query.trim().toUpperCase() + "%' " + "order by nome")
				.getResultList();
		em.close();
		return results;
	}

	public Funcionario getFuncionarioSelecionado() {
		return funcionarioSelecionado;
	}

	public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
		this.funcionarioSelecionado = funcionarioSelecionado;
	}

}
