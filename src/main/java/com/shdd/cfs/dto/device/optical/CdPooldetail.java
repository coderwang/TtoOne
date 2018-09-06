package com.shdd.cfs.dto.device.optical;

import lombok.Data;

@Data
public class CdPooldetail {
    private String name;
    private Double capacity;
    private Double used;
    private Double free;
}
