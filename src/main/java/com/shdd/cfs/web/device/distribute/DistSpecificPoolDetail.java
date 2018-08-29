package com.shdd.cfs.web.device.distribute;


import com.shdd.cfs.dto.device.distribute.PoolDetail;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class DistSpecificPoolDetail {
    /**
     * 获取分布式存储系统存储池详细概况
     *
     * @param value
     * @return
     */
    @GetMapping(value = "api/dashboard/distribute/pool")
    @ApiOperation(value = "获取分布式存储系统存储池详细概况", notes = "获取分布式存储系统指定存储池的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "poolid", value = "指定分布式存储池ID", required = true)
    })

    public JSONObject SendDetailInfoOfPool(String value) {

        log.info(value);

        JSONObject Jobject = new JSONObject();
        PoolDetail[] jarrary = new PoolDetail[1];

        jarrary[0] = new PoolDetail();
        jarrary[0].setName("长期保存库");
        jarrary[0].setCapacity(5);
        jarrary[0].setFree(2);
        jarrary[0].setUsed(3);
        jarrary[0].setStatus(1);

        Jobject.accumulate("pool", jarrary);
        return Jobject;
    }

}
