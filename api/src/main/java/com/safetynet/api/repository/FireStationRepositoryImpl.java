package com.safetynet.api.repository;

import com.safetynet.api.model.FireStation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ListIterator;

@Repository
public class FireStationRepositoryImpl implements FireStationRepository{

    private final List<FireStation> fireStationList;

    public FireStationRepositoryImpl(DataRepository dataRepository){
        this.fireStationList=dataRepository.getAllData().firestations();
    }

    @Override
    public List<FireStation> getAllFireStation() {
        return fireStationList;
    }

    @Override
    public void addFireStation(FireStation fireStation){
        fireStationList.add(fireStation);
    }

    @Override
    public void updateStationNumber(FireStation fireStation) {
        ListIterator<FireStation> iterator = fireStationList.listIterator();
        while (iterator.hasNext()){
            FireStation f = iterator.next();
            if (f.getAddress().equals(fireStation.getAddress())){
                iterator.set(fireStation);
            }
        }
    }
}
