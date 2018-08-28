package com.shdd.cfs.dto.device.distribute;

import lombok.Data;

@Data
public class PoolDetail {
    private String   name;
    private Integer   capacity;
    private Integer  used;
    private Integer  free;
    private Integer  status;
}
