package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.repository.dto.DataDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

@Repository
public class DataRepositoryImpl implements DataRepository {

    private final Logger logger = LogManager.getLogger(DataRepositoryImpl.class);

    private final ObjectMapper mapper;

    private final DataDto allData;

    public DataRepositoryImpl(ObjectMapper mapper) {
        this.mapper = mapper;
        this.allData = getData();
    }

    public DataDto getData(){
        try{
            return mapper.readValue(new File("src/main/resources/Data.json"), DataDto.class);
        } catch (IOException e){
            logger.error("Echec de la lecture du json");
            throw new RuntimeException("Erreur lors de la lecture du fichier"+e);
        }
    }

    @Override
    public DataDto getAllData(){
        return allData;
    }
}