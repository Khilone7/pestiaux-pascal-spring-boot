package com.safetynet.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FireStation {

    private String address;
    private Long station;
}
