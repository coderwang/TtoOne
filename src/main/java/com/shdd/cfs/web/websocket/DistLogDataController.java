/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.websocket;

import com.shdd.cfs.dto.log.JournalInfo;
import com.shdd.cfs.utils.json.HttpRequest;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
    @MessageMapping("/dist_warning_log")
    @SendTo("/log/dist_warning_log")
    public JournalInfo GetLogData() {

        //访问下级分布式系统接口api/monitor/clusters/cpu/10/a/获取cpu信息
        HttpRequest httpRequest = new HttpRequest();

        String result = httpRequest.sendPost("http://192.168.1.32:8000/api/logs/level", "level:error");
        JSONArray logArray = JSONArray.fromObject(result);
        JSONObject logObject = logArray.getJSONObject(0);



        JournalInfo journalInfo = new JournalInfo();

        journalInfo.setType("distribute");
        journalInfo.setTime(logObject.getString("created"));
        journalInfo.setContent(logObject.getString("description"));

        return journalInfo;
    }

    @Scheduled(cron = "0/2 * * * * ? ")//每两秒触发
    public void publishUpdates() {
        JournalInfo journalInfo = new JournalInfo();

        //向下级系统通信，并获取指定数据信息，进行数据填充
        journalInfo.setType("disk");
        journalInfo.setTime("TimeStamp xxx");
        journalInfo.setContent("This is a test warning log from disk.");

        template.convertAndSend("/log/dist_warning_log", journalInfo);
    }
}
