package com.tegra.exercicio.matheus.voos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

public class Voo {
     private String voo;
     private String origem;
     private String destino;
     private String operadora;
    
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate data;
    
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime saida;
    
    @JsonSerialize(using = LocalTimeSerializer.class)
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime chegada;
    
    private BigDecimal valor;

     public Voo() {
         this.operadora = "99planes";
     }

     public String getVoo() {
         return voo;
     }

     public void setVoo(String voo) {
         this.voo = voo;
     }

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

    public LocalDate getData() {
        return data;
    }
    @JsonIgnore
    public LocalDateTime getSaidaDateTime() {
    	return this.getData().atTime(this.getSaida());
    }
    @JsonIgnore
    public LocalDateTime getChegadaDateTime() {
    	if(this.getChegada().isBefore(this.getChegada())) {
    		return this.getData().plusDays(1).atTime(this.getChegada());
    	}
    	
    	return this.getData().atTime(this.getChegada());
    }

    @JsonDeserialize(using = LocalDateDeserializer.class)
    public void setData(LocalDate data) {
        this.data = data;
    }
    @JsonSetter("data_saida")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    public void setDataSaida(LocalDate data) {
    	this.data = data;
    }

    public LocalTime getSaida() {
        return saida;
    }

    public void setSaida(LocalTime saida) {
        this.saida = saida;
    }

    public LocalTime getChegada() {
        return chegada;
    }

    public void setChegada(LocalTime chegada) {
        this.chegada = chegada;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

     public String getOperadora() {
         return this.operadora;
     }

     public void setOperadora(String operadora) {
         this.operadora = operadora;
     }
     
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chegada == null) ? 0 : chegada.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((destino == null) ? 0 : destino.hashCode());
		result = prime * result + ((operadora == null) ? 0 : operadora.hashCode());
		result = prime * result + ((origem == null) ? 0 : origem.hashCode());
		result = prime * result + ((saida == null) ? 0 : saida.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Voo other = (Voo) obj;
		if (chegada == null) {
			if (other.chegada != null)
				return false;
		} else if (!chegada.equals(other.chegada))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (destino == null) {
			if (other.destino != null)
				return false;
		} else if (!destino.equals(other.destino))
			return false;
		if (operadora == null) {
			if (other.operadora != null)
				return false;
		} else if (!operadora.equals(other.operadora))
			return false;
		if (origem == null) {
			if (other.origem != null)
				return false;
		} else if (!origem.equals(other.origem))
			return false;
		if (saida == null) {
			if (other.saida != null)
				return false;
		} else if (!saida.equals(other.saida))
			return false;
		return true;
	}
}