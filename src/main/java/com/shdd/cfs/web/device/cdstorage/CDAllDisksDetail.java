/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.cdstorage;

import com.shdd.cfs.dto.device.distribute.DiskDetailInfo;
import com.shdd.cfs.utils.json.OpticalJsonHandle;
import com.shdd.cfs.utils.page.PageOpt;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
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
public class CDAllDisksDetail {
    /**
     * @param page_num
     * @param count
     * @return
     */
    @GetMapping(value = "api/dashboard/disk/disks")
    @ApiOperation(value = "获取光盘库存储系统中所有光盘的详细信息", notes = "获取光盘库存储系统中所有光盘的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "page_num", value = "翻页页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "count", value = "每页所含最大条目数", required = true)
    })
    public JSONObject GetCDAllDisksDetailInfo(int page_num, int count) {
        JSONObject jarrary = new JSONObject();
        ArrayList<DiskDetailInfo> arrayList = new ArrayList<>();
        ArrayList<JSONObject> cdlists = OpticalJsonHandle.getAllCardInfo();
        for(JSONObject list : cdlists){
            DiskDetailInfo arrdtail = new DiskDetailInfo();
            arrdtail.setId(list.getInt("cdslotid"));
            arrdtail.setName(list.getString("label"));
            arrdtail.setCapacity(list.getDouble("cdinfo"));
            arrdtail.setUsed(list.getDouble("cdinfo") - list.getDouble("leftinfo"));
            if(list.getInt("cdslotstate") == 0) {//指盘槽内无盘
               arrdtail.setStatus(0);
            } else {

                arrdtail.setStatus(1);
            }
            arrayList.add(arrdtail);
        }
        Integer cdNum = cdlists.size();
        Integer totalPage = cdNum%count==0?cdNum/count:cdNum/count+1; //总页数
        JSONArray jarray = PageOpt.PagingLogicProcessing(page_num,totalPage,count,cdNum,arrayList);
        jarrary.accumulate("totalPage", totalPage);
        jarrary.accumulate("disk", jarray);
        return jarrary;
    }
}
