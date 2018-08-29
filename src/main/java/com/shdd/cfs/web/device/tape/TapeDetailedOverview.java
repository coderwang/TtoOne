package com.shdd.cfs.web.device.tape;

import com.shdd.cfs.dto.device.tape.TapeLibraryStorageSystemStoresDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TapeDetailedOverview {

    @GetMapping(value = "dashboard/tape/1")
    @ApiOperation(value = "获取磁带库存储系统存储详细概况")

    public JSONObject TapeDetailInfo(String value) {

        JSONObject Jarrary = new JSONObject();
        TapeLibraryStorageSystemStoresDetail[] tapearrary = new TapeLibraryStorageSystemStoresDetail[1];
        tapearrary[0] = new TapeLibraryStorageSystemStoresDetail();
        tapearrary[0].setName("长期保存库");
        tapearrary[0].setCapacity(5.0);
        tapearrary[0].setUsed(2.0);
        tapearrary[0].setFree(3.0);
        tapearrary[0].setStatus(1);

        Jarrary.accumulate("pool", tapearrary);
        return Jarrary;
    }
}
