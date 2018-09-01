/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.tape.TapeNodeDetail;
import com.shdd.cfs.utils.xml.XmlFromURL;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "page_num", value = "翻页页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "count", value = "每页所含最大条目数", required = true)
    })

    public JSONObject TapeSystemNodeInfo(int page_num, int count) throws MalformedURLException, DocumentException {

        ////从指定URL获取xml数据，并进行解析
        //XmlFromURL xmlFromURL = new XmlFromURL();
        //Document document = xmlFromURL.GetXmlDocument("http://www.w3school.com.cn/example/xmle/note.xml");
//
        ////TODO 确认xml字段，并进行解析
        //xmlFromURL.GetStringsFromXml(document, "to");
        //xmlFromURL.GetStringsFromXml(document, "from");
        //xmlFromURL.GetStringsFromXml(document, "heading");

        JSONObject Jarrary = new JSONObject();
        TapeNodeDetail[] tapenode = new TapeNodeDetail[6];

        tapenode[0] = new TapeNodeDetail();
        tapenode[0].setId(1);
        tapenode[0].setCapacity(80.0);
        tapenode[0].setUsed(30.0);
        tapenode[0].setName("xx");
        tapenode[0].setStatus(1);

        tapenode[1] = new TapeNodeDetail();
        tapenode[1].setId(2);
        tapenode[1].setCapacity(80.0);
        tapenode[1].setUsed(30.0);
        tapenode[1].setName("xx");
        tapenode[1].setStatus(1);

        tapenode[2] = new TapeNodeDetail();
        tapenode[2].setId(3);
        tapenode[2].setCapacity(80.0);
        tapenode[2].setUsed(30.0);
        tapenode[2].setName("xx");
        tapenode[2].setStatus(1);

        tapenode[3] = new TapeNodeDetail();
        tapenode[3].setId(4);
        tapenode[3].setCapacity(80.0);
        tapenode[3].setUsed(30.0);
        tapenode[3].setName("xx");
        tapenode[3].setStatus(1);

        tapenode[4] = new TapeNodeDetail();
        tapenode[4].setId(5);
        tapenode[4].setCapacity(80.0);
        tapenode[4].setUsed(30.0);
        tapenode[4].setName("xx");
        tapenode[4].setStatus(1);

        tapenode[5] = new TapeNodeDetail();
        tapenode[5].setId(6);
        tapenode[5].setCapacity(80.0);
        tapenode[5].setUsed(30.0);
        tapenode[5].setName("xx");
        tapenode[5].setStatus(1);

        //计算总页数
        int totalPage = 0;
        if (count != 0) {
            totalPage = 6 / count + 1;
        }
        System.out.println(totalPage);

        Jarrary.accumulate("totalPage", totalPage);

        Jarrary.accumulate("disk", tapenode);
        return Jarrary;
    }
}

