package com.ufg.notificacao.main;

import java.io.IOException;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class EnviaNotificacao {
	
	private static final String ID_DISPOSITIVO_GCM = "APA91bHUfzHZMKF7Dc146X_yifvRyQch9gB9-QvtkPZT3RyYQ_p6JF9DwVaOTkv9tn62kqwWnxvHHI-wOR_wydTTWYW366iaexKt4ajG58HkocE3IB5GE0PJ_NMwhl80_7pDtLPoYeCNsJUpuOhDCWjRqclY9aUMQQ";
	private static final String API_KEY = "AIzaSyDAHjnlqy-2E_X8uAKt39sM8u29vdzK6eQ";

	public static void main(String[] args) {
		
		String mensagemTeste = "#PUBLIC:1#GRUPO:5277#MSG:Hoje não havesdsdrá aula de novo!!!!!!! Será??1 www.google.com.br";
		System.out.println(mensagemTeste.substring(16, mensagemTeste.indexOf("#MSG:")));
		System.out.println(mensagemTeste.substring(mensagemTeste.indexOf("#MSG:") + 5, mensagemTeste.length()));
		System.out.println(mensagemTeste.substring(8, mensagemTeste.indexOf("#GRUPO:")));
		
		Sender sender = new Sender(API_KEY);
		Message message = new Message.Builder()
				.collapseKey("1")
				.timeToLive(3)
				.delayWhileIdle(true)
				.addData("mensagem", // identificador da mensagem
						mensagemTeste)
				.build();

		Result result = null;

		try {
			result = sender.send(message, ID_DISPOSITIVO_GCM, 1);
			String canonicalRegId = result.getCanonicalRegistrationId();
			System.out.println("canonicalRegId: " + canonicalRegId);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Imprime o resultado do envio na saída padrão
		if (result != null)
			System.out.println(result.toString());
	}
}
