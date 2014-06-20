package com.ufg.notificacoes.activity;

import java.util.Date;
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
import com.ufg.notificacoes.bean.GrupoEnvio;
import com.ufg.notificacoes.bean.Notificacao;
import com.ufg.notificacoes.bean.Usuario;
import com.ufg.notificacoes.dao.GrupoEnvioDao;
import com.ufg.notificacoes.dao.NotificacaoDao;
import com.ufg.notificacoes.dao.UsuarioDao;
import com.ufg.notificacoes.util.GoogleCloudMessaging;
import com.ufg.notificacoes.util.Util;

public class MainActivity extends ListActivity {

	private List<Notificacao> notificacoes;
	
	private static boolean primeiraExecucao = true;
	private static boolean gcmAtivo = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			incluirDadosParaTeste();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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

	public void incluirDadosParaTeste() throws Exception {
		UsuarioDao usuarioDao = new UsuarioDao(this);
		NotificacaoDao notificacaoDao = new NotificacaoDao(this);
		GrupoEnvioDao grupoEnvioDao = new GrupoEnvioDao(this);
		
		if(usuarioDao.listar().size() == 0){
			Usuario usuario = new Usuario();
			usuario.setNome("Italo Gustavo");
			usuario.setMatricula("092492");
			usuario.setSenha(Util.criptografaSenha("123456"));
			usuario.setEmail("italogmm@gmail.com");
			usuario = usuarioDao.cadastrar(usuario);
	
			GrupoEnvio grupoEnvioUFG = new GrupoEnvio();
			grupoEnvioUFG.setNome("Universidade Federal de Goiás");
			grupoEnvioUFG.setRecebimentoAtivado(true);
			grupoEnvioUFG.setCodigo(0l);
			grupoEnvioUFG = grupoEnvioDao.cadastrar(grupoEnvioUFG);
			
			GrupoEnvio grupoEnvioEngSoftware = new GrupoEnvio();
			grupoEnvioEngSoftware.setNome("Engenharia de Software");
			grupoEnvioEngSoftware.setRecebimentoAtivado(true);
			grupoEnvioEngSoftware.setCodigo(5277l);
			grupoEnvioEngSoftware = grupoEnvioDao.cadastrar(grupoEnvioEngSoftware);
			
			Notificacao notificacao1 = new Notificacao();
			notificacao1.setLida(false);
			notificacao1
					.setTexto("Hoje não haverá aula da disciplina, estará disponível "
							+ "no moodle uma ativiade válida como presença.");
			notificacao1.setGrupoEnvio(grupoEnvioEngSoftware);
			notificacao1.setTimeData(new Date().getTime());
			notificacaoDao.cadastrar(notificacao1);
			
			Notificacao notificacao2 = new Notificacao();
			notificacao2.setLida(false);
			notificacao2
					.setTexto("A universidade está em greve nos próximos dias, portanto, "
							+ "até qualquer outro aviso as aulas estão suspensas.");
			notificacao2.setGrupoEnvio(grupoEnvioUFG);
			notificacao2.setTimeData(new Date().getTime());
			notificacaoDao.cadastrar(notificacao2);
		}
	}
}
