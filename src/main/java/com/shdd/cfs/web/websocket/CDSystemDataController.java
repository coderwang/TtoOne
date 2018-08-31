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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Random;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/28
 */
@Controller
@Slf4j
public class CDSystemDataController {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 获取光盘库存储cpu/内存/带宽使用情况
     *
     * @param helloMessage
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取光盘库存储cpu/内存/带宽使用情况")
    @MessageMapping("/cddiskSystemInfo")
    @SendTo("/device/cddiskSystemInfo")
    public DistSystemData getCddiskSystemData(HelloMessage helloMessage) throws Exception {
        Thread.sleep(1000); // simulated delay

        DistSystemData distSystemData = new DistSystemData();
        distSystemData.setCpu(95.3);
        distSystemData.setRam(23.45);
        distSystemData.setBw(78.2);

        return distSystemData;
    }


    @Scheduled(cron = "0/2 * * * * ? ")//每两秒触发
    public void publishUpdates() {
        Random random = new Random();
        DistSystemData distSystemData = new DistSystemData();

        //向下级系统通信，并获取指定数据信息，进行数据填充
        distSystemData.setCpu(random.nextDouble());
        distSystemData.setRam(random.nextDouble());
        distSystemData.setBw(random.nextDouble());
        distSystemData.setHelloMessage("定时任务");

        template.convertAndSend("/device/cddiskSystemInfo", distSystemData);
    }
}
