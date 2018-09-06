/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.cdstorage;

import com.shdd.cfs.dto.device.optical.CdPooldetail;
import com.shdd.cfs.utils.json.OpticalJsonHandle;
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
public class CDSpecificPoolDetail {
    /**
     * 获取光盘库存储系统存储详细概况
     *
     * @param poolid
     * @return
     */
    @GetMapping(value = "api/dashboard/disk/pool")
    @ApiOperation(value = "获取光盘库存储系统存储详细概况", notes = "获取光盘库存储系统中指定光盘匣的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "poolid", value = "光盘库存储系统光盘匣ID", required = true)
    })
    public JSONObject OpticalDetailInfo(String poolid) {
        JSONObject Jarrary = new JSONObject();
        CdPooldetail boxInfo = new CdPooldetail();
        //从下级系统获取需求数据
        JSONArray boxlists = OpticalJsonHandle.cdboxlist();
        JSONObject boxInfoObject;
        for (int i = 0; i < boxlists.size(); i++) {
            boxInfoObject = boxlists.getJSONObject(i);
            String name = boxInfoObject.get("label").toString();
            String cdboxfreecapacity = boxInfoObject.getString("cdboxfreecapacity");
            String cdboxtotalcapacity = boxInfoObject.getString("cdboxtotalcapacity");
            String cdboxusedcapacity = boxInfoObject.getString("cdboxusedcapacity");
            //匹配指定光盘匣
            if (name.equals(poolid)) {
                boxInfo.setName(name);
                boxInfo.setCapacity(Double.parseDouble(cdboxtotalcapacity));
                boxInfo.setUsed(Double.parseDouble(cdboxusedcapacity));
                boxInfo.setFree(Double.parseDouble(cdboxfreecapacity));

                break;
            }
        }
        //返回存储池数组
        Jarrary.accumulate("pool", boxInfo);
        return Jarrary;
    }
}
