/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.general;

import com.shdd.cfs.dto.dashboard.CurrentDistributedCapacityDetail;
import com.shdd.cfs.dto.dashboard.CurrentOpticalCapacityDetail;
import com.shdd.cfs.dto.dashboard.CurrentTapeCapacityDetail;
import com.shdd.cfs.dto.dashboard.DistributeCapacityStruct;
import com.shdd.cfs.utils.json.DistributeUrlHandle;
import com.shdd.cfs.utils.json.GetJsonMessage;
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

@RestController
@Slf4j
public class CurrentStorageCapacity {
    /**
     * 获取仪表盘当前容量信息
     *
     * @param SetValue
     * @return
     */
    @Autowired
    IampRequest iampRequest;
    @GetMapping(value = "api/dashboard/curcapacitystatus")
    @ApiOperation(value = "获取仪表盘当前容量信息", notes = "获取各存储系统当前的容量使用情况，包含总容量和已使用容量")

    public JSONObject SendCurrentCapacity(String SetValue) throws DocumentException {
//        log.info("SetValue", SetValue);
//        //光盘库节点容量获取对象
//        JSONObject getopticalcapacity = new JSONObject();
//        //获取光盘库容量, 节点状态
//        String capacity = "{\"protoname\":\"nodeconnect\"}";
//        //获取光盘库节点返回报文
//        getopticalcapacity = GetJsonMessage.GetJsonStr("192.168.100.199", 8000, capacity);
//          Double allOptCapacity = Double.parseDouble(getopticalcapacity.getString("totalinfo"));
//          Double useOptCapacity = Double.parseDouble(getopticalcapacity.getString("usedinfo"));
		//获取磁带库容量信息
        String sessonKey = iampRequest.SessionKey();
        ArrayList<Integer> alltapelist = iampRequest.all_of_tape_status(sessonKey);
        Integer alltapesize  = 	alltapelist.size();
        Double singleTapeCapacity  = 2.5;
        int fulltape = 0;
        for(Integer list: alltapelist){
            if (list == 1){
                fulltape = fulltape + 1;
            }  //空白磁带
        }
        //磁带库总容量大小
        Double allTapeCapacity = alltapesize * singleTapeCapacity;
        //磁带库使用容量大小
        Double useTapeCapacity = (alltapesize -  fulltape) * singleTapeCapacity;
        //定义Json发送对象
        DistributeCapacityStruct currentDisVal = new DistributeCapacityStruct();
        CurrentTapeCapacityDetail currenttapeVal = new CurrentTapeCapacityDetail();
        CurrentOpticalCapacityDetail currentOptVal = new CurrentOpticalCapacityDetail();
        //给磁带库当前容量赋值
        currenttapeVal.setCapacity(allTapeCapacity);
        currenttapeVal.setUsedCapacity(useTapeCapacity);
        currenttapeVal.setDevType("tape");
        //给光盘库当前容量赋值
        currentOptVal.setCapacity(100.00);
        currentOptVal.setDevType("cdstorage");
        currentOptVal.setUsedCapacity(50.00);
        //给分布式容量赋值

        //获取分布式单个存储池信息
       // JSONObject poolinfo = DistributeUrlHandle.Poolinfo();
        CurrentDistributedCapacityDetail[] disValData = new CurrentDistributedCapacityDetail[1];
        disValData[0] = new CurrentDistributedCapacityDetail();
        disValData[0].setCapacity(23.4);
        disValData[0].setPoolName("123");
        disValData[0].setUsedCapacity(43.1);
        //组织返回JSON数据对象
        currentDisVal.setDevType("distributed");
        currentDisVal.setData(disValData);
        JSONObject jobject = new JSONObject();
        JSONArray devices = new JSONArray();
        devices.add(currentDisVal);
        devices.add(currenttapeVal);
        devices.add(currentOptVal);
        jobject.accumulate("device", devices);
        //给UI 发送 JSON对象
        return jobject;
    }
}