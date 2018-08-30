/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.cdstorage;

import com.shdd.cfs.dto.device.distribute.NodeInfoDetail;
import com.shdd.cfs.dto.device.optical.OpticalNodeDetail;
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
public class CDAllDisksDetail {
    /**
     * @param page_num
     * @param count
     * @return
     */
    @GetMapping(value = "api/dashboard/disk/disks")
    @ApiOperation(value = "获取光盘库存储系统中所有光盘的详细信息", notes = "获取光盘库存储系统中所有光盘的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "page_num", value = "翻页页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "count", value = "每页所含最大条目数", required = true)
    })
    public JSONObject GetCDAllDisksDetailInfo(String page_num, String count) {
        JSONObject jarrary = new JSONObject();
        NodeInfoDetail[] arrdtail = new NodeInfoDetail[1];

        arrdtail[0] = new NodeInfoDetail();

        arrdtail[0].setId(1);
        arrdtail[0].setCapacity(50.0);
        arrdtail[0].setHostname("Node199");
        arrdtail[0].setName("xx");
        arrdtail[0].setUsed(67.99);
        arrdtail[0].setStatus(1);

        jarrary.accumulate("disk", arrdtail);
        return jarrary;
    }
}
