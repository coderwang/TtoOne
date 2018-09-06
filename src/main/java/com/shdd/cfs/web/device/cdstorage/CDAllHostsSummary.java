/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.cdstorage;

import com.shdd.cfs.dto.device.optical.OpticalSystemInfoDetail;
import com.shdd.cfs.utils.json.OpticalJsonHandle;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class CDAllHostsSummary {
    /**
     * 获取光盘库存储系统概况
     *
     * @return
     */
    @GetMapping(value = "api/dashboard/disk/hosts")
    @ApiOperation(value = "获取光盘库存储系统概况", notes = "获取光盘库存储系统所有节点的概要信息")
    public JSONObject TapeLibraryInfo() {
        //光盘库存储信息组织接口
        Map<String, Integer> olineinfo = OpticalJsonHandle.OnlineCdInfo();
        Map<String, String> cpuinfo = OpticalJsonHandle.CpuInfo();
        Map<String, String> memInfo = OpticalJsonHandle.cdLibMemInfo();
        Map<String, String> cdbasicinfo = OpticalJsonHandle.cdLibBasicInfo();
        Integer cdCount = olineinfo.get("totalOlineCard");
        Integer cpucount = Integer.parseInt(cpuinfo.get("cpucount"));
        String cputype = cpuinfo.get("cputype");
        Double totalMem = Double.parseDouble(memInfo.get("memtotal"));
        String cdLibName = cdbasicinfo.get("label");
        //光盘库存储信息发送接口
        JSONObject Jobject = new JSONObject();
        JSONArray Jarrary = new JSONArray();
        OpticalSystemInfoDetail cdarrary = new OpticalSystemInfoDetail();
        cdarrary.setCpuCount(cpucount);
        cdarrary.setCpuType(cputype);
        cdarrary.setHardDiskCount(cdCount);
        cdarrary.setMemCapacity(totalMem);
        cdarrary.setName(cdLibName);
        Jarrary.add(cdarrary);
        Jobject.accumulate("disk", Jarrary);
        return Jobject;
    }
}
