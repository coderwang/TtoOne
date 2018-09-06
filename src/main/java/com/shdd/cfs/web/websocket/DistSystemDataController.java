/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.websocket;

import com.shdd.cfs.dto.message.DistSystemData;
import com.shdd.cfs.utils.json.HttpRequest;
import com.shdd.cfs.utils.storage.Store;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author: xphi
 * @version: 1.0 2018/8/28
 */
@Controller
@RestController
@Slf4j
public class DistSystemDataController {

    @Autowired
    private Store store;

    //@Autowired
    //private SimpMessagingTemplate template;

    /**
     * 获取分布式存储主机cpu/内存/带宽使用情况
     *
     * @param deviceid
     * @param value
     * @return
     * @throws Exception
     */
    @PostMapping("device/dist_sys_data/{deviceid}")
    @ApiOperation(value = "获取分布式存储主机cpu/内存/带宽使用情况")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string",
                    name = "deviceid", value = "存储的键", required = true),
            @ApiImplicitParam(paramType = "form", dataType = "string",
                    name = "value", value = "存储的值", required = true)
    })
    public JSONObject getDistSystemData(@PathVariable String deviceid, String value) throws Exception {
        Thread.sleep(1000); // simulated delay

        DistSystemData distSystemData = GetDistributeSystemData(deviceid, value);

        JSONObject rstObject = new JSONObject();
        rstObject.accumulate("data", distSystemData);

        return rstObject;
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

        //template.convertAndSend("/device/dist_sys_data", distSystemData);
    }

    private DistSystemData GetDistributeSystemData(String deviceid, String value) {
        log.info("deviceid is " + deviceid);

        Boolean rst = store.set("distSysData", deviceid, value);
        DistSystemData distSystemData = new DistSystemData();

        //访问下级分布式系统接口api/hosts/host_id
        //api/monitor/clusters/cpu/10/a/
        HttpRequest httpRequest = new HttpRequest();

        //获取cpu信息
        String result = httpRequest.sendGet("http://192.168.1.32:8000/api/monitor/cpu/10/a/" + deviceid, " ");
        JSONObject cpuInfoObject = JSONObject.fromObject(result);

        //cpu信息
        JSONArray cpusArray = cpuInfoObject.getJSONArray("cpu");
        JSONObject cpuObject;
        Double cpuValue = 0.0;
        int cpuCount = cpusArray.size();

        if (cpuCount > 0) {
            for (int i = 0; i < cpuCount; i++) {
                cpuObject = cpusArray.getJSONObject(i);

                cpuValue += cpuObject.getDouble("value");
            }

            cpuValue /= cpuCount;
        }
        distSystemData.setCpu(cpuValue);

        //获取mem信息
        result = httpRequest.sendGet("http://192.168.1.32:8000/api/monitor/mem/10/a/" + deviceid, " ");
        JSONObject memInfoObject = JSONObject.fromObject(result);

        //cpu信息
        JSONArray memsArray = memInfoObject.getJSONArray("mem");
        JSONObject memObject;
        Double memValue = 0.0;
        int memCount = memsArray.size();

        if (memCount > 0) {
            for (int i = 0; i < memCount; i++) {
                memObject = memsArray.getJSONObject(i);

                memValue += memObject.getDouble("value");
            }

            memValue /= memCount;
        }
        distSystemData.setRam(memValue);

        //获取bandwidth信息
        result = httpRequest.sendGet("http://192.168.1.32:8000/api/monitor/bandwidth/10/a/h-" + deviceid, " ");
        JSONObject bandwidthInfoObject = JSONObject.fromObject(result);

        //cpu信息
        JSONArray bandwidthsArray = bandwidthInfoObject.getJSONArray("bandwidth_write");
        JSONObject bandwidthObject;
        Double bandwidthValue = 0.0;
        int bandwidthCount = bandwidthsArray.size();

        if (bandwidthCount > 0) {
            for (int i = 0; i < bandwidthCount; i++) {
                bandwidthObject = bandwidthsArray.getJSONObject(i);

                bandwidthValue += bandwidthObject.getDouble("value");
            }

            bandwidthValue /= bandwidthCount;
        }
        distSystemData.setBw(bandwidthValue);

        distSystemData.setHelloMessage("hostID is " + deviceid);

        return distSystemData;
    }

}
