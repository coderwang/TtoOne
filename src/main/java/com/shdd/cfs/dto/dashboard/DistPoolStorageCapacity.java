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
public class DistPoolStorageCapacity {
    /**
     * 单个分布式集群存储总容量
     */
    private Double capacity;
    /**
     * 单个分布式集群已使用容量
     */
    private Double usedCapacity;
}
