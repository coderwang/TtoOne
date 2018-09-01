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
public class DistributeCapacityStruct {
    /**
     * 分布式设备类型
     */
    private String devType;
    /**
     * 分布式存储返回各个存储池使用情况数组
     */
    private CurrentDistributedCapacityDetail[] data = new CurrentDistributedCapacityDetail[1];
}
