/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.data.distribute;

import com.shdd.cfs.config.DateConfig;
import com.shdd.cfs.dto.data.DirPathDetailInfo;
import com.shdd.cfs.dto.data.FilePathDetailInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shdd.cfs.utils.base.UnitHandle.SendCurrentFolderName;
import static com.shdd.cfs.utils.base.UnitHandle.getCurrentPathFilename;


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
    @Autowired
    private DateConfig config;
    @GetMapping(value = "api/dashboard/distribute/rootdirs")
    @ApiOperation(value = "获取目录下的文件夹名", notes = "根据路径参数获取分布式根路径下的目录名称列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "path", value = "指定分布式下目录树全路径", required = true)
    })

    public JSONObject GetDistDirInfo(String val) {

        String path= config.getdistributemount();
        JSONObject rootpath = new JSONObject();
        // 获取根目录下文件夹赋值给Json数组中
        DirPathDetailInfo[] rootFolder = SendCurrentFolderName(path);
        FilePathDetailInfo[] rootFile =  getCurrentPathFilename(path);
        //将文件夹数组塞入Json对象中
        rootpath.accumulate("folder",rootFolder);
        rootpath.accumulate("file",rootFile);
        // 发送Json 协议
        return rootpath;
    }
}
