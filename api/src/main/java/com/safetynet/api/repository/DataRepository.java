package com.safetynet.api.repository;

import com.safetynet.api.repository.dto.DataDto;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository {

    DataDto getAllData();
}
