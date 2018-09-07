/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.websocket;

import com.shdd.cfs.dto.log.JournalInfo;
import com.shdd.cfs.utils.xml.iamp.HttpResult;
import com.shdd.cfs.utils.xml.iamp.IampRequest;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/29
 */
@Controller
@Slf4j
public class TapeLogDataController {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * @return
     */
    @Autowired
    private IampRequest iampRequest;

    @ApiOperation(value = "获取磁带库存储系统告警详细信息", notes = "获取磁带库存储系统告警详细信息")
    @MessageMapping("/tape_warning_log")
    @SendTo("/log/tape_warning_log")
    public JSONObject GetTapeLogData() throws DocumentException {
        //定义返回对象
        JSONObject rstObject = new JSONObject();
        JSONArray logInfoArray = new JSONArray();
        //与下级磁带库通信
        String session = iampRequest.SessionKey();
        HttpResult logsList = iampRequest.inquiry_task_lists(session);
        ArrayList<String> logInfoList = iampRequest.get_tapes_id(logsList);
        for (String logInfo : logInfoList) {
            JournalInfo journalInfo = new JournalInfo();
            //获取需求数据
            Map<String, String> time = iampRequest.task_time(logsList, logInfo);
            String message = iampRequest.task_message(logsList, logInfo);
            //处理数据
            journalInfo.setType("tape");
            journalInfo.setTime(time.get("create"));
            journalInfo.setContent(message);
            //将数据存入缓存中
            logInfoArray.add(journalInfo);
        }
        //打包数据
        rstObject.accumulate("journal", logInfoArray);
        return rstObject;
    }

    //@Scheduled(cron = "0/2 * * * * ? ")//每两秒触发
    public void publishUpdates() throws DocumentException {
        JournalInfo journalInfo = new JournalInfo();

        //向下级系统通信，并获取指定数据信息，进行数据填充
        journalInfo.setType("tape");
        journalInfo.setTime("TimeStamp xxx");
        journalInfo.setContent("This is a test warning log from tape.");
        template.convertAndSend("/log/tape_warning_log", journalInfo);
    }
}
