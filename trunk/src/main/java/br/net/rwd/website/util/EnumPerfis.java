package br.net.rwd.website.util;

public enum EnumPerfis {
	ROLE_ADMINISTRADOR("Administrador"), ROLE_USUARIO("Usuário");

	private String role;

	EnumPerfis(String role) {
		this.role = role;
	}

	public String getPerfilUsuario() {
		return role;
	}

	@Override
	public String toString() {
		return role;
	}

}
