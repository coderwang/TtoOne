/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.data.optical;

import com.shdd.cfs.dto.data.RootFolderName;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/28
 */
@RestController
@Slf4j
public class DiskDirInfo {
    /**
     * 点击某文件夹时，返回该文件夹路径下文件夹
     */
    @GetMapping(value = "api/dashboard/disk/dirs")
    @ApiOperation(value = "点击某文件夹时，返回该文件夹路径下文件夹")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "path", value = "码表类型", required = true)
    })

    public JSONObject GetDiskDirsDataByPath(String path) {

        log.info(path);

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
