package br.net.rwd.website.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class CriptografiaMD5 {
	public static String encriptar(String senha) {
		PasswordEncoder encoder = new Md5PasswordEncoder();
		senha = encoder.encodePassword(senha, null);
		return senha;
	}
}
