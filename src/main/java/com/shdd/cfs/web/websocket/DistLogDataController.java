/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.websocket;

import com.shdd.cfs.dto.log.JournalInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/29
 */
@Controller
@Slf4j
public class DistLogDataController {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * @return
     */
    @ApiOperation(value = "获取分布式存储系统告警详细信息", notes = "获取分布式存储系统告警详细信息")
    @MessageMapping("/distWarningLog")
    @SendTo("/ws/distWarningLog")
    public JournalInfo GetLogData() {
        JournalInfo journalInfo = new JournalInfo();

        journalInfo.setType("distribute");
        journalInfo.setTime("TimeStamp xxx");
        journalInfo.setContent("This is a test warning log from distribute.");

        return journalInfo;
    }

    @Scheduled(cron = "0/2 * * * * ? ")//每两秒触发
    public void publishUpdates() {
        JournalInfo journalInfo = new JournalInfo();

        //向下级系统通信，并获取指定数据信息，进行数据填充
        journalInfo.setType("disk");
        journalInfo.setTime("TimeStamp xxx");
        journalInfo.setContent("This is a test warning log from disk.");

        log.info("定时任务4……");

        template.convertAndSend("/ws/distWarningLog", journalInfo);
    }
}
