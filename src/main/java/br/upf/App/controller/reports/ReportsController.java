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

@ManagedBean
@RequestScoped
public class ReportsController implements Serializable{
	
	private String nomeUsuario;

	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;

	public ReportsController() {
		super();
	}

	public void postosReport() {
		try {   
	         HashMap parameters = new HashMap(); 
	         RelatorioUtil.rodarRelatorioPDF("WEB-INF/Relatorios/Posto/PostoReport.jasper", 
	                       parameters); 
	      } catch (Exception e) {
	         e.printStackTrace(); 
	         FacesContext.getCurrentInstance().addMessage("Erro", new FacesMessage(e.getMessage())); 
	      }
	}
	
	public void tipoOcorrenciaReport() {
		
		try {   
	         HashMap parameters = new HashMap(); 
	         parameters.put("nomeUsuario", loginController.getUsuarioLogado().getNome());
	         RelatorioUtil.rodarRelatorioPDF("WEB-INF/Relatorios/TipoOcorrencia/TipoOcorrenciaReport.jasper", 
	                       parameters); 
	      } catch (Exception e) { 
	         e.printStackTrace(); 
	         FacesContext.getCurrentInstance().addMessage("Erro", new FacesMessage(e.getMessage())); 
	      }
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
