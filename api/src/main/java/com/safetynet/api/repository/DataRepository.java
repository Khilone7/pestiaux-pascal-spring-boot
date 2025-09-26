package com.safetynet.api.repository;

import com.safetynet.api.repository.dto.DataDto;
import org.springframework.stereotype.Repository;

/**
 * Repository providing read-only access to the data loaded from the JSON file.
 * <p>The content is loaded once at application startup and then kept in memory;
 * no further disk access is performed.</p>
 */
@Repository
public interface DataRepository {

    /**
     * Returns the entire in-memory data set.
     *
     * @return all persons, fire stations and medical records
     */
    DataDto getAllData();
}
