package com.shdd.threeinone.dto.instrumentPane;

import lombok.Data;

@Data
public class DistributeCapacityStruct {
    private  String devType;
    private  CurrentDistributedCapacityDetail[] data = new CurrentDistributedCapacityDetail[1];
}
