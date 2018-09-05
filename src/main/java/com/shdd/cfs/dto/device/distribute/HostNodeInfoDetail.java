package com.shdd.cfs.dto.device.distribute;

import lombok.Data;

@Data
public class HostNodeInfoDetail {
    private Integer id;
    private String name;
    private Double used;
    private Double capacity;
    private Integer status;
}
