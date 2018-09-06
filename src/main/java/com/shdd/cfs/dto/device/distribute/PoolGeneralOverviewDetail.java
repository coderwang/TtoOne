package com.shdd.cfs.dto.device.distribute;

import lombok.Data;

@Data
public class PoolGeneralOverviewDetail {
    private String name;
    /**
     * 存储池ID，磁带库中磁带组对应的ID，将使用磁带组名称代替ID
     */
    private String  id;
    private Double capacity;
    private Double used;
    private Double free;
}
