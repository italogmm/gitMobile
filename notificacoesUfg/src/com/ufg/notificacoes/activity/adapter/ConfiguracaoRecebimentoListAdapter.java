package com.ufg.notificacoes.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.ufg.notificacoes.R;
import com.ufg.notificacoes.bean.GrupoEnvio;
import com.ufg.notificacoes.dao.GrupoEnvioDao;

public class ConfiguracaoRecebimentoListAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final String[] idsGruposEnvio;
	private final String[] nomesGruposEnvio;
	private final Boolean[] recebimentoAtivado;
	private GrupoEnvioDao grupoEnvioDao;
	
	public ConfiguracaoRecebimentoListAdapter(Context context, String[] nomesGruposEnvio, String[] idsGruposEnvio, Boolean[] recebimentoAtivado) {
		super(context, R.layout.lista_configuracao_envio, nomesGruposEnvio);
		this.context = context;
		this.idsGruposEnvio = idsGruposEnvio;
		this.nomesGruposEnvio = nomesGruposEnvio;
		this.recebimentoAtivado = recebimentoAtivado;
		this.grupoEnvioDao = new GrupoEnvioDao(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(nomesGruposEnvio[position] == null){
			return null;
		}
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.lista_configuracao_envio, parent, false);
		CheckBox checkBoxGrupoEnvio = (CheckBox) rowView.findViewById(R.id.grupo_envio);
		checkBoxGrupoEnvio.setText(nomesGruposEnvio[position]);
		checkBoxGrupoEnvio.setChecked(recebimentoAtivado[position]);
		checkBoxGrupoEnvio.setId(Integer.valueOf(idsGruposEnvio[position]));
		checkBoxGrupoEnvio.setOnCheckedChangeListener(new OnCheckedChangeListener(){
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
		    	GrupoEnvio grupoEnvio = grupoEnvioDao.consultar(buttonView.getId());
		    	grupoEnvio.setRecebimentoAtivado(isChecked);
		    	grupoEnvioDao.alterar(grupoEnvio);
		    }
		});
		String s = nomesGruposEnvio[position];
		
		System.out.println(s);
		
		rowView.setId(Long.valueOf(idsGruposEnvio[position]).intValue());
		
		return rowView;
	}

}
