/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.dto.device.distribute.HostInfoDetail;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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

        JSONObject hostInfo = new JSONObject();
        HostInfoDetail[] arrdetail = new HostInfoDetail[1];
        arrdetail[0] = new HostInfoDetail();
        arrdetail[0].setName("node01");
        arrdetail[0].setCpuType("phytium");
        arrdetail[0].setCpuCount(2);
        arrdetail[0].setMemCapacity(20.5);
        arrdetail[0].setHardDiskCount(12);
        arrdetail[0].setStatus(1);

        hostInfo.accumulate("host", arrdetail);
        return hostInfo;
    }
}
