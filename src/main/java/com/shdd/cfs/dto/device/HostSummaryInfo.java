package com.shdd.cfs.dto.device;

import lombok.Data;

@Data
public class HostSummaryInfo {
    private String name;
    private String cpuType;
    private Integer cpuCount;
    private Double memCapacity;
    private Integer hardDiskCount;
}
