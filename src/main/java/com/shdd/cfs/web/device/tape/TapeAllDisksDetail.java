/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.tape;

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

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Map;

@RestController
@Slf4j
public class TapeAllDisksDetail {
    /**
     * 获取磁带库存储系统节点详细概况
     *
     * @param count
     * @return
     */
    @Autowired
    IampRequest iampRequest;
    @GetMapping(value = "api/dashboard/tape/disks")
    @ApiOperation(value = "获取磁带库存储系统节点详细概况", notes = "获取磁带库存储系统中所有磁带的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "page_num", value = "翻页页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "count", value = "每页所含最大条目数", required = true)
    })

    public JSONObject TapeSystemNodeInfo(int page_num, int count) throws MalformedURLException, DocumentException {
        String sessonKey = iampRequest.SessionKey();
        HttpResult tape_lists = iampRequest.inquiry_tape_lists(sessonKey);
        ArrayList<String> arrayList = iampRequest.get_tapes_id(tape_lists);
        // 发送Json报文
        JSONObject Joject = new JSONObject();
        //计算总页数
        int id = 0; //表示第几个磁带
        ArrayList<TapeNodeDetail> tapearrary = new ArrayList<>();
        for(String list :arrayList){
            id++;
            Map<String, String> capacity = iampRequest.get_tape_capacityinfo(tape_lists, list);
            TapeNodeDetail tape = new TapeNodeDetail();
            tape.setId(id);
            tape.setCapacity(Double.parseDouble(capacity.get("total")));
            tape.setUsed(Double.parseDouble(capacity.get("total")) - Double.parseDouble(capacity.get("remaining")));
            tape.setName(list);
            tape.setStatus(iampRequest.tape_online_info(tape_lists, list));
            tapearrary.add(tape);
        }
        int tapenum = tapearrary.size(); //磁带总个数
        int totalPage = tapenum%count==0?tapenum/count:tapenum/count+1;
        //分页发送
        JSONArray Jarray = PageOpt.PagingLogicProcessing(page_num,totalPage,count,tapenum,tapearrary);
        Joject.accumulate("totalPage", totalPage);
        Joject.accumulate("disk", Jarray);
        return Joject;
    }
}

