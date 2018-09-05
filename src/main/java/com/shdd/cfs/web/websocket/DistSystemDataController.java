/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.websocket;

import com.shdd.cfs.dto.message.DistSystemData;
import com.shdd.cfs.dto.message.HelloMessage;
import com.shdd.cfs.utils.storage.Store;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Random;

/**
 * @author: xphi
 * @version: 1.0 2018/8/28
 */
@Controller
@Slf4j
public class DistSystemDataController {

    @Autowired
    private Store store;

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 获取分布式存储主机cpu/内存/带宽使用情况
     *
     * @param key
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取分布式存储主机cpu/内存/带宽使用情况")

    //@MessageMapping("/hello")
    @PostMapping("dist_sys_data/{deviceid}")
    @SendTo("/device/dist_sys_data")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string",
                    name = "key", value = "存储的键", required = true),
            @ApiImplicitParam(paramType = "form", dataType = "string",
                    name = "value", value = "存储的值", required = true)
    })
    public DistSystemData getDistSystemData(@PathVariable String key, String value) throws Exception {
        Thread.sleep(1000); // simulated delay

        log.info("d is " + key);
        log.info("e is " + value);

        HelloMessage helloMessage = new HelloMessage();

        DistSystemData distSystemData = new DistSystemData();
        distSystemData.setCpu(95.3);
        distSystemData.setRam(23.45);
        distSystemData.setBw(78.2);
        distSystemData.setHelloMessage(helloMessage.getMessage());

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

        template.convertAndSend("/device/dist_sys_data", distSystemData);
    }

}
