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

import com.ufg.notificacoes.bean.Disciplina;

public class DisciplinaDao extends SQLiteOpenHelper{

	private static final int VERSAO = 1;
	private static final String TABELA = "Disciplina";
	private static final String DATABASE = "NotificacoesUFG";
	
	private static final String TAG = "CADASTRO_DISCIPLINAS";
	
	public DisciplinaDao(){
		super(null, DATABASE, null, VERSAO);
	}
	
	public DisciplinaDao(Context context){
		super(context, DATABASE, null, VERSAO);
	}
			
	public DisciplinaDao(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String ddl = "CREATE TABLE " + TABELA + "( id INTEGER PRIMARY KEY, "
				+ "nome TEXT)";
		db.execSQL(ddl);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABELA;
		db.execSQL(sql);
		onCreate(db);
	}
	
	public List<Disciplina> listar(){
		
		List<Disciplina> lista = new ArrayList<Disciplina>();
		
		String sql = "SELECT id, nome FROM " + TABELA + " ORDER BY nome ";
		
		Cursor cursor = getReadableDatabase().rawQuery(sql, null);
		
		try{
			while(cursor.moveToNext()){
				Disciplina disciplina = new Disciplina();
				
				disciplina.setId(cursor.getLong(0));
				disciplina.setNome(cursor.getString(1));
				
				lista.add(disciplina);
			}
		}catch(SQLException e){
			Log.e(TAG, e.getMessage());
		}finally{
			cursor.close();
		}
		
		Log.i(TAG, "LISTANDO DISCIPLINAS");
		return lista;
	}
	
	public void cadastrar(Disciplina disciplina){
		
		ContentValues values = new ContentValues();
		
		values.put("nome", disciplina.getNome());
		
		getWritableDatabase().insert(TABELA, null, values);
		Log.i(TAG, "DISCIPLINA CADASTRADA!");
	}
}