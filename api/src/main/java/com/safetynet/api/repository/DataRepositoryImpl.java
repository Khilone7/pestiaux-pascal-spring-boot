package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.repository.dto.DataDto;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Default {@link DataRepository} implementation.
 *
 * <p>Loads <code>Data.json</code> once at application startup and keeps the
 * resulting {@link DataDto} in memory. Calls to {@link #saveAllData(DataDto)}
 * write the current state back to the JSON file.</p>
 */
@Repository
public class DataRepositoryImpl implements DataRepository {

    private final ObjectMapper mapper;
    private final DataDto allData;

    /**
     * Constructs the repository and loads the JSON data immediately.
     *
     * @param mapper Jackson {@link ObjectMapper} used to parse the JSON file
     * @throws UncheckedIOException if the file cannot be read or parsed
     */
    public DataRepositoryImpl(ObjectMapper mapper) {
        this.mapper = mapper;
        this.allData = getDataInJson();
    }

    /**
     * Reads <strong>src/main/resources/Data.json</strong> once and converts it
     * to a {@link DataDto}. Any I/O or parsing error is rethrown as an
     * {@link UncheckedIOException}.
     *
     * @return fully populated {@link DataDto}
     */
    public DataDto getDataInJson() {
        try {
            return mapper.readValue(new File("src/main/resources/Data.json"), DataDto.class);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read JSON file", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAllData(DataDto allData) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/Data.json"), allData);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write Json file", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataDto getAllData() {
        return allData;
    }
}