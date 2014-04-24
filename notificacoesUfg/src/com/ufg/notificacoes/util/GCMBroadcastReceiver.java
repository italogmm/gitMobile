package com.ufg.notificacoes.util;

import android.content.Context;

public class GCMBroadcastReceiver extends
		com.google.android.gcm.GCMBroadcastReceiver {

	@Override
	protected String getGCMIntentServiceClassName(Context context) {

		return "com.ufg.notificacoes.util.GCMIntentService";
	}
}
