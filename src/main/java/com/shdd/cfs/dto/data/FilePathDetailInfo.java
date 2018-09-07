package com.shdd.cfs.dto.data;

import lombok.Data;

@Data
public class FilePathDetailInfo {
    /**
     * 文件ID
     */
    private Integer id;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件类型，后缀名
     */
    private String type;
}
