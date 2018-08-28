package com.shdd.threeinone.web.DeviceVisualization.OpticalDevice;


import com.shdd.threeinone.HandleTools.JsonMessageHandleTool.GetJsonMessage;
import com.shdd.threeinone.dto.DevicesVisualization.Optical.OpticalNodeDetail;
import com.shdd.threeinone.dto.SystemDetail;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DetailedOverviewOfOpticalSystemNodes {

    @GetMapping(value = "gg/opticalnode")
    @ApiOperation(value = "获取光盘库存储系统节点详细概况")

    public JSONObject TapeSystemNodeInfo(String value){
        /*组织获取光盘库节点信息*/
        JSONObject getjsoninfo = new JSONObject();
        JSONObject getcapacity = new JSONObject();
        GetJsonMessage jsoninfo = new GetJsonMessage();

        String cmdstr="{\"protoname\":\"basicinfo\",\"jukeid\":\"001\"}";// 获取光盘库型号
        String capacity = "{\"protoname\":\"nodeconnect\"}"; //获取光盘库容量, 节点状态

        getjsoninfo = GetJsonMessage.GetJsonStr("192.168.100.199",8000,cmdstr);
        getcapacity = GetJsonMessage.GetJsonStr("192.168.100.199",8000,capacity);
        System.out.println("型号" + getjsoninfo);
        System.out.println("容量信息" + getcapacity);
//        valuestauts = String.valueOf(getcapacity.getString("nodestatus"));
//        if (valuestauts != 1) {
//            value = 2;
//        }

        /*组织发送光盘库节点信息*/
        JSONObject Jarrary = new JSONObject();
        OpticalNodeDetail[] tapenode = new OpticalNodeDetail[1];

        tapenode[0] = new OpticalNodeDetail();
        tapenode[0].setId(1);
        tapenode[0].setCapacity(80);
        tapenode[0].setUsed(30);
        tapenode[0].setName(getjsoninfo.getString("label"));
        tapenode[0].setStatus(1);

        Jarrary.accumulate("disk",tapenode);
        return Jarrary;
    }
}
