package br.net.rwd.website.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class Redimensiona {

	/**
	 * Metodo para redimensionar imagens, criar thubmnails (novoTamanhos)
	 * 
	 * @param bytesImagem
	 * @param larguraFixa
	 * @param extensao
	 * @return byte[] da imagem
	 */
	public static byte[] novaLargura(byte[] bytesImagem, int larguraFixa, String extensao) {
		BufferedImage novoTamanho = null;
		byte[] bytesOut = null;

		try {
			BufferedImage bi = ImageIO.read(new ByteArrayInputStream(bytesImagem));
			
			int larguraImg   = bi.getWidth();
			int alturaImg   = bi.getHeight();
			int novaAltura = (larguraFixa * alturaImg) / larguraImg;

			novoTamanho = new BufferedImage(((int) larguraFixa) < 1 ? 1 : (int) larguraFixa, ((int) novaAltura) < 1 ? 1 : (int) novaAltura, bi.getType() == BufferedImage.TYPE_CUSTOM ? BufferedImage.TYPE_INT_ARGB : bi.getType());
			
			Graphics2D g2d = novoTamanho.createGraphics();
			
			RenderingHints rh = g2d.getRenderingHints (); 
			rh.put(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			rh.put(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			rh.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
			rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			rh.put(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 
			
			g2d.setRenderingHints(rh); 
			g2d.drawImage(bi, 0, 0, larguraFixa, novaAltura, null);
			g2d.dispose();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			//compressao/qualidade se for imagem jpg
			ImageOutputStream ios = null;
			ImageWriter writer = null;
			if (extensao.equalsIgnoreCase("jpg") || extensao.equalsIgnoreCase("jpeg")) {
				Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(extensao);
				if (!writers.hasNext())
					throw new IllegalStateException("No writers found");
				writer = (ImageWriter) writers.next();
				ios = ImageIO.createImageOutputStream(baos);
				writer.setOutput(ios);
				ImageWriteParam param = writer.getDefaultWriteParam();
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality(0.86f);
				writer.write(null, new IIOImage(bi, null, null), param);	
			}
			
			ImageIO.write(novoTamanho, extensao, baos);
			bytesOut = baos.toByteArray();
			
			if (ios != null) {
				ios.close();
				writer.dispose();
			}
		
		} catch (IOException e) {
			e.printStackTrace();
			}

		return bytesOut;
	}
		
}
