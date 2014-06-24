package com.ufg.notificacoes.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Notificacao {
	
	private Long id;
	private GrupoEnvio grupoEnvio;
	private Long timeData;
	private String texto;
	private Boolean lida;
	
	public Notificacao(){}

	public Notificacao(String texto){
		this.texto = texto;
	}
	
	public Notificacao(String remetente, String texto){
		this.texto = texto;
	}
	
	public Notificacao(GrupoEnvio grupoEnvio, String texto){
		this.texto = texto;
		this.grupoEnvio = grupoEnvio;
	}
	
	public Notificacao(String remetente, String texto, Date data, Boolean lida){
		this.texto = texto;
		this.timeData = data.getTime();
		this.lida = lida;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Boolean getLida() {
		return lida;
	}
	public void setLida(Boolean lida) {
		this.lida = lida;
	}
	public String getDataFormatada(){
		if(timeData == null)
			return "";
		
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date data = new Date(timeData);
		return dataFormatada.format(data);
	}
	public GrupoEnvio getGrupoEnvio() {
		return grupoEnvio;
	}
	public void setGrupoEnvio(GrupoEnvio grupoEnvio) {
		this.grupoEnvio = grupoEnvio;
	}
}
