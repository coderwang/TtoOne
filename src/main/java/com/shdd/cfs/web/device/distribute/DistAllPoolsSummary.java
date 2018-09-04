/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.dto.device.distribute.PoolGeneralOverviewDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DistAllPoolsSummary {
    /**
     * 获取分布式存储系统存储池总体概况
     *
     * @param value
     * @return
     */
    @GetMapping(value = "api/dashboard/distribute/pools")
    @ApiOperation(value = "获取分布式存储系统存储池总体概况", notes = "分布式存储系统所有存储池总体概况")

    public JSONObject PoolGeneralInfo(String value) {

        JSONObject storagePool = new JSONObject();
        PoolGeneralOverviewDetail[] jarrary = new PoolGeneralOverviewDetail[2];

        jarrary[0] = new PoolGeneralOverviewDetail();
        jarrary[1] = new PoolGeneralOverviewDetail();
        jarrary[0].setId("1");
        jarrary[0].setName("长期保存库");
        jarrary[1].setId("1");
        jarrary[1].setName("长期保存库2");

        storagePool.accumulate("poolCount", "3");
        storagePool.accumulate("status", 1);
        storagePool.accumulate("poolName", jarrary);
        return storagePool;
    }
}
