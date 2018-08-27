package com.shdd.cfs.dto.dashboard;

import lombok.Data;

@Data
public class CurrentOpticalCapacityDetail {
    private String devType;
    private Double capacity;
    private Double usedCapacity;

}
