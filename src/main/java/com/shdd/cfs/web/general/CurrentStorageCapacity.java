package com.shdd.cfs.web.general;

import com.shdd.cfs.dto.dashboard.CurrentDistributedCapacityDetail;
import com.shdd.cfs.dto.dashboard.CurrentOpticalCapacityDetail;
import com.shdd.cfs.dto.dashboard.CurrentTapeCapacityDetail;
import com.shdd.cfs.dto.dashboard.DistributeCapacityStruct;
import com.shdd.cfs.utils.json.GetJsonMessage;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CurrentStorageCapacity {
    /**
     * 获取仪表盘当前容量信息
     *
     * @param SetValue
     * @return
     */
    @GetMapping(value = "api/dashboard/curcapacitystatus")
    @ApiOperation(value = "获取仪表盘当前容量信息", notes = "获取各存储系统当前的容量使用情况，包含总容量和已使用容量")

    public JSONObject SendCurrentCapacity(String SetValue) {
        log.info("SetValue", SetValue);
        //光盘库节点容量获取对象
        JSONObject getopticalcapacity = new JSONObject();
        //获取光盘库容量, 节点状态
        String capacity = "{\"protoname\":\"nodeconnect\"}";
        //获取光盘库节点返回报文
        getopticalcapacity = GetJsonMessage.GetJsonStr("192.168.100.199", 8000, capacity);
        //获取分布式单个集群信息
        String baseurl = "";
        String disstorageinfo = baseurl + "api/monitor/clustes/cluster_id/storage/";
        String discapacity = GetJsonMessage.getURLContent(disstorageinfo);
        //获取分布式回传的json报文
        JSONObject disjsoncapacity = JSONObject.fromObject(discapacity);
        //获取分布式存储集群总的使用容量
        Double disUseCapacity = Double.parseDouble(disjsoncapacity.getString("storage_used"));//获取使用容量信息
        Double disTotalCapacity = Double.parseDouble(disjsoncapacity.getString("storage_total"));
        String disclusterName = disjsoncapacity.getString("cluster_name");
        //定义Json发送对象
        DistributeCapacityStruct currentDisVal = new DistributeCapacityStruct();
        CurrentDistributedCapacityDetail[] disValData = new CurrentDistributedCapacityDetail[1];
        CurrentTapeCapacityDetail currenttapeVal = new CurrentTapeCapacityDetail();
        CurrentOpticalCapacityDetail currentOptVal = new CurrentOpticalCapacityDetail();
        //给磁带库当前容量赋值
        currenttapeVal.setCapacity(20.34);
        currenttapeVal.setUsedCapacity(15.4);
        currenttapeVal.setDevType("tape");
        //给光盘库当前容量赋值
        currentOptVal.setCapacity(Double.parseDouble(getopticalcapacity.getString("totalinfo")));
        currentOptVal.setDevType("cdstorage");
        currentOptVal.setUsedCapacity(Double.parseDouble(getopticalcapacity.getString(("usedinfo"))));
        //给分布式容量赋值
        disValData[0] = new CurrentDistributedCapacityDetail();
        disValData[0].setCapacity(disTotalCapacity);
        disValData[0].setPoolName(disclusterName);
        disValData[0].setUsedCapacity(disUseCapacity);
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