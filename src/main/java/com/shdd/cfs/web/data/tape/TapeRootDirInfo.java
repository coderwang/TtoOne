/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.data.tape;

import com.shdd.cfs.dto.data.DirPathDetailInfo;
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
public class TapeRootDirInfo {

    /**
     * 获取目录下文件夹名称
     *
     * @param val
     * @return
     */

    @GetMapping(value = "api/dashboard/tape/rootdirs")
    @ApiOperation(value = "获取目录下的文件夹名", notes = "根据路径参数获取磁带库存储系统根路径下的目录名称列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "path", value = "指定磁带库下目录树全路径", required = true)
    })
    public JSONObject GetTapeRootDirInfo(String val) {
        log.info(val);

        JSONObject rootFolder = new JSONObject();

        /* 将文件夹名赋值给Json数组中*/
        DirPathDetailInfo[] rootforder = new DirPathDetailInfo[1];
        rootforder[0] = new DirPathDetailInfo();
        rootforder[0].setId(1);
        rootforder[0].setName("xx");
        /*将文件夹数组塞入Json对象中*/
        rootFolder.accumulate("folder", rootforder);
        /* 发送Json 协议*/
        return rootFolder;
    }
}
