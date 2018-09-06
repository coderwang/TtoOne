/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.optical.OpticalSystemInfoDetail;
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

@RestController
@Slf4j
public class TapeAllHostsSummary {
    /**
     * 获取磁带库存储系统概况
     *
     * @param val
     * @return
     */
    @Autowired
    IampRequest iampRequest;

    @GetMapping(value = "api/dashboard/tape/hosts")
    @ApiOperation(value = "获取磁带库存储系统概况", notes = "获取磁带库存储系统中所有服务器节点的概要信息")
    public JSONObject TapeLibraryInfo(String val) throws DocumentException {
        //获取磁带库磁带总个数
        Integer alltapesize = 0;
        Integer tapeStatus = 0;
        Integer cpucount = 0;
        String cputype = "pythium(mytest)";
        Double memcapacity = 0.0;

        ArrayList hostList = new ArrayList();

        //与下级磁带库通信获取会话信息
        String sessonKey = iampRequest.SessionKey();
        if (sessonKey.equals("wrong")) {// 获取sessonKey失败
            System.out.println("磁带库不在线");
        } else {
            HttpResult tape_lists = iampRequest.inquiry_tape_lists(sessonKey);
            ArrayList<Integer> alltapelist = iampRequest.all_of_tape_status(tape_lists);

            OpticalSystemInfoDetail hostInfo = new OpticalSystemInfoDetail();

            alltapesize = alltapelist.size();
            tapeStatus = 1;
            cpucount = 1;//待定
            cputype = "pythium(mytest)"; //待定
            memcapacity = 34.5;//待定

            hostInfo.setCpuCount(cpucount);
            hostInfo.setCpuType(cputype);
            hostInfo.setHardDiskCount(alltapesize);
            hostInfo.setMemCapacity(memcapacity);
            hostInfo.setName("磁带库");//配置

            hostList.add(hostInfo);
        }
        //数据打包
        JSONObject Jarrary = new JSONObject();

        Jarrary.accumulate("tape", hostList);

        return Jarrary;
    }
}
