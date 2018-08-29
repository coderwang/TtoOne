package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.dto.device.distribute.SystemClusterInfoDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StorageSystemClusteringInfo {
    /**
     * 获取分布式存储系统集群情况
     *
     * @param value
     * @return
     */
    @GetMapping(value = "device/distribute/hosts")
    @ApiOperation(value = "获取分布式存储系统集群情况")

    public JSONObject GetInfoOfDistribute(String value) {

        JSONObject distributeStorageInfo = new JSONObject();
        SystemClusterInfoDetail[] arrdetail = new SystemClusterInfoDetail[2];
        arrdetail[0] = new SystemClusterInfoDetail();
        arrdetail[1] = new SystemClusterInfoDetail();
        arrdetail[0].setId(1);
        arrdetail[0].setName("xx");
        arrdetail[0].setStatus(1);
        arrdetail[1].setId(2);
        arrdetail[1].setName("xxx");
        arrdetail[1].setStatus(1);

        distributeStorageInfo.accumulate("colonyCount", 3);

        distributeStorageInfo.accumulate("status", 1);
        distributeStorageInfo.accumulate("colony", arrdetail);
        return distributeStorageInfo;
    }
}
