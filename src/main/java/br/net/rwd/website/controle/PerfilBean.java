package br.net.rwd.website.controle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.net.rwd.website.entidade.Perfil;
import br.net.rwd.website.servico.PerfilServico;
import br.net.rwd.website.util.EnumPerfis;

@ManagedBean(name = "perfilBean")
@ViewScoped
public class PerfilBean extends UtilBean implements CrudBeans<Object> {

	private static final long serialVersionUID = 1L;
	
	@ManagedProperty("#{perfilServico}")
	private PerfilServico model;
	private Perfil perfil;
	private List<Perfil> perfis;
	private boolean modoEdicao;

	/* ------------------------------------------------- */
	
	private Integer per_cod;
	private String per_nome;
	
	
	/* ------------------------------------------------- */
	
	public PerfilServico getModel() {
		return model;
	}

	public void setModel(PerfilServico model) {
		this.model = model;
	}

	public Perfil getPerfil() {
		if (perfil == null) {
			perfil = new Perfil();
		}
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getPerfis() {
		if (perfis == null) {
			perfis = model.listarPerfis();
		}
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	public boolean isModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}
	
	/* ------------------------------------------------- */

	public Integer getPer_cod() {
		return per_cod;
	}

	public void setPer_cod(Integer per_cod) {
		this.per_cod = per_cod;
	}

	public String getPer_nome() {
		return per_nome;
	}

	public void setPer_nome(String per_nome) {
		this.per_nome = per_nome;
	}
	
	/* ------------------------------------------------- */

	@Override
	public void incluir() {
        this.perfil = new Perfil();
        this.modoEdicao = true;
	}

	@Override
	public void salvar() {
        if (perfil.getPer_cod() == null || perfil.getPer_cod().intValue() == 0) {
            perfil = model.incluirPerfil(perfil);
            perfil = new Perfil();
            addInfoMensagem("Perfil criado com sucesso.");
            retornar();
        } else {
            model.alterarPerfil(perfil);
            addInfoMensagem("Perfil alterado com sucesso.");
            retornar();
        }	
	}

	@Override
	public void atualizar() {
		this.modoEdicao = true;
	}

	@Override
	public void excluir() {
		model.excluirPerfil(perfil);
		retornar();
	}

	@Override
	public void filtrar(AjaxBehaviorEvent event) {
        if (per_nome != null && !per_nome.isEmpty()) {
            perfis = model.listarPerfil(per_nome);
        } else {
            perfis = model.listarPerfis();
        }
	}

	@Override
	public String retornar() {
        this.modoEdicao = false;
        perfis = model.listarPerfis();
        return "perfil";
	}
	
	public Map<EnumPerfis, String> getPerfilUsuario() {
		Map<EnumPerfis, String> mapParam = new HashMap<EnumPerfis, String>();
		for (EnumPerfis type : EnumPerfis.values()) {
			mapParam.put(type, type.name());
		}
		return mapParam;
	} 
}
