package com.ufg.notificacoes.test;

import java.util.Date;

import android.test.AndroidTestCase;

import com.ufg.notificacoes.bean.Notificacao;
import com.ufg.notificacoes.dao.NotificacaoDao;

public class NotificacaoTest extends AndroidTestCase {
	
	public void incluirNotificacao() throws Exception{
		
		Notificacao notificacao = new Notificacao();
//		notificacao.setRemetente("UFG");
		notificacao.setTexto("A UFG estará fechada do dia 2 ao dia 10 de agosto.");
		notificacao.setTimeData(new Date().getTime());
		
		NotificacaoDao notificacaoDao = new NotificacaoDao();
		notificacaoDao.cadastrar(notificacao);
	}
}
