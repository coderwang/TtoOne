/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.tape.TapePoolDetail;
import com.shdd.cfs.utils.xml.iamp.HttpResult;
import com.shdd.cfs.utils.xml.iamp.IampRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@Slf4j
public class TapeSpecificPoolDetail {
    /**
     * 获取磁带库存储系统存储详细概况
     *
     * @param value
     * @return
     */
    @Autowired
    private IampRequest iampRequest;

    @GetMapping(value = "api/dashboard/tape/pool")
    @ApiOperation(value = "获取磁带库存储系统存储详细概况", notes = "获取磁带库存储系统中指定磁带组的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "poolid", value = "磁带库磁带组ID", required = true)
    })

    public JSONObject TapeDetailInfo(String poolid) throws DocumentException {
        Double capacity = 0.0;
        Double freecapacity = 0.0;
        Double usedCapacity = 0.0;
        //获取session_key
        String session = iampRequest.SessionKey();
        //获取xml数据
        HttpResult groupsXmlData = iampRequest.inquiry_gtape_lists(session);
        //获取磁带组列表信息
        ArrayList<Map<String, String>> groupsList = iampRequest.tape_group_info(groupsXmlData);
        ArrayList poolList = new ArrayList();
        //给磁带组赋值
        for (Map<String, String> group : groupsList) {
            if (poolid.equals(group.get("id"))) {
                TapePoolDetail poolInfoDetail = new TapePoolDetail();

                capacity = Double.parseDouble(group.get("alltapenum")) * 2.5;
                freecapacity = Double.parseDouble(group.get("emptytapenum")) * 2.5;
                usedCapacity = capacity - freecapacity;

                poolInfoDetail.setCapacity(capacity);
                poolInfoDetail.setFree(freecapacity);
                poolInfoDetail.setUsed(usedCapacity);
                poolInfoDetail.setName("磁带长期保存库" + poolid);

                poolList.add(poolInfoDetail);
                break;
            } else {
                System.out.println("未知的磁带组" + poolid);
            }
        }

        //数据打包
        JSONObject Jarrary = new JSONObject();

        Jarrary.accumulate("pool", poolList);
        return Jarrary;
    }
}
