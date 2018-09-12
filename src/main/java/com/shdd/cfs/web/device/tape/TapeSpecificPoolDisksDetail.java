/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.DiskDetailInfo;
import com.shdd.cfs.utils.xml.iamp.HttpResult;
import com.shdd.cfs.utils.xml.iamp.IampRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
        ArrayList<DiskDetailInfo> poolTapesList = new ArrayList<>();

        //翻页
        int i = 0;
        int page_count = 0;
        Double usedSize = 0.0;

        for (String tapeID : tapesIdList) {
            String groupId = iampRequest.get_group_id(allTapesList, tapeID);
            //匹配目标磁带组
            if (groupId.equals(poolid)) {
                Map<String, String> capacity = iampRequest.get_tape_capacityinfo(allTapesList, tapeID);

                //翻页
                i++;
                if ((i + 1) <= (page_num - 1) * count) {
                    continue;
                }

                DiskDetailInfo tape = new DiskDetailInfo();

                //处理从下级系统获取的数据
                tape.setId(tapeID);

                Double capacityTotal = Double.parseDouble(capacity.get("total")) / 1024 / 1024;
                tape.setCapacity(capacityTotal);
                usedSize = (Double.parseDouble(capacity.get("total")) - Double.parseDouble(capacity.get("remaining"))) / 1024 / 1024;
                tape.setUsed(usedSize);
                tape.setName(tapeID);
                tape.setStatus(iampRequest.tape_online_info(allTapesList, tapeID));

                //将符合条件的数据加入发送缓存
                poolTapesList.add(tape);
                page_count += 1;
                if (page_count == count) {
                    break;
                }
            }
        }
        //指定磁带组中磁带总个数
        int tapesCount = poolTapesList.size();
        int totalPage = tapesCount % count == 0 ? tapesCount / count : tapesCount / count + 1;

        //数据打包
        JSONObject RstObject = new JSONObject();

        RstObject.accumulate("totalPage", totalPage);
        RstObject.accumulate("disk", poolTapesList);
        return RstObject;
    }
}

