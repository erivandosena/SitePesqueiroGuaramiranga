package br.net.rwd.website.controle;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.FileUploadEvent;

import br.net.rwd.website.entidade.Foto;
import br.net.rwd.website.entidade.Galeria;
import br.net.rwd.website.servico.FotoServico;
import br.net.rwd.website.servico.GaleriaServico;
import br.net.rwd.website.util.Criptografia;
import br.net.rwd.website.util.FileParaBytes;
import br.net.rwd.website.util.Redimensiona;

@ManagedBean(name = "galeriaBean")
@ViewScoped
public class GaleriaBean extends UtilBean implements CrudBeans<Object> {

	// galeria
	@ManagedProperty("#{galeriaServico}")
	private GaleriaServico modelgaleria;

	private Integer gal_cod;
	private Date gal_data;
	private String gal_titulo;
	private String gal_descricao;
	private String gal_foto;

	private Galeria galeria;
	private List<Galeria> galerias;
	private boolean modoEdicaoGaleria;
	//

	// foto
	@ManagedProperty("#{fotoServico}")
	private FotoServico modelfoto;

	private Integer fot_cod;
	private String fot_descricao;
	private String fot_foto;

	private Foto foto;
	private boolean modoEdicaoFoto;
	//

	// variaveis e constantes auxiliares
	private HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

	private static ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
	private static final String PATH = extContext.getRealPath("/upload/galeria/");
	private byte[] bytesFoto;
	String nomeImagem = null;
	String nomeArquivo = null;
	String subPasta = null;
	File arquivo = null;
	//

	public GaleriaServico getModelgaleria() {
		return modelgaleria;
	}

	public void setModelgaleria(GaleriaServico modelgaleria) {
		this.modelgaleria = modelgaleria;
	}

	public Integer getGal_cod() {
		return gal_cod;
	}

	public void setGal_cod(Integer gal_cod) {
		this.gal_cod = gal_cod;
	}

	public Date getGal_data() {
		return gal_data;
	}

	public void setGal_data(Date gal_data) {
		this.gal_data = gal_data;
	}

	public String getGal_titulo() {
		return gal_titulo;
	}

	public void setGal_titulo(String gal_titulo) {
		this.gal_titulo = gal_titulo;
	}

	public String getGal_descricao() {
		return gal_descricao;
	}

	public void setGal_descricao(String gal_descricao) {
		this.gal_descricao = gal_descricao;
	}

	public String getGal_foto() {
		return gal_foto;
	}

	public void setGal_foto(String gal_foto) {
		this.gal_foto = gal_foto;
	}

	public Galeria getGaleria() {
		if (galeria == null) {
			galeria = new Galeria();
		}
		return galeria;
	}

	public void setGaleria(Galeria galeria) {
		this.galeria = galeria;
	}

	public List<Galeria> getGalerias() {
		if(galerias == null) {
			galerias = modelgaleria.listarGalerias();
		}
		return galerias;
	}

	public boolean isModoEdicaoGaleria() {
		return modoEdicaoGaleria;
	}

	public void setModoEdicaoGaleria(boolean modoEdicaoGaleria) {
		this.modoEdicaoGaleria = modoEdicaoGaleria;
	}
	
	/* ------------------------------------------------- */

	public FotoServico getModelfoto() {
		return modelfoto;
	}

	public void setModelfoto(FotoServico modelfoto) {
		this.modelfoto = modelfoto;
	}

	public Integer getFot_cod() {
		return fot_cod;
	}

	public void setFot_cod(Integer fot_cod) {
		this.fot_cod = fot_cod;
	}

	public String getFot_descricao() {
		return fot_descricao;
	}

	public void setFot_descricao(String fot_descricao) {
		this.fot_descricao = fot_descricao;
	}

	public String getFot_foto() {
		return fot_foto;
	}

	public void setFot_foto(String fot_foto) {
		this.fot_foto = fot_foto;
	}

	public Foto getFoto() {
		if (foto == null) {
			foto = new Foto();
		}
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public boolean isModoEdicaoFoto() {
		return modoEdicaoFoto;
	}

	public void setModoEdicaoFoto(boolean modoEdicaoFoto) {
		this.modoEdicaoFoto = modoEdicaoFoto;
	}

	public byte[] getBytesFoto() {
		return bytesFoto;
	}

	public void setBytesFoto(byte[] bytesFoto) {
		this.bytesFoto = bytesFoto;
	}
	
	/* ------------------------------------------------- */

	public List<Foto> getFotos() {
		return modelfoto.listarFotosPorGaleria(galeria.getGal_cod());
	}
	
	/* ------------------------------------------------- */

	@Override
	public void incluir() {
		this.galeria = new Galeria();
		this.modoEdicaoGaleria = true;
		this.modoEdicaoFoto = false;
	}

	@Override
	public void salvar() {
		if (galeria.getGal_cod() == null || galeria.getGal_cod().intValue() == 0) {
			galeria.setGal_data(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
			galeria = modelgaleria.incluirGaleria(galeria);
			galeria = new Galeria();
			addInfoMensagem("Galeria criada com sucesso.");
			retornar();
		} else {
			modelgaleria.alterarGaleria(galeria);
			addInfoMensagem("Galeria alterada com sucesso.");
			retornar();
		}
	}

	@Override
	public void atualizar() {
		this.modoEdicaoGaleria = true;
	}

	@Override
	public void excluir() {
		String local = PATH  + File.separator +  galeria.getGal_cod();
		modelgaleria.excluirGaleria(galeria);
		//Exclui a pasta e os arquivos do disco
		File pasta = new File(local);
		File arquivo = null;
		if (pasta.isDirectory()) {  
            String[] children = pasta.list();  
            for (int i=0; i<children.length; i++) {  
            	arquivo = new File(pasta+ File.separator +children[i]);
            	arquivo.delete(); 
            }  
        }
		pasta.delete();
		addInfoMensagem("Galeria excluída com sucesso.");
		retornar();
	}

	@Override
	public void filtrar(AjaxBehaviorEvent event) {
		if (gal_titulo != null && !gal_titulo.isEmpty()) {
			galerias = modelgaleria.listarGalerias(gal_titulo);
		} else {
			galerias = modelgaleria.listarGalerias();
		}
	}

	@Override
	public String retornar() {
		this.modoEdicaoGaleria = false;
		galerias = modelgaleria.listarGalerias();
		this.modoEdicaoFoto = false;
		bytesFoto = null;
		return "galeria";
	}

	/* ------------------------------------------------- */
	
	public void incluirFoto() {
		this.foto = new Foto();
		this.modoEdicaoFoto = true;
		this.modoEdicaoGaleria = false;
		bytesFoto = null;
	}
	
	public void salvarFoto() {
		if (bytesFoto == null)
			addInfoMensagem("É preciso carregar uma foto antes de salvar!");
		if (foto.getFot_cod() == null || foto.getFot_cod().intValue() == 0) {
			foto.setGaleria(galeria);
			if (salvaArquivo()) {
				foto = modelfoto.incluirFoto(foto);
				modelgaleria.alterarGaleria(galeria);
				foto = new Foto();
				bytesFoto = null;
				addInfoMensagem("Foto incluída com sucesso.");
			} else {
				addErroMensagem("Cadastro da foto não realizado!");
			}

		} else {

			File arquivoAnterior = new File(PATH + File.separator + subPasta + File.separator + foto.getFot_foto());
			if (Criptografia.criptografarMD5(nomeArquivo).concat("-" + subPasta + ".jpg") != foto.getFot_foto()) {
				// exclui o arquivo existente
				if (arquivoAnterior.exists())
					arquivoAnterior.delete();

				if (!salvaArquivo())
					addErroMensagem("Alteração da imagem não realizada!");
			}
			modelfoto.alterarFoto(foto);
			modelgaleria.alterarGaleria(galeria);
			foto = new Foto();
			bytesFoto = null;
			addInfoMensagem("Cadastro da foto alterado com sucesso.");
		}
	}
	
	public void atualizarFoto() {
		this.modoEdicaoFoto = true;
		nomeImagem = foto.getFot_foto();
		subPasta = foto.getGaleria().getGal_cod().toString();
		arquivo = new File(PATH + File.separator + subPasta + File.separator + nomeImagem);
		if(arquivo.exists()) 
		bytesFoto = FileParaBytes.getFileBytes(arquivo);
		else
			addErroMensagem("O arquivo da foto selecionada não foi encontrado! Carregue uma nova foto.");
	}

	public void excluirFoto() {
		File arquivo = new File(PATH + File.separator + foto.getGaleria().getGal_cod()+ File.separator + foto.getFot_foto());
		File pasta = new File(PATH + File.separator + foto.getGaleria().getGal_cod());
		if (arquivo.exists())
			arquivo.delete();
		if (pasta.exists())
			if (pasta.listFiles().length == 0)
				pasta.delete();
		modelfoto.excluirFoto(foto);
		addInfoMensagem("Foto excluída com sucesso.");
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		subPasta = galeria.getGal_cod().toString();
		nomeArquivo = event.getFile().getFileName();
		nomeImagem = Criptografia.criptografarMD5(nomeArquivo).concat("-" + subPasta + ".jpg");
		arquivo = new File(PATH + File.separator + subPasta + File.separator + nomeImagem);
		bytesFoto = Redimensiona.novaLargura(event.getFile().getContents(),640);
		
		if (arquivo.exists())
			addAvisoMensagem("Já existe uma foto com mesmo nome, se continuar, a foto atual será substituída.");
		addAvisoMensagem("O arquivo " + nomeArquivo + " foi carregado. \nUse o botão salvar para completar a operação!");
	}

	boolean salvaArquivo() {
		boolean retorno = false;
		// se a pasta não existir cria
		File pasta = new File(PATH + File.separator + subPasta);
		if (!pasta.exists())
			pasta.mkdirs();

		// se o arquivo ja existe exclui
		if (arquivo.exists()) {
			arquivo.delete();
			addAvisoMensagem("O arquivo da foto existente foi excluído.");
		}

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(arquivo);
			byte[] buffer = new byte[bytesFoto.length];
			int bulk;
			InputStream inputStream = new ByteArrayInputStream(bytesFoto);
			while (true) {
				bulk = inputStream.read(buffer);
				if (bulk < 0) {
					break;
				}
				fileOutputStream.write(buffer, 0, bulk);
				fileOutputStream.flush();

				// faz outras coisas aqui
				foto.setFot_foto(nomeImagem);
				galeria.setGal_foto(nomeImagem);
			}

			fileOutputStream.close();
			inputStream.close();
			addInfoMensagem("O arquivo arquivo da foto foi enviado.");
			retorno = true;
		} catch (IOException e) {
			e.printStackTrace();
			addErroMensagem("O arquivo arquivo da foto não foi enviado, tente novamente!");
			retorno = false;
		}
		return retorno;
	}
	
	public Galeria getConteudoGaleria() throws IOException {
		if (gal_cod != null)
			if (gal_cod == 0)
				return null;
			else
				return modelgaleria.selecionarGaleria(gal_cod);
		else
			return null;
	}
    
	public List<Foto> getFotosGaleria() {
		if (gal_cod != null) {
		List<Foto> lista = modelfoto.listarFotosPorGaleria(gal_cod);
		if (lista.isEmpty())
			return null;
		else
			return lista;
		}
		return null;
	}

	public Foto getFotoGaleria() {
		if (fot_cod != null)
			return modelfoto.selecionarFoto(fot_cod);
		else
			return null;
	}
	
	public List<Galeria> getGaleriasNovas(int limit, int offset) {
		return modelgaleria.listarGaleriasNovas(limit, offset);
	}

	public String getUrlGaleria() {
		String cod = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cod");
		String tit = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tit");
		return request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath())+ "/galeria/"+cod + File.separator + tit +File.separator;
	}
}
