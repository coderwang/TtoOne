/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.distribute.HostNodeInfoDetail;
import com.shdd.cfs.utils.page.PageOpt;
import com.shdd.cfs.utils.xml.iamp.HttpResult;
import com.shdd.cfs.utils.xml.iamp.IampRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@Slf4j
/**
 * @author: wangpeng
 * @version: 1.0 2018/8/29
 */
public class TapeSpecificPoolDisksDetail {
    /**
     * @param poolid
     * @param page_num
     * @param count
     * @return
     */
    @Autowired
    IampRequest iampRequest;

    @GetMapping(value = "api/dashboard/tape/pool/disks")
    @ApiOperation(value = "主机信息状态下获取分布式存储系统节点详细概况", notes = "分布式系统指定存储池中的磁盘详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "poolid", value = "磁带库磁带组ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "page_num", value = "翻页页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "count", value = "每页所含最大条目数", required = true)
    })
    public JSONObject GetTapeSpecificPoolDisksDetailInfo(String poolid, int page_num, int count) throws DocumentException {
        //从下级磁带库系统接口获取数据
        String sessonKey = iampRequest.SessionKey();
        //磁带库中所有磁带
        HttpResult allTapesList = iampRequest.inquiry_tape_lists(sessonKey);
        //磁带ID列表
        ArrayList<String> tapesIdList = iampRequest.get_tapes_id(allTapesList);
        //指定磁带组中的所有磁带
        ArrayList<HostNodeInfoDetail> poolTapesList = new ArrayList<>();
        //自定义磁带ID
        int id = 0;

        for (String tapeID : tapesIdList) {
            String groupId = iampRequest.get_group_id(allTapesList, tapeID);
            //匹配目标磁带组
            if (groupId.equals(poolid)) {
                id++;
                Map<String, String> capacity = iampRequest.get_tape_capacityinfo(allTapesList, tapeID);
                HostNodeInfoDetail tape = new HostNodeInfoDetail();

                tape.setId(id);
                tape.setCapacity(Double.parseDouble(capacity.get("total")));
                tape.setUsed(Double.parseDouble(capacity.get("total")) - Double.parseDouble(capacity.get("remaining")));
                tape.setName(tapeID);
                tape.setStatus(iampRequest.tape_online_info(allTapesList, tapeID));

                poolTapesList.add(tape);
            }
        }
        //指定磁带组中磁带总个数
        int tapesCount = poolTapesList.size();
        int totalPage = tapesCount % count == 0 ? tapesCount / count : tapesCount / count + 1;
        //处理分页显示
        JSONArray Jarray = PageOpt.PagingLogicProcessing(page_num, totalPage, count, tapesCount, poolTapesList);
        //计算总页数
        JSONObject RstObject = new JSONObject();

        RstObject.accumulate("totalPage", totalPage);
        RstObject.accumulate("disk", Jarray);
        return RstObject;
    }
}

