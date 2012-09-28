package br.net.rwd.website.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Redimensiona {

	// Metodo para redimensionar imagens, criar thubmnails (novoTamanhos)
	public static byte[] novaLargura(byte[] bytesImagem, int larguraFixa) {
		
		BufferedImage novoTamanho = null;
		byte[] bytesOut = null;
		String extensao = "png";
		
		try {
			BufferedImage bi = ImageIO.read(new ByteArrayInputStream(bytesImagem));
			
			int larguraImg   = bi.getWidth();
			int alturaImg   = bi.getHeight();
			int novaAltura = (larguraFixa * alturaImg) / larguraImg;

			novoTamanho = new BufferedImage(((int) larguraFixa) < 1 ? 1 : (int) larguraFixa, ((int) novaAltura) < 1 ? 1 : (int) novaAltura, bi.getType() == BufferedImage.TYPE_CUSTOM ? BufferedImage.TYPE_INT_ARGB : bi.getType());
			
			Graphics2D g2d = novoTamanho.createGraphics();
			
			RenderingHints rh = g2d.getRenderingHints (); 
			
			rh.put(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			rh.put(RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			rh.put(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			
			g2d.setRenderingHints(rh); 
			g2d.drawImage(bi, 0, 0, larguraFixa, novaAltura, null);
			g2d.dispose();
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			ImageIO.write(novoTamanho, extensao, baos);
			bytesOut = baos.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytesOut;
	}
		
}
