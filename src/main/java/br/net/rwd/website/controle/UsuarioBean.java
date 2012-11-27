package br.net.rwd.website.controle;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

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

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.net.rwd.website.entidade.Site;
import br.net.rwd.website.entidade.Usuario;
import br.net.rwd.website.servico.SiteServico;
import br.net.rwd.website.servico.UsuarioServico;
import br.net.rwd.website.util.Criptografia;
import br.net.rwd.website.util.EmailUtil;

@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean extends UtilBean implements CrudBeans<Object> {
	
	@ManagedProperty("#{usuarioServico}")
	private UsuarioServico model;
	
	@ManagedProperty("#{siteServico}")
	private SiteServico modelsite;
	
	private Usuario usuario;
	private List<Usuario> usuarios;
	private boolean modoEdicao;
	private boolean modoEdicaoPerfil;

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
	private String loginExistente;
	
	/* ------------------------------------------------- */
	private String senhaExistente;
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
	
	public boolean isModoEdicaoPerfil() {
		return modoEdicaoPerfil;
	}

	public void setModoEdicaoPerfil(boolean modoEdicaoPerfil) {
		this.modoEdicaoPerfil = modoEdicaoPerfil;
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
	
	public String getLoginExistente() {
		return loginExistente;
	}

	/* ------------------------------------------------- */

	@Override
	public void incluir() {
        this.usuario = new Usuario();
        this.modoEdicao = true;
        this.modoEdicaoPerfil = true;
        addAvisoMensagem("A senha é obrigatória. Caso não seja informada o sistema irá gerá-la automaticamente!");
	}

	@Override
	public void salvar() {
		// senha automatica
		String senhaAuto = getGeraSenha();

		// compara as senhas digitadas
		if (!usuario.getUsu_senha().equals(this.getConfirmaSenha())) {
			addAvisoMensagem("Senhas não conferem.");
		} else {
			// remove espacos e mantem o e-mail em minusculo
			String emailUsuario = usuario.getUsu_email().trim().toLowerCase();
			usuario.setUsu_email(emailUsuario);

			// criptografia da senha
			if (usuario.getUsu_cod() == null || usuario.getUsu_cod().intValue() == 0) {
				// quando inserindo
				if (usuario.getUsu_senha().trim().isEmpty()) {
					usuario.setUsu_senha(Criptografia.criptografarMD5(senhaAuto));
				} else {
					senhaAuto = usuario.getUsu_senha();
					usuario.setUsu_senha(Criptografia.criptografarMD5(senhaAuto));
				}
			} else {
				// quando atualizando
				if (usuario.getUsu_senha().trim().isEmpty()) {
					// se for administrador gera nova senha
					if (getPerfilUsuarioLogado() == "ROLE_ADMINISTRADOR") {
						usuario.setUsu_senha(Criptografia.criptografarMD5(senhaAuto));
					} else {
						senhaAuto = "***** (Sem alteração.)";
						usuario.setUsu_senha(senhaExistente);
					}
				} else {
					senhaAuto = usuario.getUsu_senha();
					usuario.setUsu_senha(Criptografia.criptografarMD5(senhaAuto));
				}
			}

			// Verifica se é um insert
			if (usuario.getUsu_cod() == null || usuario.getUsu_cod().intValue() == 0) {
				/*---- NOVO ----*/
				// verifica se ja existe
				if (model.selecionarUsuarioExistente(emailUsuario)) {
					addErroMensagem("Usuário existente! Informe outro e-mail.");
				} else {
					
					if (this.usuario.getPer_roles().toString() == "[]") {
						addErroMensagem("Selecione o perfil.");
					} else {			
						// inclui perfil do usuario
						usuario.getPer_roles().add(this.usuario.getPer_roles().toString());

						// inclui
						usuario = model.incluirUsuario(usuario);

						// envia email
						envia(usuario.getUsu_nome(), usuario.getUsu_email(), senhaAuto,"Seu cadastro para acesso foi criado");

						usuario = new Usuario();
						addInfoMensagem("Usuário criado com sucesso.");
						retornar();
					}
				}
			} else {
				/*---- ATUALIZA ----*/
				// verifica se o email foi alterado
				if (!usuario.getUsu_email().equals(loginExistente)) {
					// verifica se ja existe
					if (model.selecionarUsuarioExistente(emailUsuario)) {
						addErroMensagem("Usuário existente! Informe outro e-mail.");
					} else {
						// envia email
						envia(usuario.getUsu_nome(), usuario.getUsu_email(), senhaAuto, "Seu e-mail do cadastro foi alterado");

						// atualiza com login novo
						usuario.setUsu_alteracao(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
						model.alterarUsuario(usuario);
						addInfoMensagem("Usuário alterado com sucesso.");
						retornar();
					}
				} else {
					// envia email
					envia(usuario.getUsu_nome(), usuario.getUsu_email(), senhaAuto, "Seu cadastro foi alterado");
					
					// atualiza com login antigo
					usuario.setUsu_alteracao(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
					model.alterarUsuario(usuario);
					addInfoMensagem("Usuário alterado com sucesso.");
					retornar();
				}
			}
		}
	}

	@Override
	public void atualizar() {
		loginExistente = usuario.getUsu_email();
		senhaExistente = usuario.getUsu_senha();
		this.modoEdicao = true;
		this.modoEdicaoPerfil = false;
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
	
	public String atribuiPermissao(Usuario usuario, String perfil) {
		this.usuario = usuario;

		Set<String> permissoes = this.usuario.getPer_roles();
		
		if (permissoes.contains(perfil)) {
			permissoes.remove(perfil);
		} else {
			permissoes.add(perfil);
		}
		return null;
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
			Usuario usuario = model.selecionarUsuarioLogin(((UserDetails)usuarioLogado).getUsername());
			if (usuario != null)
				username = usuario.getUsu_email();
		} else {
			username = usuarioLogado.toString();
		}
		return username;
	}
	
	/* ------------------------------------------------- */
	/* 						E-MAIL						 */
	/* ------------------------------------------------- */
	
	public void envia(String nome, String email, final String senha, String assunto) {
		EmailUtil mail = new EmailUtil();
		Site site = modelsite.selecionarSite();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		String numIp = request.getRemoteHost();
		String urlSite = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());

    	mail.setObj(site);
    	mail.setDe(site.getWeb_email());
    	mail.setDeNome(site.getWeb_titulo());
    	mail.setPara(email);
    	mail.setParaNome(nome);
    	mail.setDestinatariosNormais(null);
    	mail.setDestinatariosOcultos(null);
    	mail.setAssunto(assunto);
    	mail.setMensagem(mensagemHtml(nome, email, senha, urlSite, numIp, site.getWeb_proprietario(), site.getWeb_titulo(), site.getWeb_slogan(), site.getWeb_site()));
    	mail.setAnexo(null);
    	if(mail.enviarAutenticado())
    	addInfoMensagem("As informações de acesso foram envidas para o e-mail: " + email);
    }  
    
	private String mensagemHtml(String nome, String login, final String senha, String urlSite, String numIp, String adminSite, String tituloSite, String sloganSite, String nomeSite) {
		String html = "<html><head><title>E-mail automático</title>" +
		"<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' \\>" +
	    "</head>" +
	    "<body link='#FFFFFF' vlink='#FFFFFF' alink='#C6CBCC'>" +
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
        "<strong>CONTA DE USUÁRIO</strong>" +
	    "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td bgcolor='#F9F9F9' valign='top' style='border:0;'>" +
	    "<table width='100%' align='left' border='0' cellspacing='10' cellpadding='0'>" +
	    "<tr>" +
	    "<td colspan='2' align='left'><p align='left'>Parabéns! <strong>"+nome.toUpperCase()+"</strong></p><p align='justify'> " +
	    "Você já pode acessar a <a href='" + urlSite + "/admin/' target='_blank' style='color:#000000;'>área administrativa</a> do site "+tituloSite+", " +
	    "para fazer seu login utilize as informações atualizadas de usuário de senha abaixo.</p></td>" +
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
	    "<td align='left' width='150'>Usuário:</td>" +
	    "<td align='left' style='color:#000000;'>" + login + "</td>" +
	    "</tr>" +
	    "<td align='left' width='150'>Senha:</td>" +
	    "<td align='left'>" + senha + "</td>" +
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
	    "<td align='left' width='150'>IP:</td>" +
	    "<td align='left'>" + numIp + "</td>" +
	    "</tr>" +
	    "</table>" +
        "</td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td style='border:0'></td>" +
	    "</tr>" +
	    "<tr>" +
	    "<td align='left' style='border:0'>" +
        
        "<p>"+adminSite+"</p>"+
        "<p><strong>"+tituloSite+"</strong><br />"+sloganSite+"<br /><a href='"+urlSite+"' target='_blank' style='color:#36F;'>"+nomeSite.toLowerCase()+"</a></p>" +
        
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
