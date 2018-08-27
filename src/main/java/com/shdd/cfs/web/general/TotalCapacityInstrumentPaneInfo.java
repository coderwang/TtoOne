package com.shdd.cfs.web.general;

import com.shdd.cfs.dto.dashboard.TotalCapacityInfoDto;
import com.shdd.cfs.dto.dashboard.TotalStatusInfoDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TotalCapacityInstrumentPaneInfo {
      @GetMapping(value = "gg/total")
      @ApiOperation(value = "发送总容量信息")

      public TotalCapacityInfoDto sendTotalCapacityStatusInfo(String TotalInfo) {
            log.info(TotalInfo);

            TotalCapacityInfoDto capacityStatusInfo = new TotalCapacityInfoDto();
            TotalStatusInfoDetail[] allCapacityInfo = new TotalStatusInfoDetail[1];

            allCapacityInfo[0] = new TotalStatusInfoDetail();
            allCapacityInfo[0].setTask(1);
            allCapacityInfo[0].setRunning(1);
            allCapacityInfo[0].setCompleted(1);
            allCapacityInfo[0].setAdded(1);
            allCapacityInfo[0].setDistributedUsed(1.00);
            allCapacityInfo[0].setTapeUsed(1.00);
            allCapacityInfo[0].setDiskUsed(1.00);
            allCapacityInfo[0].setWarning(1);


            capacityStatusInfo.setData(allCapacityInfo);
            return capacityStatusInfo;
  }
}
