package com.ufg.notificacoes.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.ufg.notificacoes.MainActivity;
import com.ufg.notificacoes.R;
import com.ufg.notificacoes.bean.Notificacao;
import com.ufg.notificacoes.dao.NotificacaoDao;

public class GCMIntentService extends GCMBaseIntentService {
	
	public GCMIntentService() {
        super(Constantes.SENDER_ID);
    }
	
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
			NotificacaoDao notDao = new NotificacaoDao(context);
			notDao.cadastrar(new Notificacao("Remetente teste", mensagem));
			
			mostraNotificacao("Novo aviso da UFG!", mensagem, context);
		}
	}
	
	public void mostraNotificacao(String titulo, String mensagem, Context context) {

		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle(titulo)
		        .setContentText(mensagem);
		
		Intent resultIntent = new Intent(context, MainActivity.class);
		resultIntent.putExtra("mensagem_recebida", mensagem);
		
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		
		mBuilder.setContentIntent(resultPendingIntent);
		
		Notification notification = mBuilder.build();
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults = Notification.DEFAULT_ALL;
		
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}

	@Override
	protected void onUnregistered(Context context, String regId) {
		Log.i(Constantes.TAG, "GoogleCloudMessaging Desativado.");
	}
}