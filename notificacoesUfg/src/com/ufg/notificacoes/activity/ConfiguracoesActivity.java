package com.ufg.notificacoes.activity;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.ufg.notificacoes.activity.adapter.ConfiguracaoRecebimentoListAdapter;
import com.ufg.notificacoes.bean.GrupoEnvio;
import com.ufg.notificacoes.dao.GrupoEnvioDao;

public class ConfiguracoesActivity extends ListActivity {
	
	private static boolean primeiraExecucao = true;
	
	private List<GrupoEnvio> gruposEnvio;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		
		if(!primeiraExecucao){
			primeiraExecucao = false;
			finish();
		}
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		carregarGruposEnvio();
	}
	
	private void carregarGruposEnvio(){
		
		GrupoEnvioDao grupoEnvioDao = new GrupoEnvioDao(this);
		gruposEnvio = grupoEnvioDao.listar();
		grupoEnvioDao.close();
		
		String[] ids = new String[gruposEnvio.size()];
		String[] gruposNomes = new String[gruposEnvio.size()];
		Boolean[] recebimentoAtivado = new Boolean[gruposEnvio.size()];
		
		for(int x = 0; x < gruposEnvio.size() && x < 15; x++){
			ids[x] = gruposEnvio.get(x).getId().toString();
			gruposNomes[x] = gruposEnvio.get(x).getNome();
			recebimentoAtivado[x] = gruposEnvio.get(x).getRecebimentoAtivado();
		}
		
		if(gruposEnvio.size() > 0){
			setListAdapter(new ConfiguracaoRecebimentoListAdapter(this, gruposNomes, ids, recebimentoAtivado));
			final ListView listView = getListView();
			listView.setTextFilterEnabled(true);
		}
	}
}
