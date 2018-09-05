/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.dto.device.distribute.PoolGeneralOverviewDetail;
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

        //访问下级分布式系统接口api/volumes/
        HttpRequest httpRequest = new HttpRequest();

        String result = httpRequest.sendGet("http://192.168.1.32:8000/api/volumes/", " ");
        JSONArray volumeArray = JSONArray.fromObject(result);

        //存储池详细信息
        JSONObject volumeOject;
        int volumeCount = volumeArray.size();
        ArrayList volumeList = new ArrayList();

        for (int i = 0; i < volumeCount; i++) {
            volumeOject = volumeArray.getJSONObject(i);
            PoolGeneralOverviewDetail volumeInfo = new PoolGeneralOverviewDetail();

            volumeInfo.setName(volumeOject.getString("vol_name"));
            volumeInfo.setId(volumeOject.getString("vol_id"));

            volumeList.add(volumeInfo);
        }

        //数据打包
        JSONObject storagePool = new JSONObject();

        storagePool.accumulate("poolCount", volumeCount);
        storagePool.accumulate("poolName", volumeList);

        return storagePool;
    }
}
