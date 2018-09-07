/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.demo;

import com.shdd.cfs.utils.storage.Store;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xphi
 * @version: 1.0 2018/9/4
 */
@RestController
@Slf4j
public class DemoController {

    @Autowired
    private Store store;

    @PostMapping(value = "api/demo/set_storage/{key}")
    @ApiOperation(value = "在内存中临时保存数据的示例")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string",
                    name = "key", value = "存储的键", required = true),
            @ApiImplicitParam(paramType = "form", dataType = "string",
                    name = "value", value = "存储的值", required = true)
    })
    public JSONObject setStorage(@PathVariable String key, String value) {
        log.info("key is "+key);
        log.info("volue is "+value);
        Boolean result = store.set("demoStore", key, value);
        JSONObject returnObj = new JSONObject();
        returnObj.accumulate("ok", result);
        return returnObj;
    }

    //@Scheduled(cron = "0/5 * * * * ? ")//每五秒触发
    public void printStore() {
        String value = store.get("demoStore", "testKey");
        log.info("The value stored with 'testKey' is '{}'.", value);

    }
}
