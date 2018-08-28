/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地公司 版权所有
 *
 */
package com.shdd.cfs.web.device.optical;

import com.shdd.cfs.dto.device.optical.OpticalNodeDetail;
import com.shdd.cfs.utils.json.GetJsonMessage;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jafuzeng
 * @version: 1.0 2018/8/28
 */
@RestController
@Slf4j
public class DetailedOverviewOfOpticalSystemNodes {
    @GetMapping(value = "gg/opticalnode")
    @ApiOperation(value = "获取光盘库存储系统节点详细概况")
    public JSONObject TapeSystemNodeInfo(String value) {
        //组织获取光盘库节点信息
        JSONObject getjsoninfo = new JSONObject();
        JSONObject getcapacity = new JSONObject();
        GetJsonMessage jsoninfo = new GetJsonMessage();
        String cmdstr = "{\"protoname\":\"basicinfo\",\"jukeid\":\"001\"}";// 获取光盘库型号
        String capacity = "{\"protoname\":\"nodeconnect\"}"; //获取光盘库容量, 节点状态
        getjsoninfo = GetJsonMessage.GetJsonStr("192.168.100.199", 8000, cmdstr);
        getcapacity = GetJsonMessage.GetJsonStr("192.168.100.199", 8000, capacity);
        System.out.println(getjsoninfo);
        System.out.println(getcapacity);
         //1.工作中 2.服务未启动 -98.创建中 3.硬盘报警 4.网络错误 -99.删除中
         //光盘库1为工作中，1之外的状态认为是非工作状态
        int valuestauts = Integer.parseInt(getcapacity.getString("nodestatus"));//
        if (valuestauts != 1) {
            valuestauts = 2;
        }
        //组织发送光盘库节点信息
        JSONObject Jarrary = new JSONObject();
        OpticalNodeDetail[] tapenode = new OpticalNodeDetail[1];
        tapenode[0] = new OpticalNodeDetail();
        tapenode[0].setId(1);
        tapenode[0].setCapacity(Double.parseDouble(getcapacity.getString("totalinfo")));
        tapenode[0].setUsed(Double.parseDouble(getcapacity.getString(("usedinfo"))));
        tapenode[0].setName(getjsoninfo.getString("label"));
        tapenode[0].setStatus(valuestauts);
        Jarrary.accumulate("disk", tapenode);
        return Jarrary;
    }
}
