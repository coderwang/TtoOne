/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.cdstorage;

import com.shdd.cfs.dto.device.DiskDetailInfo;
import com.shdd.cfs.utils.json.OpticalJsonHandle;
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
        Double usedSize = 0.0;

        //翻页
        int i = 0;
        int page_count = 0;

        ArrayList<DiskDetailInfo> cdDisksList = new ArrayList<>();
        //与下级光盘库通信
        ArrayList<JSONObject> cdInfoList = OpticalJsonHandle.getAllCardInfo();
        for (JSONObject infoIndexObject : cdInfoList) {
            //翻页
            i++;
            if ((i + 1) <= (page_num - 1) * count) {
                continue;
            }

            //处理数据
            DiskDetailInfo diskDetailInfo = new DiskDetailInfo();
            diskDetailInfo.setId(infoIndexObject.getString("cdslotid"));
            diskDetailInfo.setName(infoIndexObject.getString("label"));
            diskDetailInfo.setCapacity(infoIndexObject.getString("cdinfo"));

            usedSize = infoIndexObject.getDouble("cdinfo") - infoIndexObject.getDouble("leftinfo");
            diskDetailInfo.setUsed(usedSize.toString());
            if (infoIndexObject.getInt("cdslotstate") == 0) {
                //指盘槽内无盘
                diskDetailInfo.setStatus(0);
            } else {
                //光盘在位
                diskDetailInfo.setStatus(1);
            }

            //添加数据到缓存
            cdDisksList.add(diskDetailInfo);
            page_count += 1;
            if (page_count == count) {
                break;
            }
        }
        Integer cdNum = cdInfoList.size();
        Integer totalPage = cdNum % count == 0 ? cdNum / count : cdNum / count + 1; //总页数

        //数据打包
        jarrary.accumulate("totalPage", totalPage);
        jarrary.accumulate("disk", cdDisksList);
        return jarrary;
    }
}
