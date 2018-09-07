package com.shdd.cfs.dto.log;

import lombok.Data;

@Data
public class JournalInfo {
    /**
     * 下级系统类型，取值为distribute,tape,disk
     */
    String type;
    /**
     * 日志时间戳信息
     */
    String time;
    /**
     * 日志内容信息
     */
    String content;
}
