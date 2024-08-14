package br.upf.App.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;

public class JSFUtil {

	public static void messagemDeSucesso(String mensagem) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void messagemDeAtencao(String mensagem) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static void messagemDeErro(String mensagem) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public static String tratarErro(Throwable e) {
		if (e.getCause() != null) {
			return tratarErro(e.getCause());
		} else {

			int i = e.getMessage().lastIndexOf('(');
			int j = e.getMessage().lastIndexOf(')');

			String msg = e.getMessage().substring(i + 1, j) + " Já está cadastrado";

			return msg;

		}

	}

	public static void redirecionarPagina(String pagina) {
		FacesContext context = FacesContext.getCurrentInstance();
		String url = context.getExternalContext().getRequestContextPath();
		try {
			context.getExternalContext().redirect(url + "/" + pagina);
		} catch (IOException ex) {
		}
	}

}
