package com.ufg.notificacoes.test;

import java.util.Date;

import android.test.AndroidTestCase;

import com.ufg.notificacoes.bean.GrupoEnvio;
import com.ufg.notificacoes.bean.Notificacao;
import com.ufg.notificacoes.bean.Usuario;
import com.ufg.notificacoes.dao.GrupoEnvioDao;
import com.ufg.notificacoes.dao.NotificacaoDao;
import com.ufg.notificacoes.dao.UsuarioDao;
import com.ufg.notificacoes.util.Util;

public class DataGeneratorForTests extends AndroidTestCase {

	public void incluirDadosParaTeste() throws Exception {

		UsuarioDao usuarioDao = new UsuarioDao();
		NotificacaoDao notificacaoDao = new NotificacaoDao();
		GrupoEnvioDao grupoEnvioDao = new GrupoEnvioDao();
		
		Usuario usuario = new Usuario();
		usuario.setNome("Italo Gustavo");
		usuario.setMatricula("092492");
		usuario.setSenha(Util.criptografaSenha("123456"));
		usuario.setEmail("italogmm@gmail.com");
		usuario = usuarioDao.cadastrar(usuario);

		GrupoEnvio grupoEnvioUFG = new GrupoEnvio();
		grupoEnvioUFG.setNome("UFG");
		grupoEnvioUFG.setRecebimentoAtivado(true);
		grupoEnvioUFG = grupoEnvioDao.cadastrar(grupoEnvioUFG);
		
		GrupoEnvio grupoEnvioEngSoftware = new GrupoEnvio();
		grupoEnvioEngSoftware.setNome("Engenharia de Software");
		grupoEnvioEngSoftware.setRecebimentoAtivado(true);
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
