package com.ufg.notificacoes.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Notificacao {
	
	private Long id;
	private String remetente;
	private Long timeData;
	private String texto;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRemetente() {
		return remetente;
	}
	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}
	public Long getTimeData() {
		return timeData;
	}
	public void setTimeData(Long timeData) {
		this.timeData = timeData;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	@Override
	public String toString() {
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date data = new Date(timeData);
		
		return texto + " - De: " + remetente + " - Em: " + dataFormatada.format(data);
	}

}
