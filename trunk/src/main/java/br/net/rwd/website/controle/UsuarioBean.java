package br.net.rwd.website.controle;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.net.rwd.website.entidade.Usuario;
import br.net.rwd.website.servico.UsuarioServico;

@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean extends UtilBean {
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{usuarioServico}")
	private UsuarioServico model;
	private Usuario usuario;
	private List<Usuario> usuarios;
	private String nomeUsuario;
	private boolean editMode;

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

	public void salvar() {
		System.out.println("web_cod usuario " + usuario.getUsu_cod());
		if (usuario.getUsu_cod() == null || usuario.getUsu_cod().intValue() == 0) {
			usuario = model.createUsuario(usuario);
			usuario = new Usuario();
			addInfoMessage("Usuario criado com sucesso.");
		} else {
			model.updateUsuario(usuario);
			addInfoMessage("Usuario alterado com sucesso.");
		}
	}

	public void delete() {
		model.deleteUsuario(usuario);
		if (nomeUsuario != null && !nomeUsuario.isEmpty())
			usuarios = model.getUsuariosByName(nomeUsuario);
		else
			usuarios = model.getAllUsuarios();
		// return "";
	}

	public void create() {
		this.usuario = new Usuario();
		this.editMode = true;
	}

	public void update() {
		this.editMode = true;
	}

	public String cancel() {
		this.editMode = false;
		return "usuario";
	}

	public void filtrarUsuario(AjaxBehaviorEvent event) {
		if (nomeUsuario != null && !nomeUsuario.isEmpty()) {
			usuarios = model.getUsuariosByName(nomeUsuario);
		} else {
			usuarios = model.getAllUsuarios();
		}
	}

	public List<Usuario> getUsuarios() {
		if (usuarios == null) {
			usuarios = model.getAllUsuarios();
		}
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
}
