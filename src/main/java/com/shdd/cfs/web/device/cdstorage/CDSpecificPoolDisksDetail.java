/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.cdstorage;

import com.shdd.cfs.dto.device.optical.OpticalNodeDetail;
import com.shdd.cfs.utils.json.GetJsonMessage;
import com.shdd.cfs.utils.json.OpticalJsonHandle;
import com.shdd.cfs.utils.page.PageOpt;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author: jafuzeng
 * @version: 1.0 2018/8/28
 */
@RestController
@Slf4j
public class CDSpecificPoolDisksDetail {

    /**
     * 获取光盘库存储系统指定节点中光盘详细概况
     *
     * @param value
     * @return
     */
    @GetMapping(value = "api/dashboard/disk/pool/disks")
    @ApiOperation(value = "获取光盘库存储系统指定节点中光盘详细概况", notes = "获取指定光盘匣中的光盘详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "poolid", value = "光盘库光盘匣ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "page_num", value = "翻页页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "count", value = "每页所含最大条目数", required = true)
    })

    public JSONObject TapeSystemNodeInfo(String value, int poolid, int page_num, int count) {
        //组织获取光盘库节点信息
        JSONObject Jarrary = new JSONObject();
        JSONArray cdarray = OpticalJsonHandle.cdslotlist(String.valueOf(poolid));
        ArrayList<OpticalNodeDetail> jarray = new ArrayList<>();
        Integer cdNum = cdarray.size();//总光盘个数
        Integer totalPage = cdNum%count==0?cdNum/count:cdNum/count+1; //总页数
        for(int i = 0 ; i < cdNum ; i++){
            OpticalNodeDetail tapenode = new OpticalNodeDetail();
            tapenode.setId(cdarray.getJSONObject(i).getInt("cdslotid"));
            tapenode.setName(cdarray.getJSONObject(i).getString("label"));
            tapenode.setCapacity(cdarray.getJSONObject(i).getDouble("cdinfo"));
            tapenode.setUsed(cdarray.getJSONObject(i).getDouble("cdinfo") - cdarray.getJSONObject(i).getDouble("leftinfo"));
            if (cdarray.getJSONObject(i).getInt("cdslotstate") == 0){
                tapenode.setStatus(0);
            }else {
                tapenode.setStatus(1);
            }
           jarray.add(tapenode);
        }
        JSONArray array = PageOpt.PagingLogicProcessing(page_num, totalPage, count, cdNum, jarray);
        Jarrary.accumulate("totalPage", totalPage);
        Jarrary.accumulate("disk", array);
        return Jarrary;
    }
}
