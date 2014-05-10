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

import com.ufg.notificacoes.bean.Usuario;

public class UsuarioDao extends SQLiteOpenHelper{

	private static final int VERSAO = 1;
	private static final String TABELA = "Usuario";
	private static final String DATABASE = "NotificacoesUFG";
	
	private static final String TAG = "CADASTRO_USUARIOS";
	
	public UsuarioDao(){
		super(null, DATABASE, null, VERSAO);
	}
	
	public UsuarioDao(Context context){
		super(context, DATABASE, null, VERSAO);
	}
			
	public UsuarioDao(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String ddl = "CREATE TABLE " + TABELA + "( id INTEGER PRIMARY KEY, "
				+ "nome TEXT, "
				+ "email TEXT, "
				+ "matricula TEXT, "
				+ "senha TEXT)";
		db.execSQL(ddl);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABELA;
		db.execSQL(sql);
		onCreate(db);
	}
	
	public List<Usuario> listar(){
		
		List<Usuario> lista = new ArrayList<Usuario>();
		
		String sql = "SELECT id, nome, email, matricula, senha FROM " + TABELA + " ORDER BY nome ";
		
		Cursor cursor = getReadableDatabase().rawQuery(sql, null);
		
		try{
			while(cursor.moveToNext()){
				Usuario usuario = new Usuario();
				
				usuario.setId(cursor.getLong(0));
				usuario.setNome(cursor.getString(1));
				usuario.setEmail(cursor.getString(2));
				usuario.setMatricula(cursor.getString(3));
				usuario.setSenha(cursor.getString(4));
				
				lista.add(usuario);
			}
		}catch(SQLException e){
			Log.e(TAG, e.getMessage());
		}finally{
			cursor.close();
		}
		
		Log.i(TAG, "LISTANDO USUARIOS");
		return lista;
	}
	
	public void cadastrar(Usuario usuario){
		
		ContentValues values = new ContentValues();
		
		values.put("nome", usuario.getNome());
		values.put("email", usuario.getEmail());
		values.put("matricula", usuario.getMatricula());
		values.put("senha", usuario.getSenha());
		
		getWritableDatabase().insert(TABELA, null, values);
		Log.i(TAG, "USUARIO CADASTRADA!");
	}
}
