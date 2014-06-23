package com.ufg.notificacoes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ufg.notificacoes.bean.Configuracoes;
import com.ufg.notificacoes.bean.Usuario;

public class ConfiguracoesDao extends SQLiteOpenHelper{
	
	private static final int VERSAO = 6;
	private static final String TABELA = "Configuracoes";
	private static final String DATABASE = "NotificacoesUFG";
	
	private static final String TAG = "CADASTRO_CONFIGURACAO";
	public static final String ddlCreate = "CREATE TABLE " + TABELA + "( id INTEGER PRIMARY KEY, id_usuario_logado INTEGER)";
	
	public ConfiguracoesDao(){
		super(null, DATABASE, null, VERSAO);
	}
	
	public ConfiguracoesDao(Context context){
		super(context, DATABASE, null, VERSAO);
	}
			
	public ConfiguracoesDao(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		NotificacaoDao notificacaoDao = new NotificacaoDao();
		notificacaoDao.onCreate(db);
		
		Log.i(TAG, "CRIANDO TABELA DE CONFIGURACAO");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABELA;
		db.execSQL(sql);
		onCreate(db);
	}
	
	public Configuracoes consultar(){
		
		String sql = "SELECT config.id, usu.id, usu.nome, usu.email, usu.matricula, usu.senha  FROM " + TABELA + " config "
				+ " left join " + UsuarioDao.TABELA + " usu on usu.id = config.id_usuario_logado ";
		
		Cursor cursor = getReadableDatabase().rawQuery(sql, null);
		
		try{
			while(cursor.moveToNext()){
				Configuracoes configuracoes = new Configuracoes();
				
				configuracoes.setId(cursor.getLong(0));
				
				Usuario usuarioLogado = null;
				
				Long idUsuarioLogado = cursor.getLong(1);
				if(idUsuarioLogado != null && idUsuarioLogado != 0){
					usuarioLogado = new Usuario();
					usuarioLogado.setId(idUsuarioLogado);
					usuarioLogado.setNome(cursor.getString(2));
					usuarioLogado.setEmail(cursor.getString(3));
					usuarioLogado.setMatricula(cursor.getString(4));
					usuarioLogado.setSenha(cursor.getString(5));
				}
				configuracoes.setUsuarioLogado(usuarioLogado);
				configuracoes.setId(cursor.getLong(0));
				
				return configuracoes;
			}
		}catch(SQLException e){
			Log.e(TAG, e.getMessage());
		}finally{
			cursor.close();
		}
		
		Log.i(TAG, "CONSULTANDO CONFIGURACOES");
		return null;
	}
	
	public Configuracoes alterar(Configuracoes configuracoes){
		ContentValues values = new ContentValues();
		
		if(configuracoes.getUsuarioLogado() != null)
			values.put("id_usuario_logado", configuracoes.getUsuarioLogado().getId());
		else
			values.putNull("id_usuario_logado");
		
		getWritableDatabase().update(TABELA, values, " id = " + configuracoes.getId(), null);
		Log.i(TAG, "NOTIFICACAO CADASTRADA!");
		
		return consultar();
	}
	
	public Configuracoes incluir(Configuracoes configuracoes){
		ContentValues values = new ContentValues();
		
		if(configuracoes.getUsuarioLogado() != null)
			values.put("id_usuario_logado", configuracoes.getUsuarioLogado().getId());
		else
			values.putNull("id_usuario_logado");
		
		getWritableDatabase().insert(TABELA, null,values);
		Log.i(TAG, "NOTIFICACAO CADASTRADA!");
		
		return consultar();
	}
}
