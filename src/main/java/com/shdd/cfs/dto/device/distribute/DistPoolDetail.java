package com.shdd.cfs.dto.device.distribute;

import lombok.Data;

@Data
public class DistPoolDetail {
    private String name;
    private String capacity;
    private String used;
    private String free;
    private Integer status;
}
