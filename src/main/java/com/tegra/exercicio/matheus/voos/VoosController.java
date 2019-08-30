package com.tegra.exercicio.matheus.voos;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

@Component
@Path("/flights")
public class VoosController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Voo> listVoos(){
        return new VooService().criarListaDeVoos();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<VooEscala> findVoos(Voo vooPesquisa){
    	return new VooService().findVoos(vooPesquisa);
    }
}