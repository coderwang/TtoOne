/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.tape.TapeNodeDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RestController
@Slf4j
public class TapeAllDisksDetail {
    /**
     * 获取磁带库存储系统节点详细概况
     *
     * @param value
     * @return
     */
    @GetMapping(value = "api/dashboard/tape/disks")
    @ApiOperation(value = "获取磁带库存储系统节点详细概况", notes = "获取磁带库存储系统中所有磁带的详细信息")

    public JSONObject TapeSystemNodeInfo(String value) {
        JSONObject Jarrary = new JSONObject();
        TapeNodeDetail[] tapenode = new TapeNodeDetail[1];

        tapenode[0] = new TapeNodeDetail();
        tapenode[0].setId(1);
        tapenode[0].setCapacity(80.0);
        tapenode[0].setUsed(30.0);
        tapenode[0].setName("xx");
        tapenode[0].setStatus(1);

        Jarrary.accumulate("disk", tapenode);
        return Jarrary;
    }
}

