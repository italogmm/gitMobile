package com.ufg.notificacoes.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ufg.notificacoes.R;
import com.ufg.notificacoes.R.id;
import com.ufg.notificacoes.R.layout;
import com.ufg.notificacoes.R.menu;
import com.ufg.notificacoes.bean.Notificacao;
import com.ufg.notificacoes.dao.NotificacaoDao;

public class ConsultaNotificacao extends Activity {
	
	TextView txtViewRemetente;
	TextView textViewCorpo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_notificacao);
		
		Bundle receiveBundle = this.getIntent().getExtras();
		final long idNotificacao = receiveBundle.getLong("idNotificacao");
		
		NotificacaoDao notDAO = new NotificacaoDao(this);
		Notificacao consultada = notDAO.consultar(idNotificacao);
		
		txtViewRemetente = (TextView)findViewById(R.id.remetente);
		textViewCorpo = (TextView)findViewById(R.id.corpo);
		
		txtViewRemetente.setText(consultada.getRemetente());
		textViewCorpo.setText(consultada.getTexto());
		
		consultada.setLida(true);
		notDAO.alterar(consultada);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consulta_notificacao, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
