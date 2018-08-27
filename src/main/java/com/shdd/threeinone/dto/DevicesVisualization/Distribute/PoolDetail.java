package com.shdd.threeinone.dto.DevicesVisualization.Distribute;

import lombok.Data;

@Data
public class PoolDetail {
    private String   name;
    private Integer   capacity;
    private Integer  used;
    private Integer  free;
    private Integer  status;
}
