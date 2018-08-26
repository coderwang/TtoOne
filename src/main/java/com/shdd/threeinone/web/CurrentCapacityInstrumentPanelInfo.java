package com.shdd.threeinone.web;


import com.shdd.threeinone.dto.instrumentPane.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CurrentCapacityInstrumentPanelInfo {

    @GetMapping(value = "gg/current")
    @ApiOperation(value = "获取仪表盘当前容量信息")


    public JSONObject SendCurrentCapacity(String SetValue) {
        log.info("SetValue", SetValue);
        DistributeCapacityStruct currentDisVal = new DistributeCapacityStruct();
        CurrentDistributedCapacityDetail[] disValData = new CurrentDistributedCapacityDetail[1];
        CurrentTapeCapacityDetail currenttapeVal = new CurrentTapeCapacityDetail();
        CurrentOpticalCapacityDetail currentOptVal = new CurrentOpticalCapacityDetail();

        /*给磁带库当前容量赋值*/
        currenttapeVal.setCapacity(20.34);
        currenttapeVal.setUsedCapacity(15.4);
        currenttapeVal.setDevType("tape");
        /*给光盘库当前容量赋值*/
        currentOptVal.setCapacity(100.00);
        currentOptVal.setDevType("optical");
        currentOptVal.setUsedCapacity(99.99);
        /*给分布式容量赋值*/
        disValData[0] = new CurrentDistributedCapacityDetail();
        disValData[0].setCapacity(20.34);
        disValData[0].setPoolName("xx");
        disValData[0].setUsedCapacity(15.4);
        currentDisVal.setDevType("distributed");
        currentDisVal.setData(disValData);

        /*组织返回JSON数据对象*/
        JSONObject jobject = new JSONObject();
        JSONArray devices = new  JSONArray();
        devices.add(currentDisVal);
        devices.add(currenttapeVal);
        devices.add(currentOptVal);
        jobject.accumulate("devices",devices);
        /*给UI 发送 JSON对象 */
        return jobject;
    }
}