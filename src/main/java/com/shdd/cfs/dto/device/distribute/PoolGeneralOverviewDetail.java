package com.shdd.cfs.dto.device.distribute;

import lombok.Data;

@Data
public class PoolGeneralOverviewDetail {
    private String name;
    private String  id;
    private Double capacity;
    private Double used;
    private Double free;
}
