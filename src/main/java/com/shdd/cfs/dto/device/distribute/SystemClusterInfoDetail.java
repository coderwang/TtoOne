/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地公司 版权所有
 *
 */
package com.shdd.cfs.dto.device.distribute;

import lombok.Data;

/**
 * @author: xphi
 * @version: 1.0 2018/8/28
 */
@Data
public class SystemClusterInfoDetail {
    /**
     * 分布式主机id
     */
    private Integer id;
    /**
     * 分布式节点名称
     */
    private String name;
    /**
     * 分布式节点状态
     */
    private Integer status;
}
