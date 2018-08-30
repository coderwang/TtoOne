/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地公司 版权所有
 *
 */
package com.shdd.cfs.dto.dashboard;

/**
 * @author: jfz
 * @version: 1.0 2018/8/30
 */
import lombok.Data;


@Data
public class CurrentTapeCapacityDetail {
    /**
     * 磁带库设备方式
     */
    private String devType;
    /**
     * 磁带库总容量
     */
    private Double capacity;
    /**
     * 磁带库使用容量
     */
    private Double usedCapacity;
}
