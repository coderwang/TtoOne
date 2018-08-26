package com.shdd.threeinone.dto.DevicesVisualization.Tape;

import lombok.Data;

@Data
public class TapeSystemInfoDetail {
    private String name;
    private String cpuType;
    private Integer cpuCount;
    private Double memCapacity;
    private Integer hardDiskCount;
    private Integer status;
}
