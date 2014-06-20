package com.ufg.notificacoes.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ufg.notificacoes.R;
import com.ufg.notificacoes.bean.Notificacao;
import com.ufg.notificacoes.dao.NotificacaoDao;

public class VisualizaNotificacaoActivity extends Activity {
	
	TextView txtViewRemetente;
	TextView textViewCorpo;
	Button marcarComoNaoLida;
	Long idNotificacao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_notificacao);
		
		Bundle receiveBundle = this.getIntent().getExtras();
		idNotificacao = receiveBundle.getLong("idNotificacao");
		
		final NotificacaoDao notDAO = new NotificacaoDao(this);
		final Notificacao consultada = notDAO.consultar(idNotificacao);
		
		txtViewRemetente = (TextView)findViewById(R.id.remetente);
		textViewCorpo = (TextView)findViewById(R.id.corpo);
		
		txtViewRemetente.setText(consultada.getGrupoEnvio().getNome());
		textViewCorpo.setText(consultada.getTexto());
		
		consultada.setLida(true);
		notDAO.alterar(consultada);
		
		findViewById(R.id.marcarComoNaoLidaBtn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						consultada.setLida(false);
						notDAO.alterar(consultada);
						Intent intent = new Intent(VisualizaNotificacaoActivity.this, MainActivity.class);
						startActivity(intent);
					}
				});
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void onPause(){
		super.onPause();
		finish();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consulta_notificacao, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		 case android.R.id.home:
			 Intent intent = new Intent(VisualizaNotificacaoActivity.this, MainActivity.class);
			 VisualizaNotificacaoActivity.this.startActivity(intent);
		 default:
		      return super.onOptionsItemSelected(item);
		 }
	}
}
