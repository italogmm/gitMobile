package com.ufg.notificacoes.activity.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ufg.notificacoes.R;

public class NotificacaoListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] idsArray;
	private final String[] nomesArray;
	private final String[] textosArray;
	private final String[] datasArray;
	private final Boolean[] lidaArray;
 
	public NotificacaoListAdapter(Context context, String[] remetentes, String[] textosNotificacoes, String[] datas, String[] idsArray, Boolean[] lidaArray) {
		super(context, R.layout.lista_notificacoes, remetentes);
		this.context = context;
		this.nomesArray = remetentes;
		this.textosArray = textosNotificacoes;
		this.datasArray = datas;
		this.idsArray = idsArray;
		this.lidaArray = lidaArray;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(nomesArray[position] == null){
			return null;
		}
		
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.lista_notificacoes, parent, false);
		TextView textViewRemetente = (TextView) rowView.findViewById(R.id.nome);
		TextView textViewTexto = (TextView) rowView.findViewById(R.id.texto);
		TextView textViewData = (TextView) rowView.findViewById(R.id.data);
		textViewRemetente.setText(nomesArray[position]);
		textViewTexto.setText(textosArray[position]);
		textViewData.setText(datasArray[position]);
 
		// Change icon based on name
		String s = nomesArray[position];
 
		System.out.println(s);
		
		rowView.setId(Long.valueOf(idsArray[position]).intValue());
		
		if(!lidaArray[position]){
			textViewRemetente.setTypeface(null, Typeface.BOLD);
			textViewTexto.setTypeface(null, Typeface.BOLD);
			textViewData.setTypeface(null, Typeface.BOLD);
		}
		
		return rowView;
	}
}