package com.shdd.cfs.web.device.tape;


import com.shdd.cfs.dto.device.optical.OpticalNodeDetail;
import com.shdd.cfs.dto.device.tape.TapeNodeDetail;
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

    public JSONObject TapeSystemNodeInfo(String value) {
        JSONObject Jarrary = new JSONObject();
        TapeNodeDetail[] tapenode = new TapeNodeDetail[1];

        tapenode[0] = new TapeNodeDetail();
        tapenode[0].setId(1);
        tapenode[0].setCapacity(80.0);
        tapenode[0].setUsed(30.0);
        tapenode[0].setName("xx");
        tapenode[0].setStatus(1);

        Jarrary.accumulate("disk", tapenode);
        return Jarrary;
    }
}
