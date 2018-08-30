/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.distribute.HostNodeInfoDetail;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
/**
 * @author: wangpeng
 * @version: 1.0 2018/8/29
 */
public class TapeSpecificPoolDisksDetail {
    /**
     * @param poolid
     * @param page_num
     * @param count
     * @return
     */
    @GetMapping(value = "api/dashboard/tape/pool/disks")
    @ApiOperation(value = "主机信息状态下获取分布式存储系统节点详细概况", notes = "分布式系统指定存储池中的磁盘详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "poolid", value = "磁带库磁带组ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "page_num", value = "翻页页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "count", value = "每页所含最大条目数", required = true)
    })

    public JSONObject GetTapeSpecificPoolDisksDetailInfo(String poolid, String page_num, String count) {
        JSONObject jarrary = new JSONObject();
        HostNodeInfoDetail[] arrdtail = new HostNodeInfoDetail[2];

        arrdtail[0] = new HostNodeInfoDetail();
        arrdtail[1] = new HostNodeInfoDetail();

        arrdtail[0].setId(1);
        arrdtail[0].setCapacity(50.0);
        arrdtail[0].setHostname("Node199");
        arrdtail[0].setName("xx");
        arrdtail[0].setUsed(67.99);
        arrdtail[0].setStatus(1);
        arrdtail[1].setId(2);
        arrdtail[1].setCapacity(51.0);
        arrdtail[1].setHostname("Node201");
        arrdtail[1].setName("xx");
        arrdtail[1].setUsed(69.99);
        arrdtail[1].setStatus(0);


        jarrary.accumulate("disk", arrdtail);
        return jarrary;
    }
}

