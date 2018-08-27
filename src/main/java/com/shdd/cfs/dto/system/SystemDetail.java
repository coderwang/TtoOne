package com.shdd.cfs.dto.system;

import lombok.Data;

import java.util.Map;

@Data
public class SystemDetail {
    private String devType;
    private String devStatus;
    private Double capacity;
    private Double usedCapacity;
    private Map<String, String> attribe;

}
