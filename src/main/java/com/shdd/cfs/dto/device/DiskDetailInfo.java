package com.shdd.cfs.dto.device;

import lombok.Data;

@Data
public class DiskDetailInfo {
    /**
     * 磁盘/光盘/磁带设备id
     */
    private Integer id;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 设备所属主机名称
     */
    private String hostname;
    /**
     * 已使用容量
     */
    private String used;
    /**
     * 总容量
     */
    private String capacity;
    /**
     * 设备在线状态
     */
    private Integer status;
}
