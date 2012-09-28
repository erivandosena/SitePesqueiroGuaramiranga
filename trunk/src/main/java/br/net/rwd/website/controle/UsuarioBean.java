package br.net.rwd.website.controle;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.net.rwd.website.entidade.Usuario;
import br.net.rwd.website.servico.UsuarioServico;
import br.net.rwd.website.util.Criptografia;

@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean extends UtilBean implements CrudBeans<Object> {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{usuarioServico}")
	private UsuarioServico model;
	private Usuario usuario;
	private List<Usuario> usuarios;
	private boolean modoEdicao;

	/* ------------------------------------------------- */
	
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
	
	/* ------------------------------------------------- */
	
	public UsuarioServico getModel() {
		return model;
	}

	public void setModel(UsuarioServico model) {
		this.model = model;
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
	
	/* ------------------------------------------------- */

	@Override
	public void incluir() {
        this.usuario = new Usuario();
        this.modoEdicao = true;
	}

	@Override
	public void salvar() {
		Usuario usuarioExistente = model.selecionarUsuarioLogin(usuario.getUsu_email());
		usuario.setUsu_senha(Criptografia.criptografarMD5(usuario.getUsu_senha()));
        
		if (usuario.getUsu_cod() == null || usuario.getUsu_cod().intValue() == 0) {	
			
        	if (usuarioExistente != null && usuarioExistente.getUsu_email().equals(usuario.getUsu_email())) {
        		addErroMensagem("Usuário existente.");
			} else { 
	        	usuario = model.incluirUsuario(usuario);
	            usuario = new Usuario();
	            addInfoMensagem("Usuario criado com sucesso.");
	            retornar();
			}
        	
        } else {
        	if (usuario.getUsu_nome().equals("Administrador")) {
        		addErroMensagem("Operação não permitida.");
			} else {
            model.alterarUsuario(usuario);
            addInfoMensagem("Usuario alterado com sucesso.");
            retornar();
			}
        }	
	}

	@Override
	public void atualizar() {
		this.modoEdicao = true;
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

}
