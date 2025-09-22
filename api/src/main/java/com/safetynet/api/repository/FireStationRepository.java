package com.safetynet.api.repository;

import com.safetynet.api.model.FireStation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FireStationRepository {
    List<FireStation> getAllFireStation();

    void addFireStation(FireStation fireStation);

    void updateStationNumber(FireStation fireStation);

    void deleteByAddress(String address);

    void deleteByStationNumber(Long station);
}
