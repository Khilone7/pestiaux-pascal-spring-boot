package com.safetynet.api.repository;

import com.safetynet.api.model.FireStation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides CRUD operations for {@link FireStation} entries with
 * in-memory storage and immediate JSON persistence.
 *
 * <p>Data is kept in memory and every add, update or delete also
 * writes the full dataset back to the JSON file.</p>
 */
@Repository
public interface FireStationRepository {

    /**
     * Returns all fire station mappings currently stored in memory.
     *
     * @return list of all {@link FireStation} objects
     */
    List<FireStation> getAllFireStation();

    /**
     * Adds a fire-station mapping and immediately persists the change.
     *
     * @param fireStation FireStation object to add
     */
    void addFireStation(FireStation fireStation);

    /**
     * Updates all entries whose address matches the given object,
     * then persists the change.
     *
     * @param fireStation updated FireStation object
     */
    void updateStationNumber(FireStation fireStation);

    /**
     * Deletes all entries whose address matches the given value,
     * then persists the change.
     *
     * @param address street address to match
     */
    void deleteByAddress(String address);

    /**
     * Deletes all entries whose station number matches the given value,
     * then persists the change.
     *
     * @param station station number to match
     */
    void deleteByStationNumber(Long station);
}
