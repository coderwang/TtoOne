package com.shdd.cfs.dto.dashboard;

import lombok.Data;

@Data
public class DistributeCapacityStruct {
    private  String devType;
    private  CurrentDistributedCapacityDetail[] data = new CurrentDistributedCapacityDetail[1];
}
