package com.shdd.threeinone.web.DeviceVisualization.DistributeDevice;

import com.shdd.threeinone.dto.DevicesVisualization.Distribute.NodeInfoDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j

public class DistributedStorageSystemNodesDetail {
    @GetMapping(value = "dashboard/distribute/node")
    @ApiOperation(value = "集群信息状态下获取分布式存储系统节点详细概况")
    public JSONObject DistributeStorageInfo(String value){
        JSONObject jarrary = new JSONObject();
        NodeInfoDetail[] arrdtail = new NodeInfoDetail[2];

        arrdtail[0] = new NodeInfoDetail();
        arrdtail[1] = new NodeInfoDetail();

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


        jarrary.accumulate("disk",arrdtail);
        return jarrary;
    }
}
