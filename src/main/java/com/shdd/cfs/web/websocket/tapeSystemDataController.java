/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.websocket;

import com.shdd.cfs.dto.message.DistSystemData;
import com.shdd.cfs.dto.message.HelloMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/28
 */
@Controller
public class tapeSystemDataController {
    /**
     * 获取磁带库存储cpu/内存/带宽使用情况
     *
     * @param helloMessage
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取磁带库存储cpu/内存/带宽使用情况")
    @MessageMapping("/tapeSystemInfo")
    @SendTo("/topic/tapeSystemInfo")
    public DistSystemData getTapeSystemData(HelloMessage helloMessage) throws Exception {
        Thread.sleep(1000); // simulated delay

        DistSystemData distSystemData = new DistSystemData();
        distSystemData.setCpu(95.3);
        distSystemData.setRam(23.45);
        distSystemData.setBw(78.2);

        return distSystemData;
    }

}
