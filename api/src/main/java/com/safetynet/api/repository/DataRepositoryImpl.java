package com.safetynet.api.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.FireStation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class DataRepositoryImpl implements DataRepository {

    ObjectMapper objectMapper = new ObjectMapper();

    public List<Person> getAllPerson(){
        try {
            return objectMapper.readValue(new File("src/main/resources/Données.json"),new TypeReference<List<Person>>(){});
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture de la list de personne"+e);
        }
    }

    public List<FireStation> getAllFireStation(){
        try {
            return objectMapper.readValue(new File("src/main/resources/Données.json"),new TypeReference<List<FireStation>>(){});
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture de la list de FireStation"+e);
        }
    }

    public List<MedicalRecord> getAllMedicalRecord(){
        try {
            return objectMapper.readValue(new File("src/main/resources/Données.json"),new TypeReference<List<MedicalRecord>>(){});
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture de la list de MedicalRecord"+e);
        }
    }
}