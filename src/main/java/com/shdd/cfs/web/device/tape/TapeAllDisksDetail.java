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
        //从下级磁带库系统接口获取数据
        String sessonKey = iampRequest.SessionKey();
        //磁带库中所有磁带
        HttpResult allTapesList = iampRequest.inquiry_tape_lists(sessonKey);
        //磁带ID列表
        ArrayList<String> tapesIdList = iampRequest.get_tapes_id(allTapesList);
        //翻页
        int i = 0;
        int page_count = 0;

        Double usedSize = 0.0;

        ArrayList<DiskDetailInfo> poolTapesList = new ArrayList<>();
        for (String tapeID : tapesIdList) {
            Map<String, String> capacity = iampRequest.get_tape_capacityinfo(allTapesList, tapeID);

            //翻页
            i++;
            if ((i + 1) <= (page_num - 1) * count) {
                continue;
            }

            DiskDetailInfo tape = new DiskDetailInfo();
            tape.setId(tapeID);
            tape.setCapacity(capacity.get("total"));

            usedSize = Double.parseDouble(capacity.get("total")) - Double.parseDouble(capacity.get("remaining"));
            tape.setUsed(usedSize.toString());
            tape.setName(tapeID);
            tape.setStatus(iampRequest.tape_online_info(allTapesList, tapeID));

            poolTapesList.add(tape);
            page_count += 1;
            if (page_count == count) {
                break;
            }
        }
        int tapenum = poolTapesList.size(); //磁带总个数
        int totalPage = tapenum % count == 0 ? tapenum / count : tapenum / count + 1;

        //数据打包
        JSONObject RstObject = new JSONObject();
        RstObject.accumulate("totalPage", totalPage);
        RstObject.accumulate("disk", poolTapesList);
        return RstObject;
    }
}

