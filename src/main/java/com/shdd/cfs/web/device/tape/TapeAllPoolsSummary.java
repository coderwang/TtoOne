/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.distribute.PoolGeneralOverviewDetail;
import com.shdd.cfs.utils.xml.iamp.HttpResult;
import com.shdd.cfs.utils.xml.iamp.IampRequest;
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
/**
 * @author: wangpeng
 * @version: 1.0 2018/8/29
 */
public class TapeAllPoolsSummary {
    /**
     * @return
     */
    @Autowired
    IampRequest iampRequest;

    @GetMapping(value = "api/dashboard/tape/pools")
    @ApiOperation(value = "获取磁带库存储系统存储池总体概况", notes = "磁带库存储系统所有磁带组总体概况")
    public JSONObject GetTapeAllPoolsSummaryInfo() throws DocumentException {
        //获取会话信息
        String sessonKey = iampRequest.SessionKey();
        //获取将要处理的xml数据信息
        HttpResult groupXmlData = iampRequest.inquiry_gtape_lists(sessonKey);
        //获取磁带组列表信息
        ArrayList<Map<String, String>> groupList = iampRequest.tape_group_info(groupXmlData);
        //发送缓存区
        ArrayList<PoolGeneralOverviewDetail> poolsList = new ArrayList<>();
        //给磁带组赋值
        for (Map<String, String> group : groupList) {
            Double capacity = Double.parseDouble(group.get("alltapenum")) * 2.5;
            Double freecapacity = Double.parseDouble(group.get("emptytapenum")) * 2.5;
            Double usedCapacity = capacity - freecapacity;

            PoolGeneralOverviewDetail pool = new PoolGeneralOverviewDetail();
            pool.setId(group.get("id"));
            pool.setName(group.get("groupname"));
            pool.setCapacity(capacity);
            pool.setUsed(usedCapacity);
            pool.setFree(freecapacity);

            poolsList.add(pool);
        }

        //数据打包
        JSONObject rstObject = new JSONObject();
        rstObject.accumulate("poolCount", poolsList.size());
        rstObject.accumulate("poolName", poolsList);
        return rstObject;
    }
}
