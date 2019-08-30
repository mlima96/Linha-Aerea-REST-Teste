package com.tegra.exercicio.matheus.voos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.MappingIterator;
import com.tegra.exercicio.matheus.file.CsvDataSourceListConstructor;
import com.tegra.exercicio.matheus.file.FIleDataSourceService;

public class VooService implements CsvDataSourceListConstructor<Voo> {
    private static final String PLANES_99_FILE_NAME = "99planes.json";
    private static final String UBERAIR_FILE_NAME = "uberair.csv";

    public List<Voo> criarListaDeVoos(){
        List<Voo> voos = new ArrayList<>();
        voos.addAll(new FIleDataSourceService().getListFromJson(PLANES_99_FILE_NAME));
        voos.addAll(new FIleDataSourceService().getListFromCsv(UBERAIR_FILE_NAME, this));
        return voos;
    }

    public List<VooEscala> findVoos(Voo vooPesquisa){
    	List<VooEscala> voosEscalas = new ArrayList<>();
    	List<Voo> listaVoos = this.criarListaDeVoos();
    	
    	listaVoos = this.extractVoosBYPesquisa(vooPesquisa, listaVoos);
    	
    	this.tratarVoosDiretos(vooPesquisa, voosEscalas, listaVoos);
    	this.tratarVoosComEscala(vooPesquisa, voosEscalas, listaVoos);
    	
    	Collections.sort(voosEscalas);
    	return voosEscalas;
    }

	private void tratarVoosComEscala(Voo vooPesquisa, List<VooEscala> voosEscalas, List<Voo> listaVoos) {
		List<Voo> voosComMesmaOrigem = findVoosComMesmaOrigemEData(vooPesquisa, listaVoos);
    	List<Voo> voosComMesmoDestino = findVooComMesmoDEstino(vooPesquisa, listaVoos);
    	
    	List<Voo> escala;
    	long horasDiferenca;
    	for (Voo vooOrigem : voosComMesmaOrigem) {
			for (Voo vooDestino: voosComMesmoDestino) {
				if(vooOrigem.getDestino().equals(vooDestino.getOrigem())
						&& vooOrigem.getChegadaDateTime().isBefore(vooDestino.getSaidaDateTime())) {
					horasDiferenca = vooOrigem.getChegadaDateTime().until(vooDestino.getSaidaDateTime(), ChronoUnit.HOURS);
					if(horasDiferenca <= 12) {
						escala = new ArrayList<>();
						escala.add(vooOrigem);
						escala.add(vooDestino);
						this.populateVooEscala(vooPesquisa, voosEscalas, escala);
					}
				}
			}
		}
	}

	private List<Voo> findVooComMesmoDEstino(Voo vooPesquisa, List<Voo> listaVoos) {
		return listaVoos
    			.parallelStream()
    			.filter(v -> v.getDestino().equals(vooPesquisa.getDestino()))
    			.collect(Collectors.toList());
	}

	private List<Voo> findVoosComMesmaOrigemEData(Voo vooPesquisa, List<Voo> listaVoos) {
		return listaVoos
    			.parallelStream()
    			.filter(v -> v.getOrigem().equals(vooPesquisa.getOrigem()) && v.getData().equals(vooPesquisa.getData()))
    			.collect(Collectors.toList());
	}

	private void removeVoosDiretos(Voo vooPesquisa, List<Voo> listaVoos) {
		listaVoos.removeIf(v -> v.getOrigem().equals(vooPesquisa.getOrigem())
					&& v.getDestino().equals(vooPesquisa.getDestino()));
	}

	private void tratarVoosDiretos(Voo vooPesquisa, List<VooEscala> voosEscalas, List<Voo> listaVoos) {
		List<Voo> voosDiretos;
		voosDiretos = this.findVoosDiretos(vooPesquisa, listaVoos);
		List<Voo> lista = new ArrayList<>();
    	for (Voo voo : voosDiretos) {
    		lista.add(voo);
    		this.populateVooEscala(vooPesquisa, voosEscalas, lista);
    		lista.clear();
		}
    	this.removeVoosDiretos(vooPesquisa, listaVoos);
	}

	private void populateVooEscala(Voo vooPesquisa, List<VooEscala> voosEscalas, List<Voo> voos) {
		Voo primeiroVoo = voos.get(0);
		Voo ultimoVoo = voos.get(voos.size() - 1);
		VooEscala vooEscala = new VooEscala();
		vooEscala.setOrigem(vooPesquisa.getOrigem());
		vooEscala.setDestino(vooPesquisa.getDestino());
		vooEscala.setSaida(primeiroVoo.getSaidaDateTime());
		vooEscala.setChegada(ultimoVoo.getChegadaDateTime());
			
    	for (Voo v : voos) {
    		vooEscala.addTrecho(v);
    		voosEscalas.add(vooEscala);
		}
	}
    
    private List<Voo> findVoosDiretos(Voo vooPesquisa, List<Voo> listaVoos){
    	return listaVoos
    			.parallelStream()
    			.filter(v -> v.getData().equals(vooPesquisa.getData()) 
    						&& v.getOrigem().equals(vooPesquisa.getOrigem())
    						&& v.getDestino().equals(vooPesquisa.getDestino()))
    			.collect(Collectors.toList());
    			
    }

	private List<Voo> extractVoosBYPesquisa(Voo voo, List<Voo> listaVoos) {
		return listaVoos
    			.parallelStream()
    			.filter(v -> (v.getOrigem().equals(voo.getOrigem()) || v.getDestino().equals(voo.getDestino())) 
    							&& (v.getData().isAfter(voo.getData()) || v.getData().equals(voo.getData()))
    				   )
    			.collect(Collectors.toList());
	}

    @Override
    public List<Voo> populateListFromCSVMap(MappingIterator<Map<String, String>> it) {
        List<Voo> voos = new ArrayList<>();
        Map<String, String> rowAsMap;
        Voo voo;
        while(it.hasNext()) {
            rowAsMap = it.next();

            voo = new Voo();
            voo.setVoo(rowAsMap.get("numero_voo"));
            voo.setOrigem(rowAsMap.get("aeroporto_origem"));
            voo.setDestino(rowAsMap.get("aeroporto_destino"));
            voo.setData(LocalDate.parse(rowAsMap.get("data")));
            voo.setSaida(LocalTime.parse(rowAsMap.get("horario_saida")));
            voo.setChegada(LocalTime.parse(rowAsMap.get("horario_chegada")));
            voo.setValor(new BigDecimal(rowAsMap.get("preco")));
            voo.setOperadora("UberAir");
            voos.add(voo);
        }

        return voos;
	}
}