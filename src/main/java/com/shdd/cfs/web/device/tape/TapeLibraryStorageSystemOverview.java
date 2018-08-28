package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.optical.OpticalSystemInfoDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TapeLibraryStorageSystemOverview {

    @GetMapping(value = "dashboard/tapes")
    @ApiOperation(value = "获取磁带库存储系统概况")
    public JSONObject TapeLibraryInfo(String val) {
        JSONObject Jarrary = new JSONObject();
        OpticalSystemInfoDetail[] tapearrary = new OpticalSystemInfoDetail[1];
        tapearrary[0] = new OpticalSystemInfoDetail();
        tapearrary[0].setCpuCount(2);
        tapearrary[0].setCpuType("phytium");
        tapearrary[0].setHardDiskCount(7);
        tapearrary[0].setMemCapacity(342.2);
        tapearrary[0].setName("node111");
        tapearrary[0].setStatus(1);

        Jarrary.accumulate("tape", tapearrary);
        return Jarrary;
    }
}
