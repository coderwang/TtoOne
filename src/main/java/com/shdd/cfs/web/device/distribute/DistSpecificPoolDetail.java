/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.config.DateConfig;
import com.shdd.cfs.dto.device.distribute.DistPoolDetail;
import com.shdd.cfs.utils.base.UnitHandle;
import com.shdd.cfs.utils.json.HttpRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Slf4j
public class DistSpecificPoolDetail {
    /**
     * 获取分布式存储系统存储池详细概况
     *
     * @param value
     * @return
     */
    @Autowired
    private DateConfig config;
    @GetMapping(value = "api/dashboard/distribute/pool")
    @ApiOperation(value = "获取分布式存储系统存储池详细概况", notes = "获取分布式存储系统指定存储池的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "poolid", value = "指定分布式存储池ID", required = true)
    })

    public JSONObject SendDetailInfoOfPool(String value, int poolid) {

        //访问下级分布式系统接口api/volumes/{volume_id}/storage
        HttpRequest httpRequest = new HttpRequest();
        String urlvol = config.getDistributeUrl() + "api/volumes";
        String result = httpRequest.sendGet(urlvol + poolid + "/storage", " ");
        JSONObject poolObject = JSONObject.fromObject(result);

        ArrayList poolList = new ArrayList();
        DistPoolDetail poolDetail = new DistPoolDetail();
        String totalCapacity = poolObject.getString("size");
        String freeCapacity = poolObject.getString("avail");
        String usedCapacity = poolObject.getString("used");
        //获取到单位信息，并做出判断
        Double total = UnitHandle.unitConversionToTB(totalCapacity);
        Double used = UnitHandle.unitConversionToTB(usedCapacity);
        Double free = UnitHandle.unitConversionToTB(freeCapacity);

        //组织发送数据
        poolDetail.setName(poolObject.getString("vol_name"));
        poolDetail.setCapacity(total);
        poolDetail.setUsed(used);
        poolDetail.setFree(free);

        //访问下级分布式系统接口api/volumes/volume_id
        result = httpRequest.sendGet(urlvol + poolid, " ");
        poolObject = JSONObject.fromObject(result);

        if (poolObject.getString("vol_status").equalsIgnoreCase("started")) {
            poolDetail.setStatus(1);
        } else {
            poolDetail.setStatus(0);
        }

        poolList.add(poolDetail);

        //数据打包
        JSONObject Jobject = new JSONObject();

        Jobject.accumulate("pool", poolList);
        return Jobject;
    }

}
