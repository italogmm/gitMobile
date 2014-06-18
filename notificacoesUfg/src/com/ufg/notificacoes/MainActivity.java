package com.ufg.notificacoes;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.ufg.notificacoes.activity.adapter.NotificacaoListAdapter;
import com.ufg.notificacoes.bean.Notificacao;
import com.ufg.notificacoes.dao.NotificacaoDao;
import com.ufg.notificacoes.util.GoogleCloudMessaging;

public class MainActivity extends ListActivity {

	private List<Notificacao> notificacoes;
	
	private static boolean primeiraExecucao = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		GoogleCloudMessaging.ativa(getApplicationContext());
		
		if (GoogleCloudMessaging.isAtivo(getApplicationContext())) {
			Toast.makeText(getApplicationContext(), "Recebimento de piadas ativado!", Toast.LENGTH_LONG).show();
			
			carregarLista();
		}else{
			Toast.makeText(getApplicationContext(), "Recebimento de piadas não ativado!", Toast.LENGTH_LONG).show();
		}
	}
	
	private void carregarLista(){
		NotificacaoDao notificacaoDao = new NotificacaoDao(this);
		notificacoes = notificacaoDao.listar();
		notificacaoDao.close();
		
		String[] ids = new String[1] ;
		String[] remetentes = new String[1] ;
		String[] textosNotificacoes = new String[1] ;
		String[] datas = new String[1];
		
		for(int x = 0; x < notificacoes.size() && x < 15; x++){
			remetentes[x] = notificacoes.get(x).getRemetente();
			textosNotificacoes[x] = notificacoes.get(x).getTexto();
			datas[x] = notificacoes.get(x).getDataFormatada();
			ids[x] = notificacoes.get(x).getId().toString();
		}
		
		if(notificacoes.size() > 0){
			setListAdapter(new NotificacaoListAdapter(this, remetentes, textosNotificacoes, datas, ids));
			final ListView listView = getListView();
			listView.setTextFilterEnabled(true);
			listView.setOnItemClickListener(new OnItemClickListener(){
	        @Override
	        public void onItemClick(AdapterView<?> Parent, View view, int position,
	                long id) {
	        	
	        	Intent intent = new Intent(MainActivity.this, ConsultaNotificacao.class);
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
		
		if(!primeiraExecucao){
			primeiraExecucao = false;
			finish();
		}
	}

}
