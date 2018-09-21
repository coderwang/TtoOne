/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.websocket;

import com.shdd.cfs.config.DateConfig;
import com.shdd.cfs.dto.message.DistSystemData;
import com.shdd.cfs.utils.json.HttpRequest;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Random;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/29
 */
@Controller
@Slf4j
public class DistColonySystemDataController {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * @return
     * @throws InterruptedException
     */

    @Autowired
    private DateConfig config;
    @ApiOperation(value = "获取分布式存储主机cpu/内存/带宽使用情况", notes = "获取分布式集群中所有节点的信息，取平均值")
    @MessageMapping("/dist_colony_sys_info")
    @SendTo("/device/dist_colony_sys_info")
    public DistSystemData getDistColonySystemData() throws InterruptedException {
        Thread.sleep(1000); // simulated delay

        //访问下级分布式系统接口api/monitor/clusters/cpu/10/a/获取cpu信息
        HttpRequest httpRequest = new HttpRequest();
        String urlcpu = config.getDistributeUrl() + "api/monitor/clusters/cpu/10/a/";
        String result = httpRequest.sendGet(urlcpu, " ");
        JSONObject cpuObject = JSONObject.fromObject(result);
        JSONArray cpuArray = cpuObject.getJSONArray("cpu");

        //访问下级分布式系统接口api/monitor/clusters/mem/10/a/获取mem信息
        String urlmem = config.getDistributeUrl() + "api/monitor/clusters/mem/10/a/";
        result = httpRequest.sendGet(urlmem, " ");
        JSONObject memObject = JSONObject.fromObject(result);
        JSONArray memArray = memObject.getJSONArray("mem");

        //访问下级分布式系统接口api/monitor/clusters/bandwidth/10/a/获取bandwidth信息
        String urlbandwidth = config.getDistributeUrl() + "api/monitor/clusters/bandwidth/10/a/";
        result = httpRequest.sendGet(urlbandwidth, " ");
        JSONObject bwObject = JSONObject.fromObject(result);
        JSONArray bwArray = bwObject.getJSONArray("bandwidth_write");

        //此处需求CPU，内存，带宽的平均值
        DistSystemData distSystemData = new DistSystemData();

        distSystemData.setCpu(Double.parseDouble(cpuArray.getJSONObject(0).getString("value")));
        distSystemData.setRam(Double.parseDouble(memArray.getJSONObject(0).getString("value")));
        distSystemData.setBw(Double.parseDouble(bwArray.getJSONObject(0).getString("value")));

        return distSystemData;
    }

    //@Scheduled(cron = "0/2 * * * * ? ")//每两秒触发
    public void publishUpdates() {
        Random random = new Random();
        DistSystemData distSystemData = new DistSystemData();

        //向下级系统通信，并获取指定数据信息，进行数据填充
        distSystemData.setCpu(random.nextDouble());
        distSystemData.setRam(random.nextDouble());
        distSystemData.setBw(random.nextDouble());
        distSystemData.setHelloMessage("定时任务");

        template.convertAndSend("/device/dist_colony_sys_info", distSystemData);
    }
}
