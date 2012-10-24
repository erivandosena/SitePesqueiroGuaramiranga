package br.net.rwd.website.util;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import br.net.rwd.website.controle.UtilBean;
import br.net.rwd.website.entidade.Site;

public class CommonsMailUtil extends UtilBean {
	
	private Logger log4j = Logger.getLogger(Class.class.getName());

	private Site obj;
	private String de;
	private String deNome;
	private String para;
	private String paraNome;
	private String destinatariosNormais;
	private String destinatariosOcultos;
	private String assunto;
	private String mensagem;
	private String mensagemAlternativa;
	private String anexo;

	public Site getObj() {
		return obj;
	}

	public void setObj(Site obj) {
		this.obj = obj;
	}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public String getDeNome() {
		return deNome;
	}

	public void setDeNome(String deNome) {
		this.deNome = deNome;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getParaNome() {
		return paraNome;
	}

	public void setParaNome(String paraNome) {
		this.paraNome = paraNome;
	}

	public String getDestinatariosNormais() {
		return destinatariosNormais;
	}

	public void setDestinatariosNormais(String destinatariosNormais) {
		this.destinatariosNormais = destinatariosNormais;
	}

	public String getDestinatariosOcultos() {
		return destinatariosOcultos;
	}

	public void setDestinatariosOcultos(String destinatariosOcultos) {
		this.destinatariosOcultos = destinatariosOcultos;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagemAlternativa() {
		return mensagemAlternativa;
	}

	public void setMensagemAlternativa(String mensagemAlternativa) {
		this.mensagemAlternativa = mensagemAlternativa;
	}

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public void enviarEmailHtml() {
		try {
				// Cria a mensagem de e-mail
				//MultiPartEmail email = new MultiPartEmail(); // Cria o e-mail preparado para os anexos
				HtmlEmail email = new HtmlEmail();

				// informacoes do servidor
				email.setHostName(this.obj.getWeb_smtp());
				if(this.obj.getWeb_porta() > 0) {
					email.setSmtpPort(this.obj.getWeb_porta());
				} 
				//email.setCharset("ISO-8859-1");

				// Autenticar no servidor
				if(this.obj.getWeb_conta() != null && this.obj.getWeb_senha() != null) {
				email.setAuthentication(this.obj.getWeb_conta(), this.obj.getWeb_senha());
				}
				
				if(this.obj.isWeb_ssl()) {
				email.setSSL(this.obj.isWeb_ssl());
				email.setSslSmtpPort(this.obj.getWeb_portassl().toString());
				}

				// montando o e-mail
				if (this.de != null) {
					email.setFrom(this.de, this.deNome);
				} else {
					email.setFrom(this.obj.getWeb_conta(), this.obj.getWeb_titulo());
				}
				email.addTo(this.para, this.paraNome);
				email.setSubject(this.assunto);

				// definir a mensagem de html
				//email.setHtmlMsg(this.mensagem);
				email.addPart(this.mensagem, "text/html;charset=iso-8859-1"); 

				// define a mensagem texto alternativa quando servidor nao aceitar html
				email.setTextMsg(this.mensagemAlternativa);

				List<InternetAddress> normais = this.montaDestinatarios(this.destinatariosNormais);
				if (normais != null) {
					email.setCc(normais);
				}

				List<InternetAddress> ocultos = this.montaDestinatarios(this.destinatariosOcultos);
				if (ocultos != null) {
					email.setBcc(ocultos);
				}

				// Cria o anexo
				if (this.anexo != null && this.anexo.trim().length() > 0) {
					EmailAttachment anexo = new EmailAttachment();
					anexo.setPath(this.anexo);
					anexo.setDisposition(EmailAttachment.ATTACHMENT);

					// Adiciona o anexo ao e-mail
					email.attach(anexo);
				}

				// envia o e-mail
				email.send();
				
		} catch (EmailException ee) {
			addErroMensagem("Erro ao enviar e-mail. Verifique informações sobre SMTP.");
			return;
		} catch (AddressException ae) {
			addErroMensagem("Erro ao enviar e-mail. Verifique endereços de e-mail.");
			return;
		} catch (Exception e) {
			log4j.error(e);
			addErroMensagem("Erro ao enviar e-mail. Verifique endereços de e-mail.");
			return;
		}
	}

	private List<InternetAddress> montaDestinatarios(String destinatarios) throws AddressException {
		List<InternetAddress> emails = null;
		if (destinatarios != null && destinatarios.trim().length() > 0) {
			String[] lista = destinatarios.split(";");
			emails = new ArrayList<InternetAddress>();
			for (int i = 0; i < lista.length; i++) {
				emails.add(new InternetAddress(lista[i]));
			}
		}
		return emails;
	}

}
