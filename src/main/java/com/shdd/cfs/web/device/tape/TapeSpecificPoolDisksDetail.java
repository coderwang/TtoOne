/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.distribute.HostNodeInfoDetail;
import com.shdd.cfs.dto.device.distribute.PoolGeneralOverviewDetail;
import com.shdd.cfs.dto.device.tape.TapeNodeDetail;
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
        JSONObject Jobject = new JSONObject();
        HostNodeInfoDetail arrdtail = new HostNodeInfoDetail();
        String sessonKey = iampRequest.SessionKey();
        HttpResult tape_lists = iampRequest.inquiry_tape_lists(sessonKey);
        ArrayList<String> arrayList = iampRequest.get_tapes_id(tape_lists);
        int id = 0; //表示第几个磁带
        ArrayList<HostNodeInfoDetail> tapearrary = new ArrayList<>();
        for(String list :arrayList){
            String groupId = iampRequest.get_group_id(tape_lists,list);
            if(groupId.equals(poolid)){
                id++;
                Map<String, String> capacity = iampRequest.get_tape_capacityinfo(tape_lists, list);
                HostNodeInfoDetail tape = new HostNodeInfoDetail();
                tape.setId(id);
                tape.setCapacity(Double.parseDouble(capacity.get("total")));
                tape.setUsed(Double.parseDouble(capacity.get("total")) - Double.parseDouble(capacity.get("remaining")));
                tape.setName(list);
                tape.setStatus(iampRequest.tape_online_info(tape_lists, list));
                tapearrary.add(tape);
            }
        }
        int tapenum = tapearrary.size(); //磁带总个数
        int totalPage = tapenum%count==0?tapenum/count:tapenum/count+1;
        //处理分页显示
        JSONArray Jarray = PageOpt.PagingLogicProcessing(page_num,totalPage,count,tapenum,tapearrary);
        //计算总页数
        Jobject.accumulate("totalPage", totalPage);
        Jobject.accumulate("disk", Jarray);
        return Jobject;
    }
}

