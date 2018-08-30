/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.dto.device.distribute.NodeInfoDetail;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j

public class DistAllDisksDetail {
    /**
     * 集群信息状态下获取分布式存储系统节点详细概况
     *
     * @param value
     * @return
     */
    @GetMapping(value = "api/dashboard/distribute/disks")
    @ApiOperation(value = "集群信息状态下获取分布式存储系统节点详细概况", notes = "分布式存储池中的所有磁盘的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "page_num", value = "翻页页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "count", value = "每页所含最大条目数", required = true)
    })

    public JSONObject DistributeStorageInfo(String value, int page_num, int count) {
        JSONObject jarrary = new JSONObject();
        NodeInfoDetail[] arrdtail = new NodeInfoDetail[6];

        arrdtail[0] = new NodeInfoDetail();
        arrdtail[1] = new NodeInfoDetail();
        arrdtail[2] = new NodeInfoDetail();
        arrdtail[3] = new NodeInfoDetail();
        arrdtail[4] = new NodeInfoDetail();
        arrdtail[5] = new NodeInfoDetail();

        arrdtail[0].setId(1);
        arrdtail[0].setCapacity(50.0);
        arrdtail[0].setHostname("Node199");
        arrdtail[0].setName("xx");
        arrdtail[0].setUsed(67.99);
        arrdtail[0].setStatus(1);

        arrdtail[1].setId(2);
        arrdtail[1].setCapacity(51.0);
        arrdtail[1].setHostname("Node201");
        arrdtail[1].setName("xx");
        arrdtail[1].setUsed(69.99);
        arrdtail[1].setStatus(0);

        arrdtail[2].setId(3);
        arrdtail[2].setCapacity(51.0);
        arrdtail[2].setHostname("Node201");
        arrdtail[2].setName("xx");
        arrdtail[2].setUsed(69.99);
        arrdtail[2].setStatus(0);

        arrdtail[3].setId(4);
        arrdtail[3].setCapacity(51.0);
        arrdtail[3].setHostname("Node201");
        arrdtail[3].setName("xx");
        arrdtail[3].setUsed(69.99);
        arrdtail[3].setStatus(0);

        arrdtail[4].setId(5);
        arrdtail[4].setCapacity(51.0);
        arrdtail[4].setHostname("Node201");
        arrdtail[4].setName("xx");
        arrdtail[4].setUsed(69.99);
        arrdtail[4].setStatus(0);

        arrdtail[5].setId(6);
        arrdtail[5].setCapacity(51.0);
        arrdtail[5].setHostname("Node201");
        arrdtail[5].setName("xx");
        arrdtail[5].setUsed(69.99);
        arrdtail[5].setStatus(0);


        //计算总页数
        int totalPage = 0;
        if (count != 0) {
            totalPage = 6 / count + 1;
        }
        System.out.println(totalPage);

        jarrary.accumulate("totalPage", totalPage);
        jarrary.accumulate("disk", arrdtail);
        return jarrary;
    }
}
