/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.websocket;

import com.shdd.cfs.dto.message.HelloMessage;
import com.shdd.cfs.dto.message.WarningInfo;
import com.shdd.cfs.utils.xml.iamp.HttpResult;
import com.shdd.cfs.utils.xml.iamp.IampRequest;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/28
 */
@Controller
@Slf4j
public class WarningDataController {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 获取告警信息
     *
     * @param helloMessage
     * @return
     * @throws Exception
     */
    @Autowired
    IampRequest iampRequest;
    @ApiOperation(value = "获取告警信息")
    @MessageMapping("/warning_info")
    @SendTo("/log/warning_info")
    public JSONObject getWarningDataData(HelloMessage helloMessage) throws Exception {
        Thread.sleep(1000); // simulated delay
        String session = iampRequest.SessionKey();
        HttpResult massagelist = iampRequest.inquiry_task_warn(session);
        JSONObject warningObj = new JSONObject();
        JSONArray warningArr = new JSONArray();
        WarningInfo[] warningInfo = new WarningInfo[3];
        warningInfo[0] = new WarningInfo();
        warningInfo[0].setName("distribute");
        warningInfo[0].setMessage(iampRequest.get_task_warn(massagelist));
        warningInfo[1] = new WarningInfo();
        warningInfo[1].setName("tape");
        warningInfo[1].setMessage(1);
        warningInfo[2] = new WarningInfo();
        warningInfo[2].setName("optical");
        warningInfo[2].setMessage(1);
        warningArr.add(warningInfo);
        warningObj.accumulate("status",warningArr);
        return warningObj;
    }

    //@Scheduled(cron = "0/2 * * * * ? ")//每两秒触发
    public void publishUpdates() {
        WarningInfo warningInfo = new WarningInfo();

        //向下级系统通信，并获取指定数据信息，进行数据填充
        warningInfo.setName("distribute");
        warningInfo.setMessage(1);

        template.convertAndSend("/log/warning_info", warningInfo);
    }
}
