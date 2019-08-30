package com.tegra.exercicio.matheus.airports;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

@Service
public class AirportService {
	public String listAirports(){
		try {
			InputStream is = AirportService.class.getResourceAsStream( "/files/aeroportos.json");
			return IOUtils.toString(is, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}
}
