package br.net.rwd.website.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

//@Controller("uteis")
@ManagedBean(name = "uteis")
@RequestScoped
public class Uteis {

	// data formato: 2010
	public String getAnoAtual() {
		Date hoje = Calendar.getInstance().getTime();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy");
		String formatoSaida = formato.format(hoje);
		return formatoSaida;
	}

	// data formato: 16/12/10
	public String getDataCurta() {
		Date dt = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		return df.format(dt);
	}

	// data formato: 16/12/2010
	public String getDataAbreviada() {
		Date dt = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		return df.format(dt);
	}

	// data formato: 16 de Dezembro de 2010
	public String getDataLonga() {
		Date dt = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		return df.format(dt);
	}

	// data formato: Quinta-feira, 16 de Dezembro de 2010
	public String getDataCompleta() {
		Date dt = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
		return df.format(dt);
	}

	// valida a entrada de e-mails no cadastro
	public void validaEmail(FacesContext context, UIComponent componente,
			Object objeto) throws ValidatorException {
		String digitado = (String) objeto;
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(digitado);
		boolean matchFound = m.matches();

		if (!matchFound) {
			((UIInput) componente).setValid(false);
			FacesMessage message = new FacesMessage("E-mail inválido");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(componente.getClientId(context), message);
		}
	}

}
