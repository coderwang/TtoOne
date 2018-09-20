/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.config.DateConfig;
import com.shdd.cfs.dto.device.distribute.DistHostDetailInfo;
import com.shdd.cfs.utils.json.HttpRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Slf4j
public class DistSpecificHostDetail {

    /**
     * 获取分布式存储系统主机概况
     *
     * @param value
     * @return
     */
    @Autowired
    private DateConfig config;
    @GetMapping(value = "api/device/distribute/host")
    @ApiOperation(value = "获取分布式存储系统主机概况", notes = "获取分布式存储系统指定服务器节点的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "deviceId", value = "指定分布式存储系统服务器节点ID", required = true)
    })

    public JSONObject GetHostfDistribute(String value, int deviceId) {

        //访问下级分布式系统接口api/hosts/host_id
        HttpRequest httpRequest = new HttpRequest();
        String urlhost = config.getDistributeUrl() + "api/hosts/";
        String result = httpRequest.sendGet(urlhost + deviceId, " ");
        JSONObject hostObject = JSONObject.fromObject(result);
        String urlnode = config.getDistributeUrl() + "api/nodes/";
        result = httpRequest.sendGet(urlnode + deviceId, " ");
        JSONObject nodeObject = JSONObject.fromObject(result);

        DistHostDetailInfo hostDetailInfo = new DistHostDetailInfo();

        ArrayList hostList = new ArrayList();

        //host节点对象信息
        JSONArray hostArray;
        int hostCount = 0;

        //cpu对象信息
        JSONArray cpuArray;
        JSONObject cpuObject;
        int cpuCount = 0;
        String cpuType = null;

        //mem对象信息
        JSONArray memArray;
        JSONObject memObject;
        int memCount = 0;
        Double memTotal = 0.0;

        //disk磁盘信息
        JSONArray diskArray;
        int diskCount;

        //host主机名信息
        hostDetailInfo.setName(hostObject.getString("host_name"));

        //host主机状态
        if (nodeObject.getString("node_state").equalsIgnoreCase("Connected")) {
            hostDetailInfo.setStatus(1);
        } else {
            hostDetailInfo.setStatus(0);
        }

        //cpu信息
        cpuArray = hostObject.getJSONArray("cpus");
        cpuCount = cpuArray.size();
        for (int j = 0; j < cpuCount; j++) {
            cpuObject = cpuArray.getJSONObject(j);

            //获取cpu类型
            cpuType = cpuObject.getString("model_name");
        }
        hostDetailInfo.setCpucount(cpuCount);
        hostDetailInfo.setCpu_type(cpuType);

        //内存信息
        memArray = hostObject.getJSONArray("memorys");
        memCount = memArray.size();
        for (int j = 0; j < memCount; j++) {
            memObject = memArray.getJSONObject(j);

            memTotal += Double.parseDouble(memObject.getString("total"));
        }
        hostDetailInfo.setMem_capacity(memTotal);

        //disk磁盘信息
        diskArray = hostObject.getJSONArray("disks");
        diskCount = diskArray.size();
        hostDetailInfo.setDisk_count(diskCount);

        //将处理后的数据添加到链表中
        hostList.add(hostDetailInfo);


        //数据打包发送单个主机的详细情况
        JSONObject hostInfo = new JSONObject();

        hostInfo.accumulate("host", hostList);
        return hostInfo;
    }
}
