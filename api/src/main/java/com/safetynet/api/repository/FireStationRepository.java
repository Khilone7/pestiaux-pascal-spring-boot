package com.safetynet.api.repository;

import com.safetynet.api.model.FireStation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides in-memory CRUD operations for {@link FireStation} entries.
 * <p>This repository is read/write but does not persist data to disk;
 * all changes exist only for the lifetime of the application.</p>
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
     * Adds a new fire station mapping.
     *
     * @param fireStation fire station to add
     */
    void addFireStation(FireStation fireStation);

    /**
     * Updates the station number for the fire station that matches
     * the address contained in the given {@link FireStation} object.
     *
     * @param fireStation object holding the target address and the
     *                    new station number
     */
    void updateStationNumber(FireStation fireStation);

    /**
     * Removes the fire station entry that matches the specified address.
     *
     * @param address street address to match
     */
    void deleteByAddress(String address);

    /**
     * Removes all fire station entries that use the specified station number.
     *
     * @param station station number whose entries should be deleted
     */
    void deleteByStationNumber(Long station);
}
