package br.net.rwd.website.util;

public class NormalizaString {

	public static String normalizar(String str) {
		// str = Normalizer.normalize(str, Normalizer.Form.NFD);
		// str = str.replaceAll("[^\\p{ASCII}]", "");

		// Troca os caracteres acentuados por nao acentuados
		String[][] caracteresAcento = { { "Á", "A" }, { "á", "a" },
				{ "É", "E" }, { "é", "e" }, { "Í", "I" }, { "í", "i" },
				{ "Ó", "O" }, { "ó", "o" }, { "Ú", "U" }, { "ú", "u" },
				{ "À", "A" }, { "à", "a" }, { "È", "E" }, { "è", "e" },
				{ "Ì", "I" }, { "ì", "i" }, { "Ò", "O" }, { "ò", "o" },
				{ "Ù", "U" }, { "ù", "u" }, { "Â", "A" }, { "â", "a" },
				{ "Ê", "E" }, { "ê", "e" }, { "Î", "I" }, { "î", "i" },
				{ "Ô", "O" }, { "ô", "o" }, { "Û", "U" }, { "û", "u" },
				{ "Ä", "A" }, { "ä", "a" }, { "Ë", "E" }, { "ë", "e" },
				{ "Ï", "I" }, { "ï", "i" }, { "Ö", "O" }, { "ö", "o" },
				{ "Ü", "U" }, { "ü", "u" }, { "Ã", "A" }, { "ã", "a" },
				{ "Õ", "O" }, { "õ", "o" }, { "Ç", "C" }, { "ç", "c" },
				{ "Ý", "Y" }, { "ý", "y" }, { "ÿ", "y" }, { "ÿ", "y" },
				{ "Ñ", "N" }, { "ñ", "n" }, };

		for (int i = 0; i < caracteresAcento.length; i++) {
			str = str
					.replaceAll(caracteresAcento[i][0], caracteresAcento[i][1]);
		}

		// Troca os caracteres especiais da string por ""
		String[] caracteresEspeciais = { "!", "@", "#", "\\$", "%", "¨", "&",
				"\\*", "\\(", "\\)", "_", "—", "\\+", "`", "\\{", "\\}", "\\^",
				"\\|", "\\?", "<", ">", ":", "'", "\\\\", "\\//", "\\=", "´",
				"\\[", "\\]", "\\~", "\\.", "\\;", "°", "ª", "¹", "²", "³",
				"\\£", "¢", "\\¬", "\\§", "®", "™", "\\€", "©", "\\£", "," };
		for (int i = 0; i < caracteresEspeciais.length; i++) {
			str = str.replaceAll(caracteresEspeciais[i], "");
		}

		// Troca os espaços no inicio e no fim por ""
		str = str.replaceAll("^\\s+", "");
		str = str.replaceAll("\\s+$", "");
		
		// Troca os espaços duplicados, tabulações e etc por "-"
		str = str.replaceAll("\\s+", "-");
		
		// Troca tudo por minusculo
		return str.toLowerCase();
	}

}
