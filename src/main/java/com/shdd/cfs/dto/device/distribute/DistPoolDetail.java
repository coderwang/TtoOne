package com.shdd.cfs.dto.device.distribute;

import lombok.Data;

@Data
public class DistPoolDetail {
    private String name;
    private String capacity;
    private Double used;
    private String free;
    private Integer status;
}
