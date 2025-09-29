package com.safetynet.api.repository;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.repository.dto.DataDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ListIterator;

/**
 * Default in-memory {@link MedicalRecordsRepository} implementation with
 * write-through JSON persistence.
 *
 * <p>Holds a live reference to the medical-record list from
 * {@link DataRepository#getAllData()}. Each add, update or delete
 * immediately writes the entire dataset back to the JSON file through
 * {@link DataRepository#saveAllData(DataDto)}.</p>
 *
 * <p>Not thread-safe. Updates and deletions affect <strong>all</strong>
 * entries that match the given first/last name pair (case-sensitive).</p>
 */
@Repository
public class MedicalRecordsRepositoryImpl implements MedicalRecordsRepository {

    private final List<MedicalRecord> medicalRecordList;
    private final DataRepository dataRepository;

    /**
     * Creates the repository and loads the initial medical-record list
     * from the {@link DataRepository}.
     *
     * @param dataRepository source of the initial medical record data
     */
    public MedicalRecordsRepositoryImpl(DataRepository dataRepository) {
        this.medicalRecordList = dataRepository.getAllData().medicalrecords();
        this.dataRepository = dataRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MedicalRecord> getAllMedicalRecord(){
        return medicalRecordList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordList.add(medicalRecord);
        saveData();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Iterates through the list and replaces every entry whose first and last
     * name match those of the provided {@link MedicalRecord}.
     * If several records share the same names, all of them are replaced.
     * </p>
     */
    @Override
    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        ListIterator<MedicalRecord> iterator = medicalRecordList.listIterator();
        while (iterator.hasNext()) {
            MedicalRecord mr = iterator.next();
            if (mr.getFirstName().equals(medicalRecord.getFirstName())
                    && mr.getLastName().equals(medicalRecord.getLastName())) {
                iterator.set(medicalRecord);
            }
        }
        saveData();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteMedicalRecord(String firstName, String lastName) {
        medicalRecordList.removeIf(mr -> mr.getFirstName().equals(firstName)
                && mr.getLastName().equals(lastName));
        saveData();
    }

    /**
     * Persists the current full dataset (persons, fire stations,
     * medical records) to the JSON file.
     */
    private void saveData() {
        DataDto current = dataRepository.getAllData();
        DataDto toSave = new DataDto(current.persons(), current.firestations(), medicalRecordList);
        dataRepository.saveAllData(toSave);
    }
}