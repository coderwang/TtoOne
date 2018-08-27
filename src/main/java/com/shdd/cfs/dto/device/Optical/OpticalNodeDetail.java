package com.shdd.cfs.dto.device.Optical;

import lombok.Data;

@Data
public class OpticalNodeDetail {
    private Integer id;
    private String name;
    private Integer used;
    private Integer capacity;
    private Integer status;
}
