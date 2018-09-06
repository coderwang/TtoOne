/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.cdstorage;

import com.shdd.cfs.dto.device.distribute.PoolGeneralOverviewDetail;
import com.shdd.cfs.utils.json.OpticalJsonHandle;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Slf4j
/**
 * @author: wangpeng
 * @version: 1.0 2018/8/29
 */
public class CDAllPoolsSummary {
    /**
     * @return
     */
    @GetMapping(value = "api/dashboard/disk/pools")
    @ApiOperation(value = "获取光盘库存储系统中所有存储池的详细信息", notes = "获取光盘库存储系统中所有光盘匣的详细信息")
    public JSONObject GetCDAllPoolsSummaryInfo() {
        JSONObject storagePool = new JSONObject();
        //与下级光盘库系统通讯，获取需求数据
        JSONArray boxList = OpticalJsonHandle.cdboxlist();
        ArrayList<PoolGeneralOverviewDetail> boxsArray = new ArrayList<>();
        for (int i = 0; i < boxList.size(); i++) {
            PoolGeneralOverviewDetail boxs = new PoolGeneralOverviewDetail();
            boxs.setId(boxList.getJSONObject(i).getString("cdboxid"));
            boxs.setName(boxList.getJSONObject(i).getString("label"));
            boxs.setCapacity(boxList.getJSONObject(i).getDouble("cdboxtotalcapacity"));
            boxs.setUsed(boxList.getJSONObject(i).getDouble("cdboxusedcapacity"));
            boxs.setFree(boxList.getJSONObject(i).getDouble("cdboxfreecapacity"));
            boxsArray.add(boxs);
        }
        storagePool.accumulate("poolCount", boxList.size());
        storagePool.accumulate("poollist", boxsArray);
        return storagePool;
    }
}
