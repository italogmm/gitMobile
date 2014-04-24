package com.ufg.notificacoes.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	
	@Override
	protected void onRegistered(Context context, String regId) {
		Log.i(Constantes.TAG, "GoogleCloudMessaging ativado.");
		/*
		 * Mostramos no console o ID de registro no GoogleCloudMessaging para usá-lo 
		 * posteriormente, no aplicativo cliente, para o envio de mensagens
		 * da Nuvem para o dispositivo Android.
		 */
		String mensagem = "ID de registro no GoogleCloudMessaging: " + regId;
		Log.i(Constantes.TAG, mensagem);
	}

	@Override
	protected void onError(Context context, String errorMessage) {
		Log.e(Constantes.TAG, "Erro: " + errorMessage);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		
		String mensagem = intent.getExtras().getString("mensagem");
		Log.i(Constantes.TAG, "Mensagem recebida: " + mensagem);

		if (mensagem != null && !"".equals(mensagem)){
			/**
			 * @TODO Implementar o que fazer ao receber mensagem de notifica��o.
			 */
		}
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.i(Constantes.TAG, "GoogleCloudMessaging Desativado.");
	}
}
