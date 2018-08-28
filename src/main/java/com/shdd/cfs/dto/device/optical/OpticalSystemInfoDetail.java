package com.shdd.cfs.dto.device.optical;

import lombok.Data;

@Data
public class OpticalSystemInfoDetail {
    private String name;
    private String cpuType;
    private Integer cpuCount;
    private Double memCapacity;
    private Integer hardDiskCount;
    private Integer status;
}
