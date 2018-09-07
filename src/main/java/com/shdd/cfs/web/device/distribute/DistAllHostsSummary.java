/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.dto.device.distribute.DistHostDetailInfo;
import com.shdd.cfs.utils.json.HttpRequest;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Slf4j
public class DistAllHostsSummary {
    /**
     * 获取分布式存储系统集群情况
     *
     * @param value
     * @return
     */
    @GetMapping(value = "api/device/distribute/hosts")
    @ApiOperation(value = "获取分布式存储系统集群情况", notes = "分布式存储系统所有主机概况")

    public JSONObject GetInfoOfDistribute(String value) {

        //访问下级分布式系统接口api/nodes/
        HttpRequest httpRequest = new HttpRequest();

        String result = httpRequest.sendGet("http://192.168.1.32:8000/api/nodes", " ");
        JSONArray nodesArray = JSONArray.fromObject(result);

        //所有节点数目
        int nodesCount = nodesArray.size();
        //node节点对象信息
        JSONObject nodeObject;
        ArrayList hostList = new ArrayList();

        //host节点对象信息
        JSONObject hostObject;
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

        for (int i = 0; i < nodesCount; i++) {
            //根据node_id通信获取到的node详细信息
            nodeObject = nodesArray.getJSONObject(i);
            DistHostDetailInfo hostDetailInfo = new DistHostDetailInfo();

            //node_id，host_id
            hostDetailInfo.setNode_id(Integer.parseInt(nodeObject.getString("node_id")));
            hostDetailInfo.setHost_id(Integer.parseInt(nodeObject.getString("host_id")));
            //处理node节点状态信息
            if (nodeObject.getString("node_state") == "Connected") {
                hostDetailInfo.setStatus(1);
            } else {
                hostDetailInfo.setStatus(0);
            }

            //根据host_id通信获取详细信息，api/hosts/host_id
            result = httpRequest.sendGet("http://192.168.1.32:8000/api/hosts/" + nodeObject.getString("host_id"), " ");
            //处理根据host_id获取到的host详细信息
            hostObject = JSONObject.fromObject(result);
            //host主机名信息
            hostDetailInfo.setName(hostObject.getString("host_name"));

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
        }

        //数据打包处理
        JSONObject distributeStorageInfo = new JSONObject();

        distributeStorageInfo.accumulate("colonyCount", nodesCount);
        distributeStorageInfo.accumulate("colony", hostList);
        return distributeStorageInfo;
    }
}
