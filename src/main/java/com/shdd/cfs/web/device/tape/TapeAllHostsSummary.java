/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.optical.OpticalSystemInfoDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TapeAllHostsSummary {
    /**
     * 获取磁带库存储系统概况
     *
     * @param val
     * @return
     */
    @GetMapping(value = "api/dashboard/tape/hosts")
    @ApiOperation(value = "获取磁带库存储系统概况", notes = "获取磁带库存储系统中所有服务器节点的概要信息")

    public JSONObject TapeLibraryInfo(String val) {
        JSONObject Jarrary = new JSONObject();
        OpticalSystemInfoDetail[] tapearrary = new OpticalSystemInfoDetail[1];
        tapearrary[0] = new OpticalSystemInfoDetail();
        tapearrary[0].setCpuCount(2);
        tapearrary[0].setCpuType("phytium");
        tapearrary[0].setHardDiskCount(7);
        tapearrary[0].setMemCapacity(342.2);
        tapearrary[0].setName("node111");
        tapearrary[0].setStatus(1);

        Jarrary.accumulate("tape", tapearrary);
        return Jarrary;
    }
}
