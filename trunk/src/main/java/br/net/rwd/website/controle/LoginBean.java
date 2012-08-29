package br.net.rwd.website.controle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;

@ManagedBean(name = "loginBean")
@ViewScoped
public class LoginBean {
	
	public LoginBean() {
	}

	public String logar() {
		try {
			RequestDispatcher dispatcher = UtilBean.getServletRequest().getRequestDispatcher("/j_spring_security_check");
			dispatcher.forward(UtilBean.getServletRequest(),UtilBean.getServletResponse());
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception ex) {
			UtilBean.exibirMensagemErro(ex.getMessage());
			return null;
		}
		return null;
	}

}
