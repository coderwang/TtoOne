/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.cdstorage;

import com.shdd.cfs.dto.device.optical.OpticalNodeDetail;
import com.shdd.cfs.utils.json.GetJsonMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

        //计算总页数
        int totalPage = 0;
        if (count != 0) {
            totalPage = 1 / count + 1;
        }
        System.out.println(totalPage);

        Jarrary.accumulate("totalPage", totalPage);
        Jarrary.accumulate("disk", tapenode);
        return Jarrary;
    }
}
