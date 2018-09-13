package com.shdd.cfs.dto.device.distribute;

import lombok.Data;

@Data
public class DistPoolDetail {
    private String name;
    private Double capacity;
    private Double used;
    private Double free;
    private Integer status;
}
