/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.websocket;

import com.shdd.cfs.dto.log.JournalInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/29
 */
@Controller
public class DistLogDataController {
    /**
     * @return
     */
    @ApiOperation(value = "获取分布式存储系统告警详细信息", notes = "获取分布式存储系统告警详细信息")
    @MessageMapping("/distWarningLog")
    @SendTo("/topic/distWarningLog")
    public JournalInfo GetLogData() {
        JournalInfo journalInfo = new JournalInfo();

        journalInfo.setType("distribute");
        journalInfo.setTime("TimeStamp xxx");
        journalInfo.setContent("This is a test warning log from distribute.");

        return journalInfo;
    }
}
