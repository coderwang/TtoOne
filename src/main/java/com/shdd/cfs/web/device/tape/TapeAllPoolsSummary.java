/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.distribute.PoolGeneralOverviewDetail;
import com.shdd.cfs.dto.device.optical.OpticalSystemInfoDetail;
import com.shdd.cfs.utils.xml.iamp.HttpResult;
import com.shdd.cfs.utils.xml.iamp.IampRequest;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
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

        JSONObject storagePool = new JSONObject();
        JSONArray jarrary = new JSONArray();
        String sessonKey = iampRequest.SessionKey();
        HttpResult glist = iampRequest.inquiry_gtape_lists(sessonKey);
        //获取磁带组磁带情况
        ArrayList<Map<String,String>> group = iampRequest.tape_group_info(glist);
        ArrayList<PoolGeneralOverviewDetail> pools = new ArrayList<>();
        //给磁带组赋值
        for(Map<String,String> list : group){
                Double capacity = Integer.parseInt(list.get("alltapenum")) *2.5;
                Double freecapacity = Integer.parseInt(list.get("emptytapenum"))*2.5;
                Double usedCapacity = capacity - freecapacity;
                PoolGeneralOverviewDetail pool = new PoolGeneralOverviewDetail();
                pool.setId(list.get("id"));
                pool.setName(list.get("groupname"));
                pool.setCapacity(capacity);
                pool.setUsed(usedCapacity);
                pool.setFree(freecapacity);
                pools.add(pool);
        }

        //发送JSON报文
        for(PoolGeneralOverviewDetail list :pools){
            jarrary.add(list);
        }

        storagePool.accumulate("poolCount", pools.size());
        storagePool.accumulate("poolName", jarrary);
        return storagePool;
    }
}
