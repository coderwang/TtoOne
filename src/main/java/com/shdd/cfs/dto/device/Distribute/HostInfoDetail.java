package com.shdd.cfs.dto.device.Distribute;

import lombok.Data;

@Data
public class HostInfoDetail {
    private String name;
    private String cpuType;
    private Integer cpuCount;
    private Double memCapacity;
    private Integer hardDiskCount;
    private Integer status;
}
