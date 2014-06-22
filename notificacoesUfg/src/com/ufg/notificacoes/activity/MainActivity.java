package com.ufg.notificacoes.activity;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ufg.notificacoes.R;
import com.ufg.notificacoes.activity.adapter.NotificacaoListAdapter;
import com.ufg.notificacoes.bean.Notificacao;
import com.ufg.notificacoes.dao.NotificacaoDao;
import com.ufg.notificacoes.util.GoogleCloudMessaging;

public class MainActivity extends ListActivity {

	private List<Notificacao> notificacoes;
	
	private static boolean primeiraExecucao = true;
	private static boolean gcmAtivo = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		if(!gcmAtivo || primeiraExecucao || !GoogleCloudMessaging.isAtivo(getApplicationContext())){
			GoogleCloudMessaging.ativa(getApplicationContext());
		}else{
			gcmAtivo = true;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		 switch (item.getItemId()) {
		 case R.id.action_config:
			 Intent intent = new Intent(MainActivity.this, ConfiguracoesActivity.class);
	         MainActivity.this.startActivity(intent);
		 default:
		      return super.onOptionsItemSelected(item);
		 }
	}
	
	@Override
	public void onBackPressed() {
		finishAffinity();
	}
	
	private void carregarLista(){
		NotificacaoDao notificacaoDao = new NotificacaoDao(this);
		notificacoes = notificacaoDao.listar();
		notificacaoDao.close();
		
		String[] ids = new String[notificacoes.size()] ;
		String[] remetentes = new String[notificacoes.size()] ;
		String[] textosNotificacoes = new String[notificacoes.size()] ;
		String[] datas = new String[notificacoes.size()];
		Boolean[] lida = new Boolean[notificacoes.size()];
		
		for(int x = 0; x < notificacoes.size() && x < 15; x++){
			remetentes[x] = notificacoes.get(x).getGrupoEnvio().getNome();
			textosNotificacoes[x] = notificacoes.get(x).getTexto();
			datas[x] = notificacoes.get(x).getDataFormatada();
			ids[x] = notificacoes.get(x).getId().toString();
			lida[x] = notificacoes.get(x).getLida();
		}
		
		if(notificacoes.size() > 0){
			setListAdapter(new NotificacaoListAdapter(this, remetentes, textosNotificacoes, datas, ids, lida));
			final ListView listView = getListView();
			listView.setTextFilterEnabled(true);
			listView.setOnItemClickListener(new OnItemClickListener(){
	        @Override
	        public void onItemClick(AdapterView<?> Parent, View view, int position,
	                long id) {
	        	
	        	Intent intent = new Intent(MainActivity.this, VisualizaNotificacaoActivity.class);
	        	Bundle sendBundle = new Bundle();
	            sendBundle.putLong("idNotificacao", view.getId());
	            intent.putExtras(sendBundle);
	            
	            MainActivity.this.startActivity(intent);
	        }});
		}
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		carregarLista();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
	}
}
