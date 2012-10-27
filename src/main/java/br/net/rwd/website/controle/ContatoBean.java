package br.net.rwd.website.controle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.net.rwd.website.entidade.Site;
import br.net.rwd.website.servico.SiteServico;
import br.net.rwd.website.util.EmailUtil;

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
		EmailUtil mail = new EmailUtil();
		Site site = modelsite.selecionarSite();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		String numIp = request.getRemoteHost();
		String urlSite = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());

    	mail.setObj(site);
    	mail.setDe(email);
    	mail.setDeNome(nome);
    	mail.setPara(site.getWeb_email());
    	mail.setParaNome(site.getWeb_titulo());
    	mail.setDestinatariosNormais(null);
    	mail.setDestinatariosOcultos(email);
    	mail.setAssunto(assunto);
    	mail.setMensagem(mensagemHtml(site.getWeb_titulo(), site.getWeb_site(), site.getWeb_slogan(), urlSite, numIp));
    	mail.setAnexo(null);
    	if(mail.enviarAutenticado())
    	addInfoMensagem(assunto+" enviado com sucesso!");
    	
        return "contato"; 
    }  
	
	public String mensagemHtml(String tituloSite, String nomeSite, String sloganSite, String urlSite, String numIp) {
		String html = "<html><head><title>E-mail automático via formulário</title>" +
		"<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=ISO-8859-1\">" +
	    "</head>" +
	    "<body lang='PT-BR' link='#FFFFFF' vlink='#FFFFFF'  alink='#C6CBCC'>" +
	    "<div align='center'>" +
	    "<table align='center' width='600' border='1' cellspacing='10' cellpadding='10' " +
	    "bordercolor='#000000' style='background-color:#ffffff;font-family:verdana,Arial,Helvetica,sans-serif;font-size:13px;border:2px solid #000000;'>" +
	    "<tr>" +
	    "<td bgcolor='#000000' valign='top'>" +
	    "<table width='100%' border='0' cellspacing='10' cellpadding='0' align='center'>" +
	    "<tr>" +
	    "<td align='left' valign='middle' style='display:block;float:left;width:auto;overflow:hidden;height:70px;'>" +
	    "<a href='" + urlSite + "' target='_blank' style='color:#B2C629;outline:none;text-decoration:none;'>" +
        "<h1 style='font-family:Georgia, \"Times New Roman\", Times, serif;font-size:16px;text-transform:capitalize;'>"+ tituloSite +"</h1>" +
        "<p style='margin-top:-9px;padding:0;line-height:normal;font-size:8pt;color:#C6CBCC;'>"+sloganSite+"</p>" +
        "</a>" +
	    "</td>" +
	    "<td align='center' valign='middle' style='font-size:9pt;'><strong><a href='" + urlSite + "' target='_blank' " +
	    "style='text-decoration:none;color:#ffffff;font-family:verdana,Arial,Helvetica,sans-serif;color:#FFFFFF;text-transform:lowercase;'>" + nomeSite.toLowerCase() +
	    "</a></strong></td>" +
	    "</tr>" +
	    "</table>" +
	    "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td align='center' valign='middle' style='background-color:#B2C629;color:#ffffff;text-transform:uppercase;border:0px;'>" +
        "<strong>FORMULÁRIO DE "+assunto+"</strong>" +
	    "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td bgcolor='#F7F7F7' valign='top' style='border:0;'>" +
        "<table width='100%' align='left' border='0' cellspacing='10' cellpadding='0'>" +
        "<tr>" +
        "<td align='left' width='80'>Nome:</td>" +
        "<td align='left'>" + nome + "</td>" +
        "</tr>" +
        "<tr>" +
        "<td align='left' width='80'>E-mail:</td>" +
        "<td align='left'>" + email.toLowerCase() + "</td>" +
        "</tr>" +
        "</table>" +
	    "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td style='border:0'></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td bgcolor='#F7F7F7' valign='top' style='border:0'>" +
        "<table width='100%' align='left' border='0' cellspacing='10' cellpadding='0'>" +
        "<tr>" +
        "<td align='left' width='80'>Mensagem:</td>" +
        "<td>" + mensagem + "</td>" +
        "</tr>" +
        "</table>" +
        "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td style='border:0'></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td bgcolor='#F7F7F7' valign='top' style='border:0'>" +
        "<table width='100%' align='left' border='0' cellspacing='10' cellpadding='0'>" +
        "<tr>" +
        "<td align='left' width='80'>IP:</td>" +
        "<td align='left'>" + numIp + "</td>" +
        "</tr>" +
        "</table>" +
        "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td style='border:0;font-size:smaller' align='center' valign='middle'>" +
	    "<p align='center'>&#169; <a href='" + urlSite + "' target='_blank' style='color:#000000;'>" + tituloSite +
	    "</a> | Produzido por <a href='http://www.rwd.net.br' target='_blank' style='color:#000000;'>RWD</a></p>" +
        "</td>" +
	    "</tr>" +
	    "</table>" +
	    "</div>" +
	    "</body>" +
	    "</html>";
		return html;
	}
	
}
