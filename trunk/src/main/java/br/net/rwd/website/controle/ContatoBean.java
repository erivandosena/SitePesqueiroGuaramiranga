package br.net.rwd.website.controle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.net.rwd.website.entidade.Site;
import br.net.rwd.website.servico.SiteServico;
import br.net.rwd.website.util.CommonsMailUtil;
import br.net.rwd.website.util.Uteis;

@ManagedBean(name="contatoBean")
@ViewScoped
public class ContatoBean extends UtilBean {

	@ManagedProperty("#{siteServico}")
	private SiteServico modelsite;

	private String nome;
	private String email;
	private String mensagem;
	private String assunto;

	public SiteServico getModelsite() {
		return modelsite;
	}

	public void setModelsite(SiteServico modelsite) {
		this.modelsite = modelsite;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

    public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	
	public String envia() {
		CommonsMailUtil mail = new CommonsMailUtil();
		Site site = modelsite.selecionarSite();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		String numIp = request.getRemoteHost();
		String urlSite = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
		String urlImagem = urlSite+"/resources/images/"+site.getWeb_logomarca();
		if(site.getWeb_logomarca() == null)
			urlImagem = urlSite+"/resources/images/nulo.png";

    	mail.setObj(site);
    	mail.setDe(email);
    	mail.setDeNome(nome);
    	mail.setPara(site.getWeb_email());
    	mail.setParaNome(site.getWeb_titulo());
    	mail.setDestinatariosNormais(null);
    	mail.setDestinatariosOcultos(email);
    	mail.setAssunto(assunto);
    	mail.setMensagem(mensagemHtml(site.getWeb_titulo(), urlSite, urlImagem, numIp));
    	mail.setMensagemAlternativa(Uteis.html2text(mail.getMensagem()));
    	mail.setAnexo(null);
    	mail.enviarEmailHtml();

    	addInfoMensagem(assunto+" enviado com sucesso!");
        return "contato"; 
    }  
	
	public String mensagemHtml(String tituloSite, String urlSite, String urlImagem, String numIp) {
		String html = "<html><head><title>Mensagem</title>" +
		"<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>" +
	    "</head>" +
	    "<body lang='PT-BR' link='blue' vlink='purple'>" +
	    "<div align='center'>" +
	    "<table align='center' width='600' border='1' cellspacing='10' cellpadding='10' " +
	    "bordercolor='#CCCCCC' style='font-family:Tahoma, Geneva, sans-serif;font-size:small;'>" +
	    "<tr>" +
	    "<td bgcolor='#000000' valign='top' style='border:0'>" +
	    "<table width='100%' border='0' cellspacing='10' cellpadding='0' align='center'>" +
	    "<tr>" +
	    "<td align='center' valign='middle'>" +
	    "<a href='" + urlSite + "' target='_blank'>" +
	    "<img src='"+urlImagem+"' border='0' align='middle' width='286'/></a>" +
	    "</td>" +
	    "<td align='center' valign='middle' style='color:#FFFFFF;text-transform:uppercase;'><strong>FORMULÁRIO DE "+assunto+"</strong></td>" +
	    "</tr>" +
	    "</table>" +
	    "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td bgcolor='#F9F9F9' valign='top' style='border:0;'>" +
        "<table width='100%' align='left' border='0' cellspacing='10' cellpadding='0'>" +
        "<tr>" +
        "<td align='left'>Nome:</td>" +
        "<td align='left'><strong>" + nome + "</strong></td>" +
        "</tr>" +
        "<tr>" +
        "<td align='left'>E-mail:</td>" +
        "<td align='left'><strong>" + email.toLowerCase() + "</strong></td>" +
        "</tr>" +
        "</table>" +
	    "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td style='border:0'></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td bgcolor='#F9F9F9' valign='top' style='border:0'>" +
        "<table width='100%' align='left' border='0' cellspacing='10' cellpadding='0'>" +
        "<tr>" +
        "<td align='left'>Mensagem:</td>" +
        "<td>" + mensagem + "</td>" +
        "</tr>" +
        "</table>" +
        "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td style='border:0'></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td bgcolor='#F9F9F9' valign='top' style='border:0'>" +
        "<table width='100%' align='left' border='0' cellspacing='10' cellpadding='0'>" +
        "<tr>" +
        "<td align='left'>IP:</td>" +
        "<td align='left'>" + numIp + "</td>" +
        "</tr>" +
        "</table>" +
        "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td style='border:0'><hr /></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td style='border:0;font-size:smaller' align='center' valign='middle'>" +
	    "<p align='center'>&#169; <a href='" + urlSite + "' target='_blank'>" + tituloSite +
	    "</a> | Produzido por <a href='http://www.rwd.net.br' target='_blank'>RWD</a></p>" +
        "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td style='border:0'></td>" +
	    "</tr>" +
	    "</table>" +
	    "</div>" +
	    "</body>" +
	    "</html>";
		return html;
	}
	
}
