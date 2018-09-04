/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.tape.TapeNodeDetail;
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
        JSONArray  jarray = new JSONArray();
        //计算总页数
        int tapenum = arrayList.size(); //磁带总个数
        int totalPage = tapenum%count==0?tapenum/count:tapenum/count+1;
        int id = 0; //表示第几个磁带
        if(page_num <= totalPage)
        {
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
            if(tapenum % count == 0) {// 磁带个数正好可以按照页数显示时
                int num = (page_num - 1) *count;
                int whilenum = num + count;
                for (int i = num ; i < whilenum; i++) {
                    jarray.add(tapearrary.get(num));
                }
            }else {// 当磁带个数不能整页显示时
            	int rem = tapenum % count; //取余数
            	int mod = tapenum / count; //取模
				if(mod == 0){
                   for( int i = 0 ; i < count - 1 ; i++){
                      jarray.add(tapearrary.get(i));
                   }
                }else {
					   if (page_num <= mod){
                           int initnum = (page_num-1) * count;
                           for(int i = initnum; i< count; i++){
                               jarray.add(tapearrary.get(i));
                           }
                       }else{
					            int initnum = (page_num - 1) * count;
                                int remnum = initnum + rem;
                                for(int i = initnum; i < remnum; i++){
                                jarray.add(tapearrary.get(i));
                            }
					   }
                }
            }
        }else {
            System.out.println("请求的页码不存在！");
        }
        Joject.accumulate("totalPage", totalPage);
        Joject.accumulate("disk", jarray);
        return Joject;
    }
}

