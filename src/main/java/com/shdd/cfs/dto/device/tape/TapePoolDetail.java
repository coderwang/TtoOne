package com.shdd.cfs.dto.device.tape;

import lombok.Data;

@Data
public class TapePoolDetail {
    private String name;
    private Double capacity;
    private Double used;
    private Double free;
}
