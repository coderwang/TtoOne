/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地公司 版权所有
 *
 */
package com.shdd.cfs.dto.message;

import lombok.Data;

@Data
public class WarningInfo {
    /**
     * 下级系统类型分类，取值为distribute,tape,disk
     */
    String name;
    /**
     * 状态值，0表示正常，1表示有告警
     */
    int status;
}
