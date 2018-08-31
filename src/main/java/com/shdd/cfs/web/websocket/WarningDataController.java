/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.websocket;

import com.shdd.cfs.dto.message.HelloMessage;
import com.shdd.cfs.dto.message.WarningInfo;
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
    @ApiOperation(value = "获取告警信息")
    @MessageMapping("/WarningInfo")
    @SendTo("/log/WarningInfo")
    public WarningInfo getWarningDataData(HelloMessage helloMessage) throws Exception {
        Thread.sleep(1000); // simulated delay

        WarningInfo warningInfo = new WarningInfo();
        warningInfo.setName("distribute");
        warningInfo.setMessage(1);

        return warningInfo;
    }

    @Scheduled(cron = "0/2 * * * * ? ")//每两秒触发
    public void publishUpdates() {
        WarningInfo warningInfo = new WarningInfo();

        //向下级系统通信，并获取指定数据信息，进行数据填充
        warningInfo.setName("distribute");
        warningInfo.setMessage(1);

        template.convertAndSend("/log/WarningInfo", warningInfo);
    }
}
