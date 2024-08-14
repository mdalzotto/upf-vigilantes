package br.upf.App.controller.reports;

import java.io.Serializable;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.upf.App.controller.LoginController;
import br.upf.App.utils.RelatorioUtil;
import br.upf.App.utils.JSFUtil;

@ManagedBean
@RequestScoped
public class VigilanteReportController implements Serializable {

	private String ativo;
	private String nomeUsuario;

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;

	public String init() {
		if (!loginController.getPermissaoSupervisor()) {
			JSFUtil.redirecionarPagina("faces/Views/Dashboard.xhtml");
		}
		return "";
	}

	public VigilanteReportController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void gerar() {

		try {
			HashMap parameters = new HashMap();
			parameters.put("textofiltroAtivo", ativo.equals("sim") ? "Sim" : "Não");
			parameters.put("filtroAtivo", ativo.equals("sim") ? "datainativo is null" : "datainativo is not null");
			parameters.put("nomeUsuario", loginController.getUsuarioLogado().getNome());
			RelatorioUtil.rodarRelatorioPDF("WEB-INF/Relatorios/Vigilante/VigilanteReport.jasper", parameters);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("Erro", new FacesMessage(e.getMessage()));
		}

	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
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
