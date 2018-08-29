/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.websocket;

import com.shdd.cfs.dto.message.DistSystemData;
import io.swagger.annotations.ApiOperation;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/29
 */
@Controller
public class DistColonySystemDataController {
    /**
     * @return
     * @throws InterruptedException
     */
    @ApiOperation(value = "获取分布式存储主机cpu/内存/带宽使用情况", notes = "获取分布式集群中所有节点的信息，取平均值")
    @MessageMapping("/distColonySystemInfo")
    @SendTo("/topic/distColonySystemInfo")
    public DistSystemData getDistColonySystemData() throws InterruptedException {
        Thread.sleep(1000); // simulated delay

        //此处需求CPU，内存，带宽的平均值
        DistSystemData distSystemData = new DistSystemData();
        distSystemData.setCpu(95.3);
        distSystemData.setRam(23.45);
        distSystemData.setBw(78.2);

        return distSystemData;
    }
}
