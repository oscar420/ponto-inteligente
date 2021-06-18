package com.kazale.pontointeligente.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordUtils {
	 private static final Logger log = LoggerFactory.getLogger(PasswordUtils.class);
	 
	 public PasswordUtils() {
	 }
	 
	 /**
	  * Gera um hash utilizando o BCrypt.
	  * 
	  * @param senha
	  * @return String
	  */
	 public static String gerarBCrypt(String senha) {
		 if (senha == null) {
			 return senha;
		 }
		 /*
		  * Si a senha e nulla retorna a senha (null).
		  * caso contrario imprime a informacao que esta gerando um hash
		  * Cria uma instancia do classe BCryptPasswordEncoder e logo retorna
		  * um hash da senha que o usuario digitar, utilizando a instancia criada
		  */
		 log.info("Generando hash com o BCrypt");
		 BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		 return bCryptEncoder.encode(senha);
	 }
}
