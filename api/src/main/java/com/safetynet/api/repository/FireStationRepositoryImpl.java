package com.safetynet.api.repository;

import com.safetynet.api.model.FireStation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FireStationRepositoryImpl implements FireStationRepository{

    private final DataRepository dataRepository;

    public FireStationRepositoryImpl(DataRepository dataRepository){
        this.dataRepository =dataRepository;
    }

    @Override
    public List<FireStation> getAllFireStation() {
        return dataRepository.getAllData().firestations();
    }
}
