package com.shdd.cfs.dto.device.Tape;

import lombok.Data;

@Data
public class TapeLibraryStorageSystemStoresDetail {
    private String name;
    private Integer  capacity;
    private Integer used;
    private Integer free;
    private Integer status;
}
