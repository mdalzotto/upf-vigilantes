package br.upf.App.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.upf.App.controller.LoginController;

@WebFilter(urlPatterns={"/faces/Views/*"})
public class FiltroLogin implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = ((HttpServletRequest) arg0);
		HttpServletResponse res = (HttpServletResponse) arg1;
		HttpSession ses = req.getSession();
		// --------------------------------------
		LoginController lc = (LoginController) ses.getAttribute("loginController");
		if ((lc == null) || (lc.getUsuarioLogado() == null)) {
			// est� tentando um acesso indevido
			res.sendRedirect("/App/faces/LoginForm.xhtml");
		} else {
			// passou pela autentica��o e logou corretamente! Pode segui!!!
			arg2.doFilter(arg0, arg1);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
