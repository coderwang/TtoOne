/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.cdstorage;

import com.shdd.cfs.dto.device.optical.CdPoolStorageInfo;
import com.shdd.cfs.utils.json.OpticalJsonHandle;
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
        JSONObject rstObject = new JSONObject();
        JSONArray slotArray = OpticalJsonHandle.cdslotlist(String.valueOf(poolid));
        ArrayList<CdPoolStorageInfo> cdDiskInfoList = new ArrayList<>();
        JSONObject cdDiskInfoObject;
        //翻页
        int page_count = 0;

        Integer cdNum = slotArray.size();//总光盘个数
        for (int i = 0; i < cdNum; i++) {
            cdDiskInfoObject = slotArray.getJSONObject(i);

            //翻页
            i++;
            if ((i + 1) <= (page_num - 1) * count) {
                continue;
            }

            //处理数据
            CdPoolStorageInfo cdDiskInfoDetail = new CdPoolStorageInfo();
            cdDiskInfoDetail.setId(cdDiskInfoObject.getInt("cdslotid"));
            cdDiskInfoDetail.setName(cdDiskInfoObject.getString("label"));
            cdDiskInfoDetail.setCapacity(cdDiskInfoObject.getDouble("cdinfo"));
            cdDiskInfoDetail.setUsed(cdDiskInfoObject.getDouble("cdinfo") - cdDiskInfoObject.getDouble("leftinfo"));
            if (cdDiskInfoObject.getInt("cdslotstate") == 0) {
                cdDiskInfoDetail.setStatus(0);
            } else {
                cdDiskInfoDetail.setStatus(1);
            }

            cdDiskInfoList.add(cdDiskInfoDetail);
            page_count += 1;
            if (page_count == count) {
                break;
            }
        }

        Integer totalPage = cdNum % count == 0 ? cdNum / count : cdNum / count + 1; //总页数

        rstObject.accumulate("totalPage", totalPage);
        rstObject.accumulate("disk", cdDiskInfoList);
        return rstObject;
    }
}
