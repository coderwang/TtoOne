/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.data.distribute;

import com.shdd.cfs.dto.data.RootFolderName;
import com.shdd.cfs.utils.xml.XmlFromURL;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/28
 */
@RestController
@Slf4j
public class DistDirInfo {
    /**
     * 点击某文件夹时，返回该文件夹路径下文件夹
     *
     * @param path
     * @return
     */
    @GetMapping(value = "api/dashboard/distribute/dirs")
    @ApiOperation(value = "点击某文件夹时，返回该文件夹路径下文件夹", notes = "根据路径参数获取指定路径下的目录名称列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "path", value = "指定分布式下目录树全路径", required = true)
    })

    public JSONObject GetDistDirsDataByPath(String path) throws MalformedURLException, DocumentException {

        log.info(path);

        //从指定URL获取xml数据，并进行解析
        XmlFromURL xmlFromURL = new XmlFromURL();
        Document document = xmlFromURL.GetXmlDocument("http://www.w3school.com.cn/example/xmle/note.xml");

        //TODO 确认xml字段，并进行解析
        xmlFromURL.GetStringsFromXml(document, "to");
        xmlFromURL.GetStringsFromXml(document, "from");
        xmlFromURL.GetStringsFromXml(document, "heading");

        //将获取到的数据进行填充
        JSONObject rootFolder = new JSONObject();

        RootFolderName[] rootforder = new RootFolderName[1];
        rootforder[0] = new RootFolderName();
        rootforder[0].setId(1);
        rootforder[0].setName("xx");
        /*将文件夹数组塞入Json对象中*/
        rootFolder.accumulate("folder", rootforder);
        /* 发送Json 协议*/
        return rootFolder;
    }
}
