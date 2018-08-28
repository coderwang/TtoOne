package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.dto.device.distribute.HostInfoDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SystemHostOverview {

        @GetMapping(value = "device/distribute/device/node01")
        @ApiOperation(value = "获取分布式存储系统主机概况")

        public JSONObject GetHostfDistribute(String value){

            JSONObject hostInfo = new JSONObject();
            HostInfoDetail[] arrdetail = new HostInfoDetail[1];
            arrdetail[0] = new HostInfoDetail();
            arrdetail[0].setName("node01");
            arrdetail[0].setCpuType("phytium");
            arrdetail[0].setCpuCount(2);
            arrdetail[0].setMemCapacity(20.5);
            arrdetail[0].setHardDiskCount(12);
            arrdetail[0].setStatus(1);

            hostInfo.accumulate("host",arrdetail);
            return hostInfo;
        }
    }
