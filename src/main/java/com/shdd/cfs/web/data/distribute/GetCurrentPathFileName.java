/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.data.distribute;

import com.shdd.cfs.dto.data.CurrentPathFileName;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;


/**
 * @author: wangpeng
 * @version: 1.0 2018/8/28
 */
@RestController
@Slf4j
public class GetCurrentPathFileName {


    /**
     * 获取目录下文件名称
     * @param path
     * @return
     */

    @GetMapping(value = "api/dashboard/distribute/files")
    @ApiOperation(value = "获取目录下的文件名")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "path", value = "码表类型", required = true)
    })

    public JSONObject GetDistFilesInfo(String path) {
        log.info(path);
        JSONObject jfile = new JSONObject();

        /* 将文件名和文件属性赋值给Json数组中*/
        CurrentPathFileName[] fileNameArr = new CurrentPathFileName[1];
        fileNameArr[0] = new CurrentPathFileName();
        fileNameArr[0].setId(1);
        fileNameArr[0].setName("xx");
        fileNameArr[0].setType("txt");

        /*将文件数组塞入Json对象中*/
        jfile.accumulate("file", fileNameArr);
        /* 发送Json 协议*/
        return jfile;
    }
}
