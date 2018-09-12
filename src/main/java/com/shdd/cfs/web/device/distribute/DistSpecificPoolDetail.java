/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.dto.device.distribute.DistPoolDetail;
import com.shdd.cfs.utils.json.HttpRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
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
    @GetMapping(value = "api/dashboard/distribute/pool")
    @ApiOperation(value = "获取分布式存储系统存储池详细概况", notes = "获取分布式存储系统指定存储池的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "poolid", value = "指定分布式存储池ID", required = true)
    })

    public JSONObject SendDetailInfoOfPool(String value, int poolid) {

        //访问下级分布式系统接口api/volumes/{volume_id}/storage
        HttpRequest httpRequest = new HttpRequest();

        String result = httpRequest.sendGet("http://192.168.1.32:8000/api/volumes/" + poolid + "/storage", " ");
        JSONObject poolObject = JSONObject.fromObject(result);

        //
        ArrayList poolList = new ArrayList();
        DistPoolDetail poolDetail = new DistPoolDetail();
        String totalCapacity = poolObject.getString("size");
        String freeCapacity = poolObject.getString("avail");
        String usedCapacity = poolObject.getString("used");
        poolDetail.setCapacity(totalCapacity.substring(0, totalCapacity.length() - 1));
        poolDetail.setName(poolObject.getString("vol_name"));

        String unitCapacity = "";
        String unitUsed = "";
        //获取到单位信息，并做出判断
        Double Capacity = 0.0;
        Double used = 0.0;
        unitCapacity = freeCapacity.substring(freeCapacity.length() - 1, freeCapacity.length());
        unitUsed = usedCapacity.substring(usedCapacity.length() - 1, usedCapacity.length());

        if (unitCapacity.equalsIgnoreCase("T")) {
            //根据单位信息，转成TB级别数据
            Capacity = Double.parseDouble(freeCapacity.substring(0, freeCapacity.length() - 1));
        } else if (unitCapacity.equalsIgnoreCase("G")) {
            //根据单位信息为GB，转成TB级别数据
            Capacity = Double.parseDouble(freeCapacity.substring(0, freeCapacity.length() - 1)) / 1024;
        } else if (unitCapacity.equalsIgnoreCase("M")) {
            //根据单位信息为MB，转成TB级别数据
            Capacity = Double.parseDouble(freeCapacity.substring(0, freeCapacity.length() - 1)) / 1024 / 1024;
        } else {
            //根据单位信息为Byte，转成TB级别数据
            Capacity = Double.parseDouble(freeCapacity.substring(0, freeCapacity.length() - 1)) / 1024 / 1024 / 1024;
        }
        poolDetail.setFree(Capacity.toString());

        if (unitUsed.equalsIgnoreCase("T")) {
            //根据单位信息，转成TB级别数据
            used = Double.parseDouble(usedCapacity.substring(0, usedCapacity.length() - 1));
        } else if (unitUsed.equalsIgnoreCase("G")) {
            //根据单位信息为GB，转成TB级别数据
            used = Double.parseDouble(usedCapacity.substring(0, usedCapacity.length() - 1)) / 1024;
        } else if (unitUsed.equalsIgnoreCase("M")) {
            //根据单位信息为MB，转成TB级别数据
            used = Double.parseDouble(usedCapacity.substring(0, usedCapacity.length() - 1)) / 1024 / 1024;
        } else {
            //根据单位信息为Byte，转成TB级别数据
            used = Double.parseDouble(usedCapacity.substring(0, usedCapacity.length() - 1)) / 1024 / 1024 / 1024;
        }
        poolDetail.setUsed(used);

        //访问下级分布式系统接口api/volumes/volume_id
        result = httpRequest.sendGet("http://192.168.1.32:8000/api/volumes/" + poolid, " ");
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
