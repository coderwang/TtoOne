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

import java.util.ArrayList;

@Data
public class DistCurStorageCapacity {
    /**
     * 分布式设备类型
     */
    private String devType;
    /**
     * 分布式集群存储池列表
     */
    ArrayList data;
}
