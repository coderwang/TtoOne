package com.shdd.cfs.dto.dashboard;

import lombok.Data;


@Data
public class CurrentTapeCapacityDetail {
    private String devType;
    private Double capacity;
    private Double usedCapacity;
}
