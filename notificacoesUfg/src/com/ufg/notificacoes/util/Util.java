package com.ufg.notificacoes.util;

import java.security.MessageDigest;

import com.ufg.notificacoes.bean.Usuario;

public class Util {
	
	public static Usuario usuarioLogado;
	
	public static String criptografaSenha(String senha){
		
		String senhaCriptografada = "";
		try{
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
			
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
			  hexString.append(String.format("%02X", 0xFF & b));
			}
			senhaCriptografada = hexString.toString();
			
		}catch(Exception e){
			System.out.println(e);
		}
		
		return senhaCriptografada;
	}
}
