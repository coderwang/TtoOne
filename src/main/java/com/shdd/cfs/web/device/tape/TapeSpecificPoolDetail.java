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
        Double usedCapacity = 0.0;
        Double remainCapacity = 0.0;
        //获取session_key
        String session = iampRequest.SessionKey();
        //获取xml数据
        HttpResult groupsXmlData = iampRequest.inquiry_gtape_lists(session);
        HttpResult tapeXmlDate = iampRequest.inquiry_tape_lists(session);
        //获取磁带组列表信息
        ArrayList<Map<String, String>> groupsList = iampRequest.tape_group_info(groupsXmlData);
        ArrayList poolList = new ArrayList();
        //获取磁带组id =  poolid 总容量和剩余总容量
        ArrayList totalArrary = iampRequest.get_tapes_capacityinfo(tapeXmlDate,"total",poolid);
        ArrayList remainArrary = iampRequest.get_tapes_capacityinfo(tapeXmlDate,"remaining",poolid);
        for( int i = 0 ; i < totalArrary.size(); i++){
            capacity += Double.parseDouble(totalArrary.get(i).toString());
        }
        for( int j = 0 ; j < remainArrary.size(); j++){
            remainCapacity += Double.parseDouble(remainArrary.get(j).toString());
        }
        usedCapacity = capacity - remainCapacity;
		TapePoolDetail poolInfoDetail = new TapePoolDetail();
		poolInfoDetail.setCapacity(capacity);
		poolInfoDetail.setFree(remainCapacity);
		poolInfoDetail.setUsed(usedCapacity);
        //给磁带组赋值
        for (Map<String, String> group : groupsList) {
             if (poolid.equals(group.get("id"))) {
                poolInfoDetail.setName(group.get("groupname"));

                poolList.add(poolInfoDetail);
            }
        }

        //数据打包
        JSONObject Jarrary = new JSONObject();

        Jarrary.accumulate("pool", poolList);
        return Jarrary;
    }
}
