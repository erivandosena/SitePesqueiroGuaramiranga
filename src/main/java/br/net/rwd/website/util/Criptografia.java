package br.net.rwd.website.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class Criptografia {

	public static String criptografarMD5(String senha) {
		PasswordEncoder encoder = new Md5PasswordEncoder();
		senha = encoder.encodePassword(senha, null);
		return senha;
	}

	public static String criptografarSHA(String senha) {
		PasswordEncoder encoder = new ShaPasswordEncoder();
		senha = encoder.encodePassword(senha, null);
		return senha;
	}

}
