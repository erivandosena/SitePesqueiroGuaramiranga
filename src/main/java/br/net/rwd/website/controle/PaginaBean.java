package br.net.rwd.website.controle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.net.rwd.website.entidade.Pagina;
import br.net.rwd.website.servico.PaginaServico;
import br.net.rwd.website.util.EnumPosicaoMenu;

@ManagedBean(name = "paginaBean")
@ViewScoped
public class PaginaBean extends UtilBean implements CrudBeans<Object> {

	@ManagedProperty("#{paginaServico}")
	private PaginaServico model;
	private Pagina pagina;
	private List<Pagina> paginas;
	private boolean modoEdicao;
	
	private Integer pag_cod;
	private String pag_titulo;
	private String pag_legenda;
	private String pag_conteudo;
	private String pag_extra;
	private String pag_posicao;

	public PaginaServico getModel() {
		return model;
	}

	public void setModel(PaginaServico model) {
		this.model = model;
	}

	public Pagina getPagina() {
		if (pagina == null) {
			pagina = new Pagina();
		}
		return pagina;
	}

	public void setPagina(Pagina pagina) {
		this.pagina = pagina;
	}

	public List<Pagina> getPaginas() {
		if (paginas == null) {
			paginas = model.listarPagina();
		}
		return paginas;
	}

	public void setPaginas(List<Pagina> paginas) {
		this.paginas = paginas;
	}

	public boolean isModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}
	
	/* ------------------------------------------------- */

	public Integer getPag_cod() {
		return pag_cod;
	}

	public void setPag_cod(Integer pag_cod) {
		this.pag_cod = pag_cod;
	}

	public String getPag_titulo() {
		return pag_titulo;
	}

	public void setPag_titulo(String pag_titulo) {
		this.pag_titulo = pag_titulo;
	}

	public String getPag_legenda() {
		return pag_legenda;
	}

	public void setPag_legenda(String pag_legenda) {
		this.pag_legenda = pag_legenda;
	}

	public String getPag_conteudo() {
		return pag_conteudo;
	}

	public void setPag_conteudo(String pag_conteudo) {
		this.pag_conteudo = pag_conteudo;
	}

	public String getPag_extra() {
		return pag_extra;
	}

	public void setPag_extra(String pag_extra) {
		this.pag_extra = pag_extra;
	}

	public String getPag_posicao() {
		return pag_posicao;
	}

	public void setPag_posicao(String pag_posicao) {
		this.pag_posicao = pag_posicao;
	}

	@Override
	public void incluir() {
        this.pagina = new Pagina();
        this.modoEdicao = true;	
	}

	@Override
	public void salvar() {
        if (pagina.getPag_cod() == null || pagina.getPag_cod().intValue() == 0) {
            pagina = model.incluirPagina(pagina);
            pagina = new Pagina();
            addInfoMensagem("Pagina criada com sucesso.");
            retornar();
        } else {
            model.alterarPagina(pagina);
            addInfoMensagem("Pagina alterada com sucesso.");
            retornar();
        }
	}

	@Override
	public void atualizar() {
		this.modoEdicao = true;
	}

	@Override
	public void excluir() {
		model.excluirPagina(pagina);
		retornar();
	}

	@Override
	public void filtrar(AjaxBehaviorEvent event) {
        if (pag_titulo != null && !pag_titulo.isEmpty()) {
            paginas = model.listarPagina(pag_titulo);
        } else {
            paginas = model.listarPagina();
        }
	}

	@Override
	public String retornar() {
        this.modoEdicao = false;
        paginas = model.listarPagina();
        return "pagina";
	}
	
	public Map<EnumPosicaoMenu, String> getPosicoes() {
		Map<EnumPosicaoMenu, String> mapParam = new HashMap<EnumPosicaoMenu, String>();
		for (EnumPosicaoMenu type : EnumPosicaoMenu.values()) {
			mapParam.put(type, type.name());
		}
		return mapParam;
	}  

}
