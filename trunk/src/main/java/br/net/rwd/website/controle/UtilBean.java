package br.net.rwd.website.controle;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class UtilBean  {
    
    private static void addMessage(String componentId, String errorMessage, FacesMessage.Severity severity) {
		FacesMessage message = new FacesMessage(errorMessage);
		message.setSeverity(severity);
		FacesContext.getCurrentInstance().addMessage(componentId, message);
	}

    public static void addInfoMensagem(String msg) {
        addMessage(null,msg,FacesMessage.SEVERITY_INFO);
    }
    
    public static void addAvisoMensagem(String msg) {
        addMessage(null,msg,FacesMessage.SEVERITY_WARN);
    }
    
    public static void addErroMensagem(String msg) {
        addMessage(null,msg,FacesMessage.SEVERITY_ERROR);
    }

	/*
	 * 
	 * Login / Utilitario JSF
	 * 
	 */
    
    @SuppressWarnings("rawtypes")
	public static Map getSessionMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}
	
}
