package com.shdd.threeinone.dto.DevicesVisualization.Distribute;

import lombok.Data;

@Data
public class HostNodeInfoDetail {
    private Integer id;
    private String name;
    private String hostname;
    private Double used;
    private Double capacity;
    private Integer status;
}
