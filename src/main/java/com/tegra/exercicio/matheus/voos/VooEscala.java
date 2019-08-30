package com.tegra.exercicio.matheus.voos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class VooEscala implements Comparable<VooEscala>{
	private String origem;
	private String destino;
	@JsonSerialize(using= LocalDateTimeSerializer.class)
	private LocalDateTime saida;
	@JsonSerialize(using= LocalDateTimeSerializer.class)
	private LocalDateTime chegada;
	private List<Voo> trechos;
	
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public LocalDateTime getSaida() {
		return saida;
	}
	public void setSaida(LocalDateTime saida) {
		this.saida = saida;
	}
	public LocalDateTime getChegada() {
		return chegada;
	}
	public void setChegada(LocalDateTime chegada) {
		this.chegada = chegada;
	}
	public List<Voo> getTrechos() {
		if(this.trechos == null) {
			this.trechos = new ArrayList<>();
		}
		return trechos;
	}
	public void setTrechos(List<Voo> trechos) {
		this.trechos = trechos;
	}
	public void addTrecho(Voo voo) {
		this.getTrechos().add(voo);
	}
	@Override
	public int compareTo(VooEscala o) {
		if(o.getSaida().isBefore(this.getSaida())) {
			return 1;
		} else if (o.getSaida().isAfter(this.getSaida())) {
			return -1;
		}
		return 0;
	}
}
