package com.tegra.exercicio.matheus.config;

import javax.ws.rs.ApplicationPath;

import com.tegra.exercicio.matheus.airports.AirportController;
import com.tegra.exercicio.matheus.voos.VoosController;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * JerseyConfig
 */ 
@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(AirportController.class);
        register(VoosController.class);
    }
}