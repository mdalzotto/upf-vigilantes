package br.upf.App.controller.reports;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.upf.App.controller.LoginController;
import br.upf.App.utils.RelatorioUtil;

@ManagedBean
@RequestScoped
public class OcorrenciasDoMesReportController implements Serializable {

	private Date dataIni;
	private Date dataFim;
	private String nomeUsuario;

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;

	public OcorrenciasDoMesReportController() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void gerar() {
		try {
			HashMap parameters = new HashMap();
			parameters.put("diaInicial", dataIni);
			parameters.put("diaFinal", dataFim);
			parameters.put("nomeUsuario", loginController.getUsuarioLogado().getNome());

			RelatorioUtil.rodarRelatorioPDF("WEB-INF/Relatorios/Ocorrencias/OcorrenciasDoMesReport.jasper", parameters);
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
