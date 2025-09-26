package com.safetynet.api.repository;

import com.safetynet.api.model.FireStation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ListIterator;

/**
 * Default in-memory implementation of {@link FireStationRepository}.
 * <p>
 * Loads all fire station records from the JSON file at application startup
 * through {@link DataRepository} and keeps them in memory for the entire
 * lifetime of the application. No further disk access or persistence occurs.
 * </p>
 */
@Repository
public class FireStationRepositoryImpl implements FireStationRepository{

    private final List<FireStation> fireStationList;

    /**
     * Initializes the repository by loading the list of fire stations
     * from the {@link DataRepository}.
     *
     * @param dataRepository source of the initial fire station data;
     */
    public FireStationRepositoryImpl(DataRepository dataRepository){
        this.fireStationList=dataRepository.getAllData().firestations();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FireStation> getAllFireStation() {
        return fireStationList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFireStation(FireStation fireStation){
        fireStationList.add(fireStation);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByAddress(String address) {
        fireStationList.removeIf(f -> f.getAddress().equals(address));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByStationNumber(Long station) {
        fireStationList.removeIf(f -> f.getStation().equals(station));
    }
}
