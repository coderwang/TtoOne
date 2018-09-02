/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.dto.device.distribute.HostNodeInfoDetail;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j

public class DistSpecificPoolDisksDetail {
    /**
     * 主机信息状态下获取分布式存储系统节点详细概况
     *
     * @param value
     * @return
     */
    @GetMapping(value = "api/dashboard/distribute/pool/disks")
    @ApiOperation(value = "主机信息状态下获取分布式存储系统节点详细概况", notes = "分布式系统指定存储池中的磁盘详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "poolid", value = "分布式存储池ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "page_num", value = "翻页页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "count", value = "每页所含最大条目数", required = true)
    })

    public JSONObject DistributeStorageInfo(int poolid, int page_num, int count) {
        JSONObject jarrary = new JSONObject();
        HostNodeInfoDetail[] arrayDiskInfo = new HostNodeInfoDetail[6];

        arrayDiskInfo[0] = new HostNodeInfoDetail();
        arrayDiskInfo[1] = new HostNodeInfoDetail();
        arrayDiskInfo[2] = new HostNodeInfoDetail();
        arrayDiskInfo[3] = new HostNodeInfoDetail();
        arrayDiskInfo[4] = new HostNodeInfoDetail();
        arrayDiskInfo[5] = new HostNodeInfoDetail();

        arrayDiskInfo[0].setId(1);
        arrayDiskInfo[0].setCapacity(50.0);
        arrayDiskInfo[0].setHostname("Node199");
        arrayDiskInfo[0].setName("xx");
        arrayDiskInfo[0].setUsed(67.99);
        arrayDiskInfo[0].setStatus(1);

        arrayDiskInfo[1].setId(2);
        arrayDiskInfo[1].setCapacity(51.0);
        arrayDiskInfo[1].setHostname("Node201");
        arrayDiskInfo[1].setName("xx");
        arrayDiskInfo[1].setUsed(69.99);
        arrayDiskInfo[1].setStatus(0);

        arrayDiskInfo[2].setId(3);
        arrayDiskInfo[2].setCapacity(51.0);
        arrayDiskInfo[2].setHostname("Node201");
        arrayDiskInfo[2].setName("xx");
        arrayDiskInfo[2].setUsed(69.99);
        arrayDiskInfo[2].setStatus(0);

        arrayDiskInfo[3].setId(4);
        arrayDiskInfo[3].setCapacity(51.0);
        arrayDiskInfo[3].setHostname("Node201");
        arrayDiskInfo[3].setName("xx");
        arrayDiskInfo[3].setUsed(69.99);
        arrayDiskInfo[3].setStatus(0);

        arrayDiskInfo[4].setId(5);
        arrayDiskInfo[4].setCapacity(51.0);
        arrayDiskInfo[4].setHostname("Node201");
        arrayDiskInfo[4].setName("xx");
        arrayDiskInfo[4].setUsed(69.99);
        arrayDiskInfo[4].setStatus(0);

        arrayDiskInfo[5].setId(6);
        arrayDiskInfo[5].setCapacity(51.0);
        arrayDiskInfo[5].setHostname("Node201");
        arrayDiskInfo[5].setName("xx");
        arrayDiskInfo[5].setUsed(69.99);
        arrayDiskInfo[5].setStatus(0);

        //计算总页数
        int totalPage = 0;
        if (count != 0) {
            totalPage = 6 / count + 1;
        }
        System.out.println(totalPage);

        jarrary.accumulate("totalPage", totalPage);
        jarrary.accumulate("disk", arrayDiskInfo);
        return jarrary;
    }
}
