package com.shdd.threeinone.dto.instrumentPane;

import lombok.Data;

import java.util.Map;

@Data
public class CurrentDistributedCapacityDetail {
    private String poolName;
    private Double capacity;
    private Double usedCapacity;
    //private Map<String, String> attribe;
}
