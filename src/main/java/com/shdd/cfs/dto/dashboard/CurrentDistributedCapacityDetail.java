package com.shdd.cfs.dto.dashboard;

import lombok.Data;

@Data
public class CurrentDistributedCapacityDetail {
    private String poolName;
    private Double capacity;
    private Double usedCapacity;
    //private Map<String, String> attribe;
}
