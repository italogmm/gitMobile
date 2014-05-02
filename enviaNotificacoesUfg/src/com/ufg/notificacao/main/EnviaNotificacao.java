package com.ufg.notificacao.main;

import java.io.IOException;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class EnviaNotificacao {

	private static final String ID_DISPOSITIVO_GCM = "APA91bHZwhlDcfimB_fHVbh0IVluJWQ4-F6c-pwKAR7UH_XMHaaJXhOuuImPV3QwUoS30c1wU8jnSrDcQ2kYlFuk9sz55YKCcv24jU21ShK8Pn_wy3SWiH-w7guQ8FpTlukuiFSKycIsMLkr7XPoHlflo-YIgjID8Q";

	private static final String API_KEY = "AIzaSyDAHjnlqy-2E_X8uAKt39sM8u29vdzK6eQ";

	public static void main(String[] args) {
		
		Sender sender = new Sender(API_KEY);
		Message message = new Message.Builder()
				.collapseKey("1")
				.timeToLive(3)
				.delayWhileIdle(true)
				.addData("mensagem", // identificador da mensagem
						"##MSGMETADATA:Edmundo Sergio##MSGMETADATA:Engenharia de Software##MSGMETADATA:Comunicado geral##MSGMETADATA: A universidade estar‡ fechada na pr—xima semana!")
				.build();

		Result result = null;

		try {
			result = sender.send(message, ID_DISPOSITIVO_GCM, 1);
			String canonicalRegId = result.getCanonicalRegistrationId();
			System.out.println("canonicalRegId: " + canonicalRegId);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Imprime o resultado do envio na sa’da padr‹o
		if (result != null)
			System.out.println(result.toString());
	}
}
