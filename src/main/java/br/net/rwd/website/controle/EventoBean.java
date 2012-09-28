package br.net.rwd.website.controle;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.FileUploadEvent;

import br.net.rwd.website.entidade.Evento;
import br.net.rwd.website.entidade.Imagem;
import br.net.rwd.website.servico.EventoServico;
import br.net.rwd.website.servico.ImagemServico;
import br.net.rwd.website.util.Criptografia;
import br.net.rwd.website.util.FileParaBytes;
import br.net.rwd.website.util.Redimensiona;

@SuppressWarnings("restriction")
@ManagedBean(name = "eventoBean")
@ViewScoped
public class EventoBean extends UtilBean implements CrudBeans<Object> {

	@ManagedProperty("#{eventoServico}")
	private EventoServico model;
	
	@ManagedProperty("#{imagemServico}")
	private ImagemServico modelimagem;
	
	private Evento evento;
	private List<Evento> eventos;
	private boolean modoEdicao;
	private Integer pub_cod;
	private Date pub_data;
	private Date pub_dataalteracao;
	private String pub_titulo;
	private String pub_sumario;
	private String pub_conteudo;
	
	private Imagem imagem;
	private boolean modoEdicaoImagem;

	private Integer ima_cod;
	private String ima_descricao;
	private String ima_normal;
	private String ima_mini;
	
	private byte[] bytesImagem;
	private List<String> fotos;
	
	String nomeArquivo = null;
	String subPasta = null;
	File arquivo = null;
	
	private static ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
	private static final String PATH = extContext.getRealPath("/upload/");
	 
	public EventoServico getModel() {
		return model;
	}

	public void setModel(EventoServico model) {
		this.model = model;
	}

	public ImagemServico getModelimagem() {
		return modelimagem;
	}

	public void setModelimagem(ImagemServico modelimagem) {
		this.modelimagem = modelimagem;
	}

	public Evento getEvento() {
		if (evento == null) {
			evento = new Evento();
		}
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Imagem getImagem() {
		if (imagem == null) {
			imagem = new Imagem();
		}
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}

	public List<Evento> getEventos() {
		if (eventos == null) {
			eventos = model.listarEvento();
		}
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public List<Imagem> getImagens() {
		return modelimagem.listarImagemPorEvento(evento.getPub_cod());
	}

	public boolean isModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(boolean modoEdicao) {
		this.modoEdicao = modoEdicao;
	}

	public boolean isModoEdicaoImagem() {
		return modoEdicaoImagem;
	}

	public void setModoEdicaoImagem(boolean modoEdicaoImagem) {
		this.modoEdicaoImagem = modoEdicaoImagem;
	}

	public Integer getPub_cod() {
		return pub_cod;
	}

	public void setPub_cod(Integer pub_cod) {
		this.pub_cod = pub_cod;
	}

	public Date getPub_data() {
		return pub_data;
	}

	public void setPub_data(Date pub_data) {
		this.pub_data = pub_data;
	}

	public Date getPub_dataalteracao() {
		return pub_dataalteracao;
	}

	public void setPub_dataalteracao(Date pub_dataalteracao) {
		this.pub_dataalteracao = pub_dataalteracao;
	}

	public String getPub_titulo() {
		return pub_titulo;
	}

	public void setPub_titulo(String pub_titulo) {
		this.pub_titulo = pub_titulo;
	}

	public String getPub_sumario() {
		return pub_sumario;
	}

	public void setPub_sumario(String pub_sumario) {
		this.pub_sumario = pub_sumario;
	}

	public String getPub_conteudo() {
		return pub_conteudo;
	}

	public void setPub_conteudo(String pub_conteudo) {
		this.pub_conteudo = pub_conteudo;
	}

	public Integer getIma_cod() {
		return ima_cod;
	}

	public void setIma_cod(Integer ima_cod) {
		this.ima_cod = ima_cod;
	}

	public String getIma_descricao() {
		return ima_descricao;
	}

	public void setIma_descricao(String ima_descricao) {
		this.ima_descricao = ima_descricao;
	}

	public String getIma_normal() {
		return ima_normal;
	}

	public void setIma_normal(String ima_normal) {
		this.ima_normal = ima_normal;
	}

	public String getIma_mini() {
		return ima_mini;
	}

	public void setIma_mini(String ima_mini) {
		this.ima_mini = ima_mini;
	}

	public byte[] getBytesImagem() {
		return bytesImagem;
	}

	public void setBytesImagem(byte[] bytesImagem) {
		this.bytesImagem = bytesImagem;
	}
	
	/* ------------------------------------------------- */

	@Override
	public void incluir() {
		this.evento = new Evento();
		this.modoEdicao = true;
		this.modoEdicaoImagem = false;
	}

	@Override
	public void salvar() {
		if (evento.getPub_cod() == null || evento.getPub_cod().intValue() == 0) {
			evento.setPub_data(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			evento = model.incluirEvento(evento);
			evento = new Evento();
			addInfoMensagem("Evento criado com sucesso.");
			retornar();
		} else {
			evento.setPub_dataalteracao(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			model.alterarEvento(evento);
			addInfoMensagem("Evento alterado com sucesso.");
			retornar();
		}
	}

	@Override
	public void atualizar() {
		this.modoEdicao = true;
	}

	@Override
	public void excluir() {
		model.excluirEvento(evento);
		retornar();
	}

	@Override
	public void filtrar(AjaxBehaviorEvent event) {
		if (pub_titulo != null && !pub_titulo.isEmpty()) {
			eventos = model.listarEvento(pub_titulo);
		} else {
			eventos = model.listarEvento();
		}
	}

	@Override
	public String retornar() {
		this.modoEdicao = false;
		eventos = model.listarEvento();
		this.modoEdicaoImagem = false;
		bytesImagem = null;
		return "evento";
	}
	
	/* ------------------------------------------------- */

	public void incluirImagem() {
		this.imagem = new Imagem();
		this.modoEdicaoImagem = true;
		this.modoEdicao = false;
		bytesImagem = null;
	}
	
	public void salvarImagem() {
		if (bytesImagem == null)
			addInfoMensagem("É preciso carregar uma imagem antes de salvar!");
		if (imagem.getIma_cod() == null || imagem.getIma_cod().intValue() == 0) {
			imagem.setEvento(evento);
			if (salvaArquivo()) {
				imagem = modelimagem.incluirImagem(imagem);
				imagem = new Imagem();
				bytesImagem = null;
				addInfoMensagem("Imagem incluída com sucesso.");
			} else {
				addErroMensagem("Cadastro da imagem não realizado!");
			}

		} else {

			File arquivoAnterior = new File(PATH + "\\" + subPasta + "\\" + imagem.getIma_normal());
			if (Criptografia.criptografarMD5(nomeArquivo).concat("-" + subPasta + ".jpg") != imagem.getIma_normal()) {
				// exclui o arquivo existente
				if (arquivoAnterior.exists())
					arquivoAnterior.delete();

				if (!salvaArquivo())
					addErroMensagem("Alteração da imagem não realizada!");
			}
			modelimagem.alterarImagem(imagem);
			imagem = new Imagem();
			bytesImagem = null;
			addInfoMensagem("Cadastro da imagem alterado com sucesso.");
		}
	}
	
	public void atualizarImagem() {
		this.modoEdicaoImagem = true;
		nomeArquivo = imagem.getIma_normal();
		subPasta = imagem.getEvento().getPub_cod().toString();
		bytesImagem = FileParaBytes.getFileBytes(new File(PATH + "\\" + imagem.getEvento().getPub_cod() + "\\" + imagem.getIma_normal()));
	}

	public void excluirImagem() {
		File arquivo = new File(PATH + "\\" + imagem.getEvento().getPub_cod()+ "\\" + imagem.getIma_normal());
		File pasta = new File(PATH + "\\" + imagem.getEvento().getPub_cod());
		if (arquivo.exists())
			arquivo.delete();
		if (pasta.exists())
			if (pasta.listFiles().length == 0)
				pasta.delete();
		modelimagem.excluirImagem(imagem);
		addInfoMensagem("Imagem excluída com sucesso.");
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		subPasta = evento.getPub_cod().toString();
		arquivo = new File(PATH + "\\" + subPasta);
		nomeArquivo = event.getFile().getFileName();
		bytesImagem = Redimensiona.novaLargura(event.getFile().getContents(),640);

		if (new File(arquivo.getPath()+ "\\" + Criptografia.criptografarMD5(nomeArquivo).concat("-" + subPasta + ".jpg")).exists())
			addAvisoMensagem("Já existe uma imagem com mesmo nome, se continuar, a imagem atual será substituída.");
		addAvisoMensagem("O arquivo " + nomeArquivo + " foi carregado. \nUse o botão salvar para completar a operação!");
	}

	boolean salvaArquivo() {
		boolean retorno = false;
		// se a pasta não existir cria
		if (!arquivo.exists())
			arquivo.mkdirs();

		arquivo = new File(arquivo.getPath() + "\\" + Criptografia.criptografarMD5(nomeArquivo).concat("-" + subPasta + ".jpg"));

		// se o arquivo ja existe exclui
		if (arquivo.exists()) {
			arquivo.delete();
			addAvisoMensagem("O arquivo " + nomeArquivo + " existente foi excluído.");
		}

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(arquivo);
			byte[] buffer = new byte[bytesImagem.length];
			int bulk;
			InputStream inputStream = new ByteArrayInputStream(bytesImagem);
			while (true) {
				bulk = inputStream.read(buffer);
				if (bulk < 0) {
					break;
				}
				fileOutputStream.write(buffer, 0, bulk);
				fileOutputStream.flush();

				// faz outras coisas aqui
				imagem.setIma_normal(arquivo.getName());
			}

			fileOutputStream.close();
			inputStream.close();
			addInfoMensagem("O arquivo " + nomeArquivo + " foi enviado.");
			retorno = true;
		} catch (IOException e) {
			e.printStackTrace();
			addErroMensagem("O arquivo " + nomeArquivo + " não foi enviado, tente novamente!");
			retorno = false;
		}
		return retorno;
	}

	public List<String> fotos() {
        return fotos;
    }
	
    @PostConstruct
	public void init() {
		fotos = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {
			fotos.add(extContext.getRealPath("//upload//") + i + ".jpg");
			System.out.println(extContext.getRealPath("//upload//") + i + ".jpg");
		}
	}
    
}
