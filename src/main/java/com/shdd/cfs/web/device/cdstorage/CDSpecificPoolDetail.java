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
        CdPooldetail cdarrary = new CdPooldetail();
		JSONArray boxlists = OpticalJsonHandle.cdboxlist();
        for(int i = 0 ;  i < boxlists.size(); i++){
			String name = boxlists.getJSONObject(i).get("label").toString();
			String cdboxfreecapacity = boxlists.getJSONObject(i).get("cdboxfreecapacity").toString();
			String cdboxtotalcapacity = boxlists.getJSONObject(i).get("cdboxtotalcapacity").toString();
			String cdboxusedcapacity = boxlists.getJSONObject(i).get("cdboxusedcapacity").toString();
			System.out.println(name +  "   " + cdboxtotalcapacity + "   "+ cdboxusedcapacity + "   " +cdboxfreecapacity);
			if(name.equals(poolid)){
				cdarrary.setName(name);
				cdarrary.setCapacity(Double.parseDouble(cdboxtotalcapacity));
				cdarrary.setUsed(Double.parseDouble(cdboxusedcapacity));
				cdarrary.setFree(Double.parseDouble(cdboxfreecapacity));
			}
		}
        //返回存储池数组
        Jarrary.accumulate("pool", cdarrary);
        return Jarrary;
    }
}
