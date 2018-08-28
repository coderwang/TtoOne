package com.shdd.cfs.web.device.distribute;


import com.shdd.cfs.dto.device.distribute.PoolDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DetailedOverviewOfTheStoragePool {

    @GetMapping(value = "dashboard/pools/1")
    @ApiOperation(value = "获取分布式存储系统存储池详细概况")

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

        Jobject.accumulate("pool",jarrary);
        return Jobject;
    }

}
