package com.shdd.cfs.web.general;

import com.shdd.cfs.dto.system.SystemCapacityDto;
import com.shdd.cfs.dto.system.SystemDetail;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@Slf4j
public class SystemInfoController {
    /**
     * 获取当前容量状况
     *
     * @param dummy
     * @return
     */
    @GetMapping(value = "gg/test")
    @ApiOperation(value = "获取当前容量状况")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "dummy", value = "码表类型", required = true)
    })
    public SystemCapacityDto getSystemCapacity(String dummy) {

        //Get data from database or slave systems.
        log.info("dummy:{}", dummy);

        SystemCapacityDto systemCapacity = new SystemCapacityDto();

        SystemDetail[] systemDetails = new SystemDetail[2];
        systemDetails[0] = new SystemDetail();
        systemDetails[0].setDevType("tape");
        systemDetails[0].setDevStatus("normal");
        systemDetails[0].setCapacity(20.34);
        systemDetails[0].setUsedCapacity(4.5);
        systemDetails[0].setDevType("tape");
        systemDetails[0].setAttribe(new HashMap<>());
        systemDetails[0].getAttribe().put("haha", "xxxx");

        systemDetails[1] = new SystemDetail();
        systemDetails[1].setDevType("optical");

        systemCapacity.setDevices(systemDetails);

        return systemCapacity;
    }
}
