package com.shdd.cfs.web.general;

import com.shdd.cfs.dto.dashboard.WarnInfoDetail;
import com.shdd.cfs.dto.dashboard.WarningInfoDto;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class InstrumentWarningInfo {
    /**
     * 返回仪表盘警告信息
     *
     * @param warn
     * @return
     */
    @GetMapping(value = "gg/warning")
    @ApiOperation(value = "返回仪表盘警告信息")

    public WarningInfoDto sendWarningInfo(String warn) {
        log.info(warn);

        WarningInfoDto WarningInfo = new WarningInfoDto();
        WarnInfoDetail[] ArrWarnInfo = new WarnInfoDetail[3];
        ArrWarnInfo[0] = new WarnInfoDetail();
        ArrWarnInfo[1] = new WarnInfoDetail();
        ArrWarnInfo[2] = new WarnInfoDetail();

        ArrWarnInfo[0].setName("distributed");
        ArrWarnInfo[0].setMessage(1);
        ArrWarnInfo[1].setName("tape");
        ArrWarnInfo[1].setMessage(1);
        ArrWarnInfo[2].setName("optical");
        ArrWarnInfo[2].setMessage(1);

        WarningInfo.setStatus(ArrWarnInfo);
        return WarningInfo;
    }
}
