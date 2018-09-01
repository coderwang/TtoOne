/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地公司 版权所有
 *
 */
package com.shdd.cfs.dto.dashboard;

import lombok.Data;

@Data
public class CurrentOpticalCapacityDetail {
    private String devType;
    private Double capacity;
    private Double usedCapacity;

}
