/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.websocket;

import com.shdd.cfs.dto.log.JournalInfo;
import com.shdd.cfs.utils.json.OpticalJsonHandle;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/29
 */
@Controller
@Slf4j
public class CDLogDataController {
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * @return
     */
    @ApiOperation(value = "获取光盘库存储系统告警详细信息", notes = "获取光盘库存储系统告警详细信息")
    @MessageMapping("/disk_warning_log")
    @SendTo("/log/disk_warning_log")
    public JSONObject GetCDLogData() {

        JSONObject Jobject = new JSONObject();
        ArrayList<JournalInfo> messarrary = new ArrayList<>();
        JSONArray array= OpticalJsonHandle.getErrMessage("2018-07-20");
        for(int i = 0 ; i < array.size(); i++){
            System.out.println(array.getJSONObject(i).get("message"));
            JournalInfo journalInfo = new JournalInfo();
            journalInfo.setType("disk");
            journalInfo.setTime("2018-07-20");
            journalInfo.setContent(array.getJSONObject(i).get("message").toString());
            messarrary.add(journalInfo);
        }
        Jobject.accumulate("journal",messarrary);
        return Jobject;
    }


    //@Scheduled(cron = "0/2 * * * * ? ")//每两秒触发
    public void publishUpdates() {
        JournalInfo journalInfo = new JournalInfo();

        //向下级系统通信，并获取指定数据信息，进行数据填充
        journalInfo.setType("disk");
        journalInfo.setTime("TimeStamp xxx");
        journalInfo.setContent("This is a test warning log from disk.");

        template.convertAndSend("/log/disk_warning_log", journalInfo);
    }
}
