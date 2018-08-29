/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.websocket;

import com.shdd.cfs.dto.message.DistSystemData;
import com.shdd.cfs.dto.message.HelloMessage;
import com.shdd.cfs.dto.message.WarningInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/28
 */
@Controller
public class warningDataController {

    /**
     * 获取告警信息
     *
     * @param helloMessage
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取告警信息")
    @MessageMapping("/WarningInfo")
    @SendTo("/topic/WarningInfo")
    public WarningInfo getWarningDataData(HelloMessage helloMessage) throws Exception {
        Thread.sleep(1000); // simulated delay

        WarningInfo warningInfo = new WarningInfo();
        warningInfo.setName("distribute");
        warningInfo.setMessage(1);

        return warningInfo;
    }
}
