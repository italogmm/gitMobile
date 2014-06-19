package com.ufg.notificacoes.dao;

import java.util.ArrayList;
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

public class GrupoEnvioDao extends SQLiteOpenHelper{

	private static final int VERSAO = 5;
	public static final String TABELA = "Grupoenvio";
	private static final String DATABASE = "NotificacoesUFG";
	
	private static final String TAG = "CADASTRO_GRUPO_ENVIO";
	public static final String ddlCreate = "CREATE TABLE " + TABELA + "( "
			+ "id INTEGER PRIMARY KEY, "
			+ "nome TEXT, "
			+ "codigo TEXT, "
			+ "recebimentoAtivado INTEGER)";
	
	public GrupoEnvioDao(){
		super(null, DATABASE, null, VERSAO);
	}
	
	public GrupoEnvioDao(Context context){
		super(context, DATABASE, null, VERSAO);
	}
			
	public GrupoEnvioDao(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		NotificacaoDao notificacaoDao = new NotificacaoDao();
		notificacaoDao.onCreate(db);
		
		Log.i(TAG, "TABELA DE GRUPO DE ENVIO CRIADA");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABELA;
		db.execSQL(sql);
		onCreate(db);
	}
	
	public List<GrupoEnvio> listar(){
		
		List<GrupoEnvio> lista = new ArrayList<GrupoEnvio>();
		
		String sql = "SELECT id, nome, codigo, recebimentoAtivado FROM " + TABELA + " ORDER BY nome ";
		
		Cursor cursor = getReadableDatabase().rawQuery(sql, null);
		
		try{
			while(cursor.moveToNext()){
				GrupoEnvio grupoEnvio = new GrupoEnvio();
				
				grupoEnvio.setId(cursor.getLong(0));
				grupoEnvio.setNome(cursor.getString(1));
				grupoEnvio.setCodigo(cursor.getString(2));
				grupoEnvio.setRecebimentoAtivado(cursor.getInt(3) == 1);
				
				lista.add(grupoEnvio);
			}
		}catch(SQLException e){
			Log.e(TAG, e.getMessage());
		}finally{
			cursor.close();
		}
		
		Log.i(TAG, "LISTANDO GRUPOS DE ENVIO");
		return lista;
	}
	
	public GrupoEnvio consultar(long id){

		String sql = "Select * from " + TABELA + " where id = " + id;
		
		Cursor cursor = getReadableDatabase().rawQuery(sql, null);
		
		try{
			while(cursor.moveToNext()){
				GrupoEnvio grupoEnvio = new GrupoEnvio();
				
				grupoEnvio.setId(cursor.getLong(0));
				grupoEnvio.setNome(cursor.getString(1));
				grupoEnvio.setCodigo(cursor.getString(2));
				grupoEnvio.setRecebimentoAtivado(cursor.getInt(3) == 1);
				
				return grupoEnvio;
			}
		}catch(SQLException e){
			Log.e(TAG, e.getMessage());
		}finally{
			cursor.close();
		}
		
		return null;
	}

	public GrupoEnvio cadastrar(GrupoEnvio grupoEnvio){
		
		ContentValues values = new ContentValues();
		
		values.put("nome", grupoEnvio.getNome());
		values.put("codigo", grupoEnvio.getNome());
		values.put("recebimentoAtivado", grupoEnvio.getRecebimentoAtivado() != null && 
				grupoEnvio.getRecebimentoAtivado() ? 1 : 0);
		
		long id = getWritableDatabase().insert(TABELA, null, values);
		
		Log.i(TAG, "GRUPO DE ENVIO CADASTRADO!");
		
		return consultar(id);
	}
}
