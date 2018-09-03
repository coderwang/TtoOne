/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.dto.device.distribute.HostDetailInfo;
import com.shdd.cfs.utils.json.DistributeUrlHandle;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DistSpecificHostDetail {

    /**
     * 获取分布式存储系统主机概况
     *
     * @param value
     * @return
     */
    @GetMapping(value = "api/device/distribute/host")
    @ApiOperation(value = "获取分布式存储系统主机概况" , notes = "获取分布式存储系统指定服务器节点的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "deviceId", value = "指定分布式存储系统服务器节点ID", required = true)
    })

    public JSONObject GetHostfDistribute(String value) {
        //向分布式厂家发送获取单个主机的信息的请求

        //获取分布式集群中单个主机信息
        String baseurl = "";
        JSONObject hostinfo = DistributeUrlHandle.Nodeinfo();
        String hostname = hostinfo.getString("host_name");
        JSONArray cpucount = hostinfo.getJSONArray("cpus");
        JSONArray memaryinfo = hostinfo.getJSONArray("memorys");
        JSONArray diskinfo = hostinfo.getJSONArray("disks");
        //发送单个主机的详细情况
        JSONObject hostInfo = new JSONObject();
        HostDetailInfo[] arrdetail = new HostDetailInfo[1];
        arrdetail[0] = new HostDetailInfo();
        arrdetail[0].setName(hostname);
        arrdetail[0].setCpu_type("phytium");
        arrdetail[0].setCpucount(cpucount.size());
        arrdetail[0].setMem_capacity((Double) memaryinfo.getJSONObject(0).get("total"));
        arrdetail[0].setDisk_count(diskinfo.size());
        arrdetail[0].setStatus(1);

        hostInfo.accumulate("host", arrdetail);
        return hostInfo;
    }
}
