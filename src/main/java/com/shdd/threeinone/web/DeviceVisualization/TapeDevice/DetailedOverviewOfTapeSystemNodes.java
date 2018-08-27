package com.shdd.threeinone.web.DeviceVisualization.TapeDevice;


import com.shdd.threeinone.dto.DevicesVisualization.Optical.OpticalNodeDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DetailedOverviewOfTapeSystemNodes {

    @GetMapping(value = "dashboard/tape/detail/1")
    @ApiOperation(value = "获取磁带库存储系统节点详细概况")

    public JSONObject TapeSystemNodeInfo(String value){
        JSONObject Jarrary = new JSONObject();
        OpticalNodeDetail[] tapenode = new OpticalNodeDetail[1];

        tapenode[0] = new OpticalNodeDetail();
        tapenode[0].setId(1);
        tapenode[0].setCapacity(80);
        tapenode[0].setUsed(30);
        tapenode[0].setName("xx");
        tapenode[0].setStatus(1);

        Jarrary.accumulate("disk",tapenode);
        return Jarrary;
    }
}
