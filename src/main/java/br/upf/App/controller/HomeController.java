package br.upf.App.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.PrimeFaces;

@ManagedBean
@SessionScoped
public class HomeController implements Serializable {
	
	@ManagedProperty(value = "#{loginController}")
	private LoginController loginController;
	
	public HomeController() {
		super();
	}

	public void abrirPopup(String url, int largura, int altura, boolean modal) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", modal);
		options.put("width", largura);
		options.put("height", altura);
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("headerElement", "customheader");
		options.put("resizable", false);
		options.put("minimizable", true);

		options.put("maximizable", true);
		// até Primefaces 6.1
		// RequestContext.getCurrentInstance().openDialog(url, options, null);
		// a partir do Primefaces 6.2 e 7.0
		PrimeFaces.current().dialog().openDynamic(url, options, null);
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

}
