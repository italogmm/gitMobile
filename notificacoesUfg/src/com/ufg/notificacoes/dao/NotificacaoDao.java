package com.ufg.notificacoes.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ufg.notificacoes.bean.GrupoEnvio;
import com.ufg.notificacoes.bean.Notificacao;

public class NotificacaoDao extends SQLiteOpenHelper{

	private static final int VERSAO = 5;
	private static final String TABELA = "Notificacao";
	private static final String DATABASE = "NotificacoesUFG";
	
	private static final String TAG = "CADASTRO_NOTIFICACOES";
	public static final String ddlCreate = "CREATE TABLE " + TABELA + "(id INTEGER PRIMARY KEY, texto TEXT, dataNotificacao NUMERIC, lida INTEGER, id_grupo_envio INTEGER)";
	
	public NotificacaoDao(){
		super(null, DATABASE, null, VERSAO);
	}
	
	public NotificacaoDao(Context context){
		super(context, DATABASE, null, VERSAO);
	}
			
	public NotificacaoDao(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ddlCreate);
		db.execSQL(UsuarioDao.ddlCreate);
		db.execSQL(GrupoEnvioDao.ddlCreate);
		
		Log.i(TAG, "CRIANDO TABELA DE NOTIFICACAO");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABELA;
		db.execSQL(sql);
		onCreate(db);
	}
	
	public List<Notificacao> listar(){
		
		List<Notificacao> lista = new ArrayList<Notificacao>();
		
		String sql = "SELECT bean.id, bean.texto, bean.dataNotificacao, bean.lida, "
				+ "gpEnvio.id, gpEnvio.nome, gpEnvio.codigo, gpEnvio.recebimentoAtivado FROM " + TABELA + " bean "
				+ " left join " + GrupoEnvioDao.TABELA + " gpEnvio on gpEnvio.id = bean.id_grupo_envio ";
		
		Cursor cursor = getReadableDatabase().rawQuery(sql, null);
		
		try{
			while(cursor.moveToNext()){
				Notificacao notificacao = new Notificacao();
				
				notificacao.setId(cursor.getLong(0));
				notificacao.setTexto(cursor.getString(1));
				notificacao.setTimeData(cursor.getLong(2));
				notificacao.setLida(cursor.getInt(3) == 1);
				notificacao.setGrupoEnvio(new GrupoEnvio(cursor.getLong(4), cursor.getString(5), cursor.getLong(6), cursor.getInt(7) == 1));
				
				lista.add(notificacao);
			}
		}catch(SQLException e){
			Log.e(TAG, e.getMessage());
		}finally{
			cursor.close();
		}
		
		Log.i(TAG, "LISTANDO NOTIFICACOES");
		return lista;
	}
	
	public Notificacao consultar(long id){
		
		String sql = "SELECT bean.id, bean.texto, bean.dataNotificacao, bean.lida, "
				+ "gpEnvio.id, gpEnvio.nome, gpEnvio.codigo, gpEnvio.recebimentoAtivado FROM " + TABELA + " bean "
				+ " left join " + GrupoEnvioDao.TABELA + " gpEnvio on gpEnvio.id = bean.id_grupo_envio "
				+ "where bean.id = " + id;
		
		Cursor cursor = getReadableDatabase().rawQuery(sql, null);
		
		try{
			while(cursor.moveToNext()){
				Notificacao notificacao = new Notificacao();
				
				notificacao.setId(cursor.getLong(0));
				notificacao.setTexto(cursor.getString(1));
				notificacao.setTimeData(cursor.getLong(2));
				notificacao.setLida(cursor.getInt(3) == 1);
				notificacao.setGrupoEnvio(new GrupoEnvio(cursor.getLong(4), cursor.getString(5), cursor.getLong(6), cursor.getInt(7) == 1));
				
				return notificacao;
			}
		}catch(SQLException e){
			Log.e(TAG, e.getMessage());
		}finally{
			cursor.close();
		}
		
		return null;
	}
	
	public void excluir(Notificacao notificacao){
		getReadableDatabase().delete(TABELA, " id = " + notificacao.getId(), null);
	}

	public Notificacao cadastrar(Notificacao notificacao){
		notificacao.setTimeData(new Date().getTime());
		
		ContentValues values = new ContentValues();
		
		values.put("texto", notificacao.getTexto());
		values.put("dataNotificacao", notificacao.getTimeData());
		values.put("lida", notificacao.getLida() != null && notificacao.getLida() ? 1 : 0);
		values.put("id_grupo_envio", notificacao.getGrupoEnvio().getId());
		
		long id = getWritableDatabase().insert(TABELA, null, values);
		Log.i(TAG, "NOTIFICACAO CADASTRADA!");
		
		return consultar(id);
	}
	
	public Notificacao alterar(Notificacao notificacao){
		ContentValues values = new ContentValues();
		
		values.put("texto", notificacao.getTexto());
		values.put("dataNotificacao", notificacao.getTimeData());
		values.put("lida", notificacao.getLida() != null && notificacao.getLida() ? 1 : 0);
		values.put("id_grupo_envio", notificacao.getGrupoEnvio().getId());
		
		getWritableDatabase().update(TABELA, values, " id = " + notificacao.getId(), null);
		Log.i(TAG, "NOTIFICACAO CADASTRADA!");
		
		return consultar(notificacao.getId());
	}
}
