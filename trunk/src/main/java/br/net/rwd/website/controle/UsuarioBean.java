package br.net.rwd.website.controle;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.net.rwd.website.entidade.Perfil;
import br.net.rwd.website.entidade.Site;
import br.net.rwd.website.entidade.Usuario;
import br.net.rwd.website.servico.PerfilServico;
import br.net.rwd.website.servico.SiteServico;
import br.net.rwd.website.servico.UsuarioServico;
import br.net.rwd.website.util.Criptografia;

@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean extends UtilBean implements CrudBeans<Object> {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{usuarioServico}")
	private UsuarioServico model;
	
	@ManagedProperty("#{siteServico}")
	private SiteServico modelsite;
	
	private Usuario usuario;
	private List<Usuario> usuarios;
	private boolean modoEdicao;

	private Integer usu_cod;
	private String usu_nome;
	private String usu_email;
	private String usu_senha;
	private String usu_endereco;
	private String usu_numero;
	private String usu_cidade;
	private String usu_cep;
	private String usu_estado;
	private boolean usu_situacao = true;
	
	private String confirmaSenha;
	private String loginAnterior;
	
	/* ------------------------------------------------- */
	
	@ManagedProperty("#{perfilServico}")
	private PerfilServico modelperfil;
	private Perfil perfil;
	private List<Perfil> perfis;
	
	private String per_nome;
	private String per_role;

	/* ------------------------------------------------- */

	public UsuarioServico getModel() {
		return model;
	}

	public void setModel(UsuarioServico model) {
		this.model = model;
	}

	public SiteServico getModelsite() {
		return modelsite;
	}

	public void setModelsite(SiteServico modelsite) {
		this.modelsite = modelsite;
	}

	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		if (usuarios == null) {
			usuarios = model.listarUsuarios();
		}
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public boolean isModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}
	
	/* ------------------------------------------------- */

	public Integer getUsu_cod() {
		return usu_cod;
	}

	public void setUsu_cod(Integer usu_cod) {
		this.usu_cod = usu_cod;
	}

	public String getUsu_nome() {
		return usu_nome;
	}

	public void setUsu_nome(String usu_nome) {
		this.usu_nome = usu_nome;
	}

	public String getUsu_email() {
		return usu_email;
	}

	public void setUsu_email(String usu_email) {
		this.usu_email = usu_email;
	}

	public String getUsu_senha() {
		return usu_senha;
	}

	public void setUsu_senha(String usu_senha) {
		this.usu_senha = usu_senha;
	}

	public String getUsu_endereco() {
		return usu_endereco;
	}

	public void setUsu_endereco(String usu_endereco) {
		this.usu_endereco = usu_endereco;
	}

	public String getUsu_numero() {
		return usu_numero;
	}

	public void setUsu_numero(String usu_numero) {
		this.usu_numero = usu_numero;
	}

	public String getUsu_cidade() {
		return usu_cidade;
	}

	public void setUsu_cidade(String usu_cidade) {
		this.usu_cidade = usu_cidade;
	}

	public String getUsu_cep() {
		return usu_cep;
	}

	public void setUsu_cep(String usu_cep) {
		this.usu_cep = usu_cep;
	}

	public String getUsu_estado() {
		return usu_estado;
	}

	public void setUsu_estado(String usu_estado) {
		this.usu_estado = usu_estado;
	}
	
	public boolean isUsu_situacao() {
		return usu_situacao;
	}

	public void setUsu_situacao(boolean usu_situacao) {
		this.usu_situacao = usu_situacao;
	}
	
	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}
	
	/* ------------------------------------------------- */

	public PerfilServico getModelperfil() {
		return modelperfil;
	}

	public void setModelperfil(PerfilServico modelperfil) {
		this.modelperfil = modelperfil;
	}

	public Perfil getPerfil() {
		if(perfil == null) {
			perfil = new Perfil();
		}
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getPerfis() {
		if(perfis == null) {
			perfis = modelperfil.listarPerfis();
		}
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public String getPer_nome() {
		return per_nome;
	}

	public void setPer_nome(String per_nome) {
		this.per_nome = per_nome;
	}

	public String getPer_role() {
		return per_role;
	}

	public void setPer_role(String per_role) {
		this.per_role = per_role;
	}
	
	/* ------------------------------------------------- */

	@Override
	public void incluir() {
        this.usuario = new Usuario();
        this.modoEdicao = true;
        addAvisoMensagem("A senha é obrigatória. Caso não seja informada o sistema irá gerá-la automaticamente!");
	}

	@Override
	public void salvar() {
		// senha automatica
		String senha = getGeraSenha();

		// compara as senhas digitadas
		if (!usuario.getUsu_senha().equals(this.getConfirmaSenha())) {
			addAvisoMensagem("Senhas não conferem.");
		} else {
			// remove espacos e mantem o e-mail em minusculo
			String emailUsuario = usuario.getUsu_email().trim().toLowerCase();
			usuario.setUsu_email(emailUsuario);

			// criptografa a senha
			if (usuario.getUsu_senha() == null) {
				usuario.setUsu_senha(Criptografia.criptografarMD5(senha));
			} else {
				senha = usuario.getUsu_senha();
				usuario.setUsu_senha(Criptografia.criptografarMD5(usuario.getUsu_senha()));
			}

			if (usuario.getUsu_cod() == null || usuario.getUsu_cod().intValue() == 0) {
				/*---- NOVO ----*/
				// verifica se ja existe
				if (model.selecionarUsuarioExistente(emailUsuario)) {
					addErroMensagem("Usuário existente! Informe outro e-mail.");
				} else {
					// envia email
					envia(usuario.getUsu_nome(), usuario.getUsu_email(),"Seus dados para acesso", senha);
					
					//inclui
					usuario = model.incluirUsuario(usuario);
					usuario = new Usuario();
					addInfoMensagem("Usuário criado com sucesso.");
					retornar();
				}
			} else {
				/*---- ATUALIZA ----*/
				// verifica se o email foi alterado
				if (!usuario.getUsu_email().equals(loginAnterior)) {
					// verifica se ja existe
					if (model.selecionarUsuarioExistente(emailUsuario)) {
						addErroMensagem("Usuário existente! Informe outro e-mail.");
					} else {
						// envia email
						envia(usuario.getUsu_nome(), usuario.getUsu_email(), "Novo e-mail para acesso", senha);
						
						//atualiza com login novo
						model.alterarUsuario(usuario);
						addInfoMensagem("Usuário alterado com sucesso.");
						retornar();
					}
				} else {
					// envia email
					envia(usuario.getUsu_nome(), usuario.getUsu_email(),"Conta para acesso foi alterada", senha);
					
					//atualiza com login antigo
					model.alterarUsuario(usuario);
					addInfoMensagem("Usuário alterado com sucesso.");
					retornar();
				}

			}
		}
	}

	@Override
	public void atualizar() {
		loginAnterior = usuario.getUsu_email();
		this.modoEdicao = true;
		addAvisoMensagem("A senha é obrigatória. Caso não seja informada o sistema irá gerá-la automaticamente!");
	}

	@Override
	public void excluir() {
		if (usuario.getUsu_nome().equals("Administrador")) {
			addErroMensagem("Operação não permitida.");
		} else {
			model.excluirUsuario(usuario);
			retornar();
		}
	}

	@Override
	public void filtrar(AjaxBehaviorEvent event) {
        if (usu_nome != null && !usu_nome.isEmpty()) {
            usuarios = model.listarLikeUsuario("%"+usu_nome+"%");
        } else {
            usuarios = model.listarUsuarios();
        }
	}

	@Override
	public String retornar() {
        this.modoEdicao = false;
        usuarios = model.listarUsuarios();
        return "usuario";
	}
	
	private String getGeraSenha() {
		return String.format("%05x",  (int)(Math.random() * 999999L));
	}
	
	/* ------------------------------------------------- */
	/* 						LOGIN						 */
	/* ------------------------------------------------- */
	
	public String logar() throws IOException, ServletException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		RequestDispatcher dispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check?j_username=" + usu_email + "&j_password=" + Criptografia.criptografarMD5(usu_senha));
		dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
		FacesContext.getCurrentInstance().responseComplete();
		return null;
	}

	public String getUsuarioLogado() {
		String username = null;
		Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (usuarioLogado instanceof UserDetails) {
			username = model.selecionarUsuarioLogin(((UserDetails)usuarioLogado).getUsername()).getUsu_nome().toString();
		} else {
			username = usuarioLogado.toString().replace("anonymousUser","Visitante");
		}
		return username;
	}
	
	public boolean getStatusLogado() {
		Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (usuarioLogado instanceof UserDetails) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getPerfilUsuarioLogado() {
		Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (usuarioLogado instanceof UserDetails) {
			return ((UserDetails)usuarioLogado).getAuthorities().iterator().next().getAuthority().toString();
		} else {
			return null;
		}
	}
	
	public String getLoginUsuarioLogado() {
		String username = null;
		Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (usuarioLogado instanceof UserDetails) {
			username = model.selecionarUsuarioLogin(((UserDetails)usuarioLogado).getUsername()).getUsu_email().toString();
		} else {
			username = usuarioLogado.toString();
		}
		return username;
	}
	
	/* ------------------------------------------------- */
	/* 						E-MAIL						 */
	/* ------------------------------------------------- */
	
	public void envia(String nome, String email, String assunto, String senha) {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String urlSite = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
		String numIp = request.getRemoteHost();
		
		//String emailRemetente = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("remetente");

		Site site = modelsite.selecionarSite();

		
    	CommonsMailBean mail = new CommonsMailBean();
    	mail.setObj(site);
    	mail.setDe(site.getWeb_email());
    	mail.setDeNome(site.getWeb_titulo());
    	mail.setPara(email);
    	mail.setParaNome(nome);
    	mail.setDestinatariosNormais(null);
    	mail.setDestinatariosOcultos(null);
    	mail.setAssunto(assunto);
    	
		// incorporar a imagem e obter o ID de conteudo
    	HtmlEmail htmlemail = new HtmlEmail();
		URL url = null;
		String cid = null;
		try {
			url = new URL(urlSite+"/resources/images/logo.png");
			cid = htmlemail.embed(url, "Clique aqui!");
		} catch (MalformedURLException me) {
			me.printStackTrace();
		}  catch (EmailException ee) {
			ee.printStackTrace();
		}
		//ex: <html>The apache logo - <img src=\"cid:"+cid+"\"></html>
    	
    	mail.setMensagem(mensagemHtml(nome, email, senha, cid, urlSite, numIp));
    	String texto = "\nParabéns! "+nome.toUpperCase()+"\n\nVocê já pode acessar a área administrativa do site "+ urlSite.substring(7, urlSite.length()) +"/admin/, para fazer seu login utilize as informações atualizadas de acesso abaixo:\n\nNome de usuário: "+ email +"\nSenha: "+ senha +"\n\nIP: "+ numIp + "\n\n\n(c) "+ urlSite.substring(7, urlSite.length()) +" - Produzido por RWD (www.rwd.net.br)\n";
    	mail.setMensagemAlternativa(texto);
    	
    	mail.enviarEmailHtml();
    	
    	
    }  
    
	public String mensagemHtml(String nome, String login, String senha, String cidImagem, String url, String ip) {
		//HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		//String site = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
		
		//final String IMAGEM = "/resources/images/logo.png";
		
		String html = "<html><head><title>Mensagem</title>" +
		"<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />" +
	    "</head>" +
	    "<body lang='PT-BR' link='blue' vlink='purple'>" +
	    "<div align='center'>" +
	    "<table align='center' width='600' border='1' cellspacing='10' cellpadding='0' " +
	    "bordercolor='#CCCCCC' style='font-family:Tahoma, Geneva, sans-serif;font-size:small;'>" +
	    "<tr>" +
	    "<td bgcolor='#000000' valign='top' style='border:0'>" +
	    "<table width='600' border='0' cellspacing='10' cellpadding='0' align='left'>" +
	    "<tr>" +
	    "<td align='left'>" +
	    "<a href='" + url + "' target='_blank'>" +
	    "<img src='cid:"+cidImagem+"' border='0' align='middle' /></a>" +
	    "</td>" +
	    "<td align='center' style='color:#FFFFFF;text-transform:uppercase;'><strong>Conta de USUÁRIO</strong></td>" +
	    "</tr>" +
	    "</table>" +
	    "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td bgcolor='#F9F9F9' valign='top' style='border:0;'>" +
	    "<table border='0' cellspacing='10' cellpadding='0'>" +
	    "<tr>" +
	    "<td colspan='2' align='left'>Parabéns! <strong>"+nome.toUpperCase()+"</strong> <p> Você já pode acessar a área administrativa do site <a href='" + url + "/admin/' target='_blank'>" + url.substring(7, url.length()) +"</a>, para fazer seu login utilize as informações atualizadas de acesso abaixo:</p> </td>" +
	    "</tr>" +
	    "</table>" +
	    "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td style='border:0'></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td bgcolor='#F9F9F9' valign='top' style='border:0'>" +
	    "<table border='0' cellspacing='10' cellpadding='0'>" +
	    "<tr>" +
	    "<td align='left'>Nome de usuário:</td>" +
	    "<td align='left'><strong>" + login + "</strong></td>" +
	    "</tr>" +
	    "<td align='left'>Senha:</td>" +
	    "<td align='left'><strong>" + senha + "</strong></td>" +
	    "</tr>" +
	    "</table>" +
	    "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td bgcolor='#F9F9F9' valign='top' style='border:0'>" +
	    "<table border='0' cellspacing='10' cellpadding='0'>" +
	    "<tr>" +
	    "<td align='left'>IP:</td>" +
	    "<td align='left'>" + ip + "</td>" +
	    "</tr>" +
	    "</table>" +
	    "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td style='border:0'></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td style='border:0;font-size:smaller' align='center'>&copy; " +
	    "<a href='" + url + "' target='_blank'>" + url.substring(7, url.length()) +
	    "</a> | Produzido por <a href='http://www.rwd.net.br' target='_blank'>RWD</a>" +
	    "</td>" +
	    "</tr>" +
	    "</table>" +
	    "</div>" +
	    "</body>" +
	    "</html>";
		return html;
	}
	
}
