package br.net.rwd.website.util;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

import br.net.rwd.website.entidade.Site;

public class EmailUtil {
	
	private Logger log4j = Logger.getLogger(Class.class.getName());
	
	private String	usuario;
	private String	senha;

	private Site 	obj;
	private String	de;
	private String 	deNome;
	private String 	para;
	private String 	paraNome;
	private String 	destinatariosNormais;
	private String 	destinatariosOcultos;
	private String 	assunto;
	private String 	mensagem;
	private String 	mensagemAlternativa;
	private String 	anexo;
	
	public EmailUtil() {

	}

	public EmailUtil(String usuario, String senha) {
		this.usuario = usuario;
		this.senha = senha;
	}
	
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.usuario, this.senha);
	}

	public boolean enviarAutenticado() {
		final String	SERVIDOR_SMTP = this.obj.getWeb_smtp();
		final int		PORTA_SERVIDOR_SMTP = this.obj.getWeb_porta();
		final String	CONTA_PADRAO = this.obj.getWeb_conta();
		final String	SENHA_CONTA_PADRAO = this.obj.getWeb_senha();
		final boolean	CONEXAO_SSL = this.obj.isWeb_ssl();

		Properties config = new Properties(); 
		config.put("mail.smtp.host", SERVIDOR_SMTP);
		config.put("mail.smtp.port", PORTA_SERVIDOR_SMTP);
		config.put("mail.smtp.auth", CONEXAO_SSL);
		if(CONEXAO_SSL) {
		config.put("mail.smtp.starttls.enable", "true");
		config.put("mail.smtp.socketFactory.port", SERVIDOR_SMTP);  
		config.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
		config.put("mail.smtp.socketFactory.fallback", "false");
		}
		
		config.put("mail.debug", "true");

		Session sessao = Session.getInstance(config, new Authenticator() {
			@Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(CONTA_PADRAO, SENHA_CONTA_PADRAO);
	        }
		});

		try {
			MimeMessage email = new MimeMessage(sessao); 
			email.setHeader("Content-Type", "text/plain; charset=ISO-8859-1");
			email.setFrom(new InternetAddress(this.de, this.deNome));
			email.setRecipient(Message.RecipientType.TO, new InternetAddress(this.para, this.paraNome));

			InternetAddress[] normais = this.montaDestinatarios(this.destinatariosNormais);
			if (normais != null) {
				email.setRecipients(Message.RecipientType.CC, normais);
			}
			
			InternetAddress[] ocultos = this.montaDestinatarios(this.destinatariosOcultos);
			if (ocultos != null) {
				email.setRecipients(Message.RecipientType.BCC, ocultos);
			}

			email.setSubject(MimeUtility.encodeText(this.assunto, "ISO-8859-1", "B"));
			email.setSentDate(new Date());

			// Preparando o corpo de email
			MimeMultipart partesEmail = new MimeMultipart(); 
			MimeBodyPart corpoEmail = new MimeBodyPart();
			
			corpoEmail.setHeader("Content-Type","text/plain; charset=ISO-8859-1"); 
			corpoEmail.setContent(corpoEmail, "text/plain; charset=ISO-8859-1" ); 
			//corpoEmail.setHeader("Content-Transfer-Encoding", "quoted-printable");
			
			corpoEmail.setContent(this.mensagem, "text/html");
			partesEmail.addBodyPart(corpoEmail);

			// Anexando arquivo
			if (this.anexo != null && this.anexo.trim().length() > 0) {
				MimeBodyPart anexo = new MimeBodyPart();
				FileDataSource arquivo = new FileDataSource(this.anexo); 
				anexo.setDataHandler(new DataHandler(arquivo));
				anexo.setFileName(arquivo.getName());
				partesEmail.addBodyPart(anexo);
			} 

			email.setContent(partesEmail);

			if(CONEXAO_SSL) {
			Transport transport = sessao.getTransport("smtps");
			transport.connect(SERVIDOR_SMTP, PORTA_SERVIDOR_SMTP, CONTA_PADRAO, SENHA_CONTA_PADRAO);
			transport.sendMessage(email, email.getAllRecipients());
			transport.close();
			return true;
			}
			else 
			{
			Transport transport = sessao.getTransport("smtp");
			transport.connect(SERVIDOR_SMTP, CONTA_PADRAO, SENHA_CONTA_PADRAO);
			transport.sendMessage(email, email.getAllRecipients());
			transport.close();
			return true;
			}

		} catch (AddressException ae) {
			addErroMensagem("Erro ao verificar endereços de e-mail.");
			log4j.error(ae);
			return false;
		} catch (MessagingException me) {
			addErroMensagem("Erro ao enviar mensagem de e-mail.");
			log4j.error(me);
			return false;
		} catch (Exception e) {
			addErroMensagem("Erro ao enviar e-mail.");
			log4j.error(e);
			return false;
		}
	}

	public void enviarSimples() { 
		final String	SERVIDOR_SMTP = this.obj.getWeb_smtp();
		final int		PORTA_SERVIDOR_SMTP = this.obj.getWeb_porta();
		
		Properties config = new Properties(); 
		config.put("mail.smtp.host", SERVIDOR_SMTP);
		config.put("mail.smtp.port", PORTA_SERVIDOR_SMTP);

		Session sessao = Session.getInstance(config);

		try {
			MimeMessage email = new MimeMessage(sessao);
			email.setFrom(new InternetAddress(this.de, this.deNome));
			email.setRecipient(Message.RecipientType.TO, new InternetAddress(this.para, this.paraNome));

			InternetAddress[] normais = this.montaDestinatarios(this.destinatariosNormais);
			if (normais != null) {
				email.setRecipients(Message.RecipientType.CC, normais);
			}
			
			InternetAddress[] ocultos = this.montaDestinatarios(this.destinatariosOcultos);
			if (normais != null) {
				email.setRecipients(Message.RecipientType.BCC, ocultos);
			}

			email.setSubject(this.assunto);
			email.setSentDate(new Date());
			email.setText(this.mensagem);
			Transport.send(email);

			addInfoMensagem(this.assunto+" enviado com sucesso!");

		} catch (AddressException ae) {
			addErroMensagem("Erro ao verificar endereços de e-mail.");
			log4j.error(ae);
			return;
		} catch (MessagingException me) {
			addErroMensagem("Erro ao enviar mensagem de e-mail.");
			log4j.error(me);
			return;
		} catch (Exception e) {
			addErroMensagem("Erro ao enviar e-mail.");
			log4j.error(e);
			return;
		}
	}

	private InternetAddress[] montaDestinatarios(String destinatarios) throws AddressException { 
		InternetAddress[] emails = null;
		if (destinatarios != null && destinatarios.trim().length() > 0) {			
			String[] lista = destinatarios.split(";");
			emails = new InternetAddress[lista.length];
			for (int i = 0; i < lista.length; i++) {
				emails[i] = new InternetAddress(lista[i]);
			}
		}
		return emails;
	}

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
	
    private static void addMessage(String componentId, String errorMessage, FacesMessage.Severity severity) {
		FacesMessage message = new FacesMessage(errorMessage);
		message.setSeverity(severity);
		FacesContext.getCurrentInstance().addMessage(componentId, message);
	}
    
    public static void addErroMensagem(String msg) {
        addMessage(null,msg,FacesMessage.SEVERITY_ERROR);
    }
    
    public static void addInfoMensagem(String msg) {
        addMessage(null,msg,FacesMessage.SEVERITY_INFO);
    }

}
