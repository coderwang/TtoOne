package com.shdd.cfs.web.device.optical;

import com.shdd.cfs.dto.device.optical.OpticalSystemInfoDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OpticalLibraryStorageSystemOverview {

    @GetMapping(value = "gg/opticalinfo")
    @ApiOperation(value = "获取光盘库存储系统概况")
    public JSONObject TapeLibraryInfo(String val){
        /*光盘库存储信息组织接口*/


       /*光盘库存储信息发送接口*/
        JSONObject Jarrary = new JSONObject();
        OpticalSystemInfoDetail[] tapearrary = new OpticalSystemInfoDetail[1];
        tapearrary[0] = new OpticalSystemInfoDetail();
        tapearrary[0].setCpuCount(2);
        tapearrary[0].setCpuType("phytium");
        tapearrary[0].setHardDiskCount(7);
        tapearrary[0].setMemCapacity(342.2);
        tapearrary[0].setName("node111");
        tapearrary[0].setStatus(1);

        Jarrary.accumulate("optical",tapearrary);
       return Jarrary;
    }
}
