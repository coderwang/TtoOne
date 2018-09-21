/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.data.optical;

import com.shdd.cfs.dto.data.DirPathDetailInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shdd.cfs.utils.base.UnitHandle.SendCurrentFolderName;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/28
 */
@RestController
@Slf4j
public class CDDirInfo {
    /**
     * 点击某文件夹时，返回该文件夹路径下文件夹
     *
     * @param path
     * @return
     */
    @GetMapping(value = "api/dashboard/disk/dirs")
    @ApiOperation(value = "点击某文件夹时，返回该文件夹路径下文件夹", notes = "根据路径参数获取指定路径下的目录名称列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "path", value = "指定光盘库下目录树全路径", required = true)
    })

    public JSONObject GetDiskDirsDataByPath(String path) {

        log.info(path);

        //将获取到的数据进行填充
        JSONObject folderName = new JSONObject();
        DirPathDetailInfo[] rootFolder = SendCurrentFolderName(path);

        /*将文件夹数组塞入Json对象中*/
        folderName.accumulate("folder", rootFolder);
        /* 发送Json 协议*/
        return folderName;
    }
}
