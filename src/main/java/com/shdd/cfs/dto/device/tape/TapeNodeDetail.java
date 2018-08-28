package com.shdd.cfs.dto.device.tape;

import lombok.Data;

@Data
public class TapeNodeDetail {
    private Integer id;
    private String name;
    private Integer used;
    private Integer capacity;
    private Integer status;
}
