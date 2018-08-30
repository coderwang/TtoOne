/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地公司 版权所有
 *
 */
package com.shdd.cfs.dto.message;

import lombok.Data;

/**
 * @author: xphi
 * @version: 1.0 2018/8/28
 */

@Data
public class DistSystemData {

    /**
     * 处理器信息
     */
    Double cpu;
    /**
     * 内存信息
     */
    Double ram;
    /**
     * 带宽信息
     */
    Double bw;
    /**
     * 回传测试数据
     */
    String helloMessage;
}
