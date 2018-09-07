/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地公司 版权所有
 *
 */

package com.shdd.cfs.dto.device.optical;

import lombok.Data;

/**
 * @author: jiafuzeng
 * @version: 1.0 2018/8/28
 */

@Data
public class CdPoolStorageInfo {
    /**
     * 光盘库节点id
     */
    private Integer id;
    /**
     * 光盘库节点名字
     */
    private String name;
    /**
     * 光盘库节点当前使用容量
     */
    private Double used;
    /**
     * 光盘库节点当前总容量
     */
    private Double capacity;
    /**
     * 光盘库节点是否在线
     * 1 在线 2离线
     */
    private Integer status;
}
