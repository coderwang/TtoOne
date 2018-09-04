/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.tape.TapeLibraryStorageSystemStoresDetail;
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
        String tapeName =  "";
        //获取session_key
        String session = iampRequest.SessionKey();
        HttpResult glist = iampRequest.inquiry_gtape_lists(session);
        //获取磁带组磁带情况
        ArrayList<Map<String,String>> group = iampRequest.tape_group_info(glist);
        //给磁带组赋值
        for(Map<String,String> list : group){
            if (poolid.equals(list.get("groupname"))){
        	   capacity = Integer.parseInt(list.get("alltapenum")) *2.5;
        	   freecapacity = Integer.parseInt(list.get("emptytapenum"))*2.5;
        	   usedCapacity = capacity - freecapacity;
        	   tapeName = poolid;
        	   break;
            } else {
                System.out.println("未知的磁带组" + poolid);
            }
        }
        JSONObject Jarrary = new JSONObject();
        TapeLibraryStorageSystemStoresDetail[] tapearrary = new TapeLibraryStorageSystemStoresDetail[1];
        tapearrary[0] = new TapeLibraryStorageSystemStoresDetail();
        tapearrary[0].setName(tapeName);
        tapearrary[0].setCapacity(capacity);
        tapearrary[0].setUsed(usedCapacity);
        tapearrary[0].setFree(freecapacity);
        Jarrary.accumulate("pool", tapearrary);
        return Jarrary;
    }
}
