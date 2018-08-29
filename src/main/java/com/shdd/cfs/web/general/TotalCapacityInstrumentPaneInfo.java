package com.shdd.cfs.web.general;

import com.shdd.cfs.dto.dashboard.TotalCapacityInfoDto;
import com.shdd.cfs.dto.dashboard.TotalStatusInfoDetail;
import com.shdd.cfs.utils.json.GetJsonMessage;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TotalCapacityInstrumentPaneInfo {

      @GetMapping(value = "api/dashboard/capacitystatus")
      @ApiOperation(value = "发送总容量信息")

    public TotalCapacityInfoDto sendTotalCapacityStatusInfo(String TotalInfo) {
        log.info(TotalInfo);
        JSONObject getnodecapacity = new JSONObject();
        String nodecapacity = "{\"protoname\":\"nodeconnect\"}"; //获取光盘库容量, 节点状态
        getnodecapacity = GetJsonMessage.GetJsonStr("192.168.100.199", 8000, nodecapacity);//获取光盘库节点容量信息
        String baseurl = "";
        String disstorageinfo = baseurl + "api/monitor/clustes/storage/";//获取分布式容量
        String discapacity = GetJsonMessage.getURLContent(disstorageinfo);
        JSONObject disjsoncapacity = JSONObject.fromObject(discapacity); //获取回传的json报文
        TotalCapacityInfoDto capacityStatusInfo = new TotalCapacityInfoDto();
        TotalStatusInfoDetail[] allCapacityInfo = new TotalStatusInfoDetail[1];

        allCapacityInfo[0] = new TotalStatusInfoDetail();
        allCapacityInfo[0].setTask(1);
        allCapacityInfo[0].setRunning(1);
        allCapacityInfo[0].setCompleted(1);
        allCapacityInfo[0].setAdded(1);
        allCapacityInfo[0].setDistributedUsed(Double.parseDouble(disjsoncapacity.getString("storage_used")));
        allCapacityInfo[0].setTapeUsed(1.00);
        allCapacityInfo[0].setDiskUsed(Double.parseDouble(getnodecapacity.getString(("usedinfo"))));
        allCapacityInfo[0].setWarning(1);


        capacityStatusInfo.setData(allCapacityInfo);
        return capacityStatusInfo;
    }
}
