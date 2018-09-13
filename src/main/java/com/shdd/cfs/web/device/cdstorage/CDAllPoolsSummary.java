/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.cdstorage;

import com.shdd.cfs.dto.device.PoolSummaryInfo;
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
        ArrayList<Integer> onLineBox = OpticalJsonHandle.getOlineBoxNum();
        ArrayList<PoolSummaryInfo> boxsArray = new ArrayList<>();
        for (int i = 0; i < onLineBox.size(); i++) {
                int j = onLineBox.get(i);
                JSONObject boxInfo = OpticalJsonHandle.boxInfoFromCdBoxList(j);
                PoolSummaryInfo boxs = new PoolSummaryInfo();
                boxs.setId(boxInfo.getString("cdboxid"));
                boxs.setName(boxInfo.getString("label"));
                boxs.setCapacity(OpticalJsonHandle.singleBoxCapacity());
                boxs.setUsed(boxInfo.getDouble("cdboxusedcapacity"));
                boxs.setFree(boxInfo.getDouble("cdboxfreecapacity"));
                boxsArray.add(boxs);
        }
        storagePool.accumulate("poolCount", onLineBox.size());
        storagePool.accumulate("poollist", boxsArray);
        return storagePool;
    }
}
