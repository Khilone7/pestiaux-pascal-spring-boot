package com.safetynet.api.repository;

import com.safetynet.api.repository.dto.DataDto;
import org.springframework.stereotype.Repository;

/**
 * Repository for the JSON-backed application data.
 *
 * <p>Holds a single in-memory {@link DataDto} snapshot loaded at startup.
 * Callers can persist the current state back to the JSON file when needed.</p>
 */
@Repository
public interface DataRepository {

    /**
     * Returns the entire in-memory data set.
     *
     * @return all persons, fire stations and medical records
     */
    DataDto getAllData();

    /**
     * Persists the given dataset to the JSON file.
     *
     * @param allData dataset to save
     * @throws java.io.UncheckedIOException if writing fails
     */
    void saveAllData(DataDto allData);
}
