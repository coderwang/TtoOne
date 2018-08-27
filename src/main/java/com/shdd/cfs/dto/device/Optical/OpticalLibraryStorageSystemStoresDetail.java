package com.shdd.cfs.dto.device.Optical;

import lombok.Data;

@Data
public class OpticalLibraryStorageSystemStoresDetail {
    private String name;
    private Integer  capacity;
    private Integer used;
    private Integer free;
    private Integer status;
}
