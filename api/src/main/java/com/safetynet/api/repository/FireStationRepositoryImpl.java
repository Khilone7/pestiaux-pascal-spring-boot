package com.safetynet.api.repository;

import com.safetynet.api.model.FireStation;
import com.safetynet.api.repository.dto.DataDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ListIterator;

/**
 * Default in-memory {@link FireStationRepository} implementation with
 * write-through JSON persistence.
 *
 * <p>Holds a live reference to the fire-station list from
 * {@link DataRepository#getAllData()}. Each add, update or delete
 * immediately writes the entire dataset back to the JSON file through
 * {@link DataRepository#saveAllData(DataDto)}.</p>
 *
 * <p>Not thread-safe. Updates and deletions affect <strong>all</strong>
 * entries that match the criteria (case-sensitive).</p>
 */
@Repository
public class FireStationRepositoryImpl implements FireStationRepository {

    private final List<FireStation> fireStationList;
    private final DataRepository dataRepository;

    /**
     * Creates the repository and loads the initial fire-station list
     * from the {@link DataRepository}.
     *
     * @param dataRepository source of the initial fire-station data
     */
    public FireStationRepositoryImpl(DataRepository dataRepository) {
        this.fireStationList = dataRepository.getAllData().firestations();
        this.dataRepository = dataRepository;
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
    public void addFireStation(FireStation fireStation) {
        fireStationList.add(fireStation);
        saveData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateStationNumber(FireStation fireStation) {
        ListIterator<FireStation> iterator = fireStationList.listIterator();
        while (iterator.hasNext()) {
            FireStation f = iterator.next();
            if (f.getAddress().equals(fireStation.getAddress())) {
                iterator.set(fireStation);
            }
        }
        saveData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByAddress(String address) {
        fireStationList.removeIf(f -> f.getAddress().equals(address));
        saveData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByStationNumber(Long station) {
        fireStationList.removeIf(f -> f.getStation().equals(station));
        saveData();
    }

    /**
     * Persists the current full dataset (persons, fire stations,
     * medical records) to the JSON file.
     */
    private void saveData() {
        DataDto current = dataRepository.getAllData();
        DataDto toSave = new DataDto(current.persons(), fireStationList, current.medicalrecords());
        dataRepository.saveAllData(toSave);
    }
}
