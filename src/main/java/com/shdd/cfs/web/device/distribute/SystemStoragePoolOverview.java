package com.shdd.cfs.web.device.distribute;


import com.shdd.cfs.dto.device.Distribute.PoolGeneralOverviewDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SystemStoragePoolOverview {

    @GetMapping(value = "gg/pool")
    @ApiOperation(value = "获取分布式存储系统存储池总体概况")

    public   JSONObject PoolGeneralInfo(String value){

        JSONObject storagePool =  new JSONObject();
        PoolGeneralOverviewDetail[] jarrary = new PoolGeneralOverviewDetail[2];

        jarrary[0] = new PoolGeneralOverviewDetail();
        jarrary[1] = new PoolGeneralOverviewDetail();
        jarrary[0].setId(1);
        jarrary[0].setName("长期保存库");
        jarrary[1].setId(2);
        jarrary[1].setName("长期保存库2");

        storagePool.accumulate("poolCount","3");
        storagePool.accumulate("status",1);
        storagePool.accumulate("poolName",jarrary);
        return storagePool;
    }
}
