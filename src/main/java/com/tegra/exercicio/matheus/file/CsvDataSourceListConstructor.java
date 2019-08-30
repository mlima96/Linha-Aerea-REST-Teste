package com.tegra.exercicio.matheus.file;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;

public interface CsvDataSourceListConstructor<T> {
    public List<T> populateListFromCSVMap(MappingIterator<Map<String, String>> it);
}