package com.shdd.cfs.web.device.optical;

import com.shdd.cfs.dto.device.Optical.OpticalLibraryStorageSystemStoresDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OpticalDetailedOverview {

    @GetMapping(value = "gg/opticaldetail")
    @ApiOperation(value = "获取光盘库存储系统存储详细概况")

    public JSONObject TapeDetailInfo(String value){

        JSONObject Jarrary = new JSONObject();
        OpticalLibraryStorageSystemStoresDetail[] tapearrary = new OpticalLibraryStorageSystemStoresDetail[1];
        tapearrary[0] = new OpticalLibraryStorageSystemStoresDetail();
        tapearrary[0].setName("长期保存库");
        tapearrary[0].setCapacity(5);
        tapearrary[0].setUsed(2);
        tapearrary[0].setFree(3);
        tapearrary[0].setStatus(1);

        Jarrary.accumulate("pool",tapearrary);
        return Jarrary;
    }
}
