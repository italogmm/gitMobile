package com.ufg.notificacoes;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ufg.notificacoes.bean.Notificacao;
import com.ufg.notificacoes.dao.NotificacaoDao;
import com.ufg.notificacoes.util.GoogleCloudMessaging;

public class MainActivity extends ActionBarActivity {

	ListView lvListagem;
	
	private List<Notificacao> notificacoes;
	private ArrayAdapter<Notificacao> adapter;
	private int adapterLayout = android.R.layout.simple_list_item_1;
	
	private static boolean primeiraExecucao = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		GoogleCloudMessaging.ativa(getApplicationContext());
		
		if (GoogleCloudMessaging.isAtivo(getApplicationContext())) {
			Toast.makeText(getApplicationContext(), "Recebimento de piadas ativado!", Toast.LENGTH_LONG).show();
			
			carregarLista();
		}else{
			Toast.makeText(getApplicationContext(), "Recebimento de piadas n�o ativado!", Toast.LENGTH_LONG).show();
		}
	}
	
	private void carregarLista(){
		NotificacaoDao notificacaoDao = new NotificacaoDao(this);
		notificacoes = notificacaoDao.listar();
		notificacaoDao.close();
		
		lvListagem = (ListView) findViewById(R.id.lvListagem);
		this.adapter = new ArrayAdapter<Notificacao>(this, adapterLayout, notificacoes);
		this.lvListagem.setAdapter(adapter);
		lvListagem.setOnItemClickListener(new OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> Parent, View view, int position,
                long id) {
        	Toast.makeText(getApplicationContext(), id + "", Toast.LENGTH_LONG).show();
        
        }});
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		setContentView(R.layout.activity_main);
		
		lvListagem = (ListView) findViewById(R.id.lvListagem);
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