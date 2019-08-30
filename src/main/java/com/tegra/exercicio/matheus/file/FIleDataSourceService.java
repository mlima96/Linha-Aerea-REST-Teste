package com.tegra.exercicio.matheus.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.tegra.exercicio.matheus.voos.Voo;

import org.apache.commons.io.IOUtils;

public class FIleDataSourceService {

    public List<Voo> getListFromJson(String fileName) {
        try(InputStream is = FIleDataSourceService.class.getResourceAsStream("/files/" + fileName)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(IOUtils.toString(is, StandardCharsets.UTF_8), new TypeReference<List<Voo>>() {} );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public List<Voo> getListFromCsv(String fileName, CsvDataSourceListConstructor<Voo> constructor) {
        List<Voo> voos = Collections.emptyList();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader();
        
        try(InputStream is = FIleDataSourceService.class.getResourceAsStream("/files/" + fileName)) {
            MappingIterator<Map<String, String>> it = mapper.readerFor(new TypeReference<Map<String, String>>() {}).with(schema).readValues(is);
            voos = constructor.populateListFromCSVMap(it);
        } catch (IOException e) {
            e.printStackTrace();
		}

        return voos;
    }
}