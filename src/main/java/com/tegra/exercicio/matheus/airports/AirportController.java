package com.tegra.exercicio.matheus.airports;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Path("/airports")
public class AirportController {
	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public String listAirports(){
		return new AirportService().listAirports();
	}
}
