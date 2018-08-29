package com.shdd.cfs.dto.device.optical;

import lombok.Data;

@Data
public class OpticalLibraryStorageSystemStoresDetail {
    private String name;
    private Double capacity;
    private Double used;
    private Double free;
    private Integer status;
}
