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
public class DistHostDetailInfo {
    /**
     * 分布式主机id，涵盖系统中cluster内所有机器
     */
    private Integer host_id;
    /**
     * 分布式主机id，涵盖系统中所有机器
     */
    private Integer node_id;
    /**
     * 分布式节点名称
     */
    private String name;
    /**
     * 分布式节点状态，0不在线，1在线
     */
    private Integer status;
    /**
     * 集群中每台主机的cpu类型
     */
    private String cpu_type;
    /**
     * 集群中每台主机的cpu数量
     */
    private Integer cpucount;
    /**
     * 集群中每台主机的内存容量
     */
    private Double mem_capacity;
    /**
     * 集群中每台主机的磁盘数量
     */
    private Integer disk_count;
}
