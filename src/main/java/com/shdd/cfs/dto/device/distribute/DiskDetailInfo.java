package com.shdd.cfs.dto.device.distribute;

import lombok.Data;

@Data
public class DiskDetailInfo {
    private Integer id;
    private String name;
    private String hostname;
    private String used;
    private String capacity;
    private Integer status;
}
