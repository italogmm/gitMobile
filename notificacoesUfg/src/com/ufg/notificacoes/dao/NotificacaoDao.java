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

import com.ufg.notificacoes.bean.Notificacao;

public class NotificacaoDao extends SQLiteOpenHelper{

	private static final int VERSAO = 1;
	private static final String TABELA = "Notificacao";
	private static final String DATABASE = "NotificacoesUFG";
	
	private static final String TAG = "CADASTRO_NOTIFICACOES";
	
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
		String ddl = "CREATE TABLE " + TABELA + "( id INTEGER PRIMARY KEY, remetente TEXT, texto TEXT, dataNotificacao NUMERIC, lida INTEGER)";
		db.execSQL(ddl);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABELA;
		db.execSQL(sql);
		onCreate(db);
	}
	
	public List<Notificacao> listar(){
		
		List<Notificacao> lista = new ArrayList<Notificacao>();
		
		String sql = "SELECT id, remetente, texto, dataNotificacao, lida FROM " + TABELA + " ORDER BY dataNotificacao ";
		
		Cursor cursor = getReadableDatabase().rawQuery(sql, null);
		
		try{
			while(cursor.moveToNext()){
				Notificacao notificacao = new Notificacao();
				
				notificacao.setId(cursor.getLong(0));
				notificacao.setRemetente(cursor.getString(1));
				notificacao.setTexto(cursor.getString(2));
				notificacao.setTimeData(cursor.getLong(3));
				notificacao.setLida(cursor.getInt(4) == 1);
				
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
		
		String sql = "Select * from " + TABELA + " where id = " + id;
		
		Cursor cursor = getReadableDatabase().rawQuery(sql, null);
		
		try{
			while(cursor.moveToNext()){
				Notificacao notificacao = new Notificacao();
				
				notificacao.setId(cursor.getLong(0));
				notificacao.setRemetente(cursor.getString(1));
				notificacao.setTexto(cursor.getString(2));
				notificacao.setTimeData(cursor.getLong(3));
				notificacao.setLida(cursor.getInt(4) == 1);
				
				return notificacao;
			}
		}catch(SQLException e){
			Log.e(TAG, e.getMessage());
		}finally{
			cursor.close();
		}
		
		return null;
	}

	public void cadastrar(Notificacao notificacao){
		notificacao.setTimeData(new Date().getTime());
		
		ContentValues values = new ContentValues();
		
		values.put("remetente", notificacao.getRemetente());
		values.put("texto", notificacao.getTexto());
		values.put("dataNotificacao", notificacao.getTimeData());
		values.put("lida", notificacao.getLida() != null && notificacao.getLida() ? 1 : 0);
		
		
		getWritableDatabase().insert(TABELA, null, values);
		Log.i(TAG, "NOTIFICACAO CADASTRADA!");
	}
	
	public void alterar(Notificacao notificacao){
		ContentValues values = new ContentValues();
		
		values.put("remetente", notificacao.getRemetente());
		values.put("texto", notificacao.getTexto());
		values.put("dataNotificacao", notificacao.getTimeData());
		values.put("lida", notificacao.getLida() != null && notificacao.getLida() ? 1 : 0);
		
		getWritableDatabase().update(TABELA, values, " id = " + notificacao.getId(), null);
		Log.i(TAG, "NOTIFICACAO CADASTRADA!");
	}
}
