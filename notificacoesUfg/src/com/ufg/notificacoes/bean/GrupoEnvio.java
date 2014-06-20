package com.ufg.notificacoes.bean;

public class GrupoEnvio {
	
	private Long id;
	private String nome;
	private Long codigo;
	private Boolean recebimentoAtivado;
	
	public GrupoEnvio(){}
	
	public GrupoEnvio(Long id){
		this.id = id;
	}
	
	public GrupoEnvio(Long id, String nome, Long codigo, Boolean recebimentoAtivado){
		this.id = id;
		this.nome = nome;
		this.codigo = codigo;
		this.recebimentoAtivado = recebimentoAtivado;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Boolean getRecebimentoAtivado() {
		return recebimentoAtivado;
	}
	public void setRecebimentoAtivado(Boolean recebimentoAtivado) {
		this.recebimentoAtivado = recebimentoAtivado;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
}
