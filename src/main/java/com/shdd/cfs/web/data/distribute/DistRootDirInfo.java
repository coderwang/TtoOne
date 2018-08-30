/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.data.distribute;

import com.shdd.cfs.dto.data.RootFolderName;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
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
public class DistRootDirInfo {

    /**
     * 获取目录下文件夹名称
     *
     * @param val
     * @return
     */

    @GetMapping(value = "api/dashboard/distribute/rootdirs")
    @ApiOperation(value = "获取目录下的文件夹名", notes = "根据路径参数获取分布式根路径下的目录名称列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "path", value = "指定分布式下目录树全路径", required = true)
    })

    public JSONObject GetDistDirInfo(String val) {
        log.info(val);

        JSONObject rootFolder = new JSONObject();

        /* 将文件夹名赋值给Json数组中*/
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
