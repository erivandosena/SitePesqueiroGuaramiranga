package br.net.rwd.website.util;

public enum EnumPosicaoMenu {
	T("Topo"), L("Lateral"), R("Rodapé");

	private String posicao;

	EnumPosicaoMenu(String posicao) {
		this.posicao = posicao;
	}

	public String getPosicaoPagina() {
		return posicao;
	}

	@Override
	public String toString() {
		return posicao;
	}

}
