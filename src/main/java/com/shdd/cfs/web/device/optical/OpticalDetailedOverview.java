package com.shdd.cfs.web.device.optical;

import com.shdd.cfs.dto.device.optical.OpticalLibraryStorageSystemStoresDetail;
import com.shdd.cfs.utils.json.GetJsonMessage;
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

    public JSONObject OpticalDetailInfo(String value) {
        JSONObject getbasicinfo = new JSONObject();
        JSONObject getcapacity = new JSONObject();
        GetJsonMessage jsoninfo = new GetJsonMessage();
        String cmdstr = "{\"protoname\":\"basicinfo\",\"jukeid\":\"001\"}";// 获取光盘库型号
        String capacity = "{\"protoname\":\"nodeconnect\"}"; //获取光盘库容量, 节点状态
        getbasicinfo = GetJsonMessage.GetJsonStr("192.168.100.199", 8000, cmdstr);
        getcapacity = GetJsonMessage.GetJsonStr("192.168.100.199", 8000, capacity);
        System.out.println(getbasicinfo);
        System.out.println(getcapacity);
        //1.工作中 2.服务未启动 -98.创建中 3.硬盘报警 4.网络错误 -99.删除中
        //光盘库1为工作中，1之外的状态认为是非工作状态
        int valuestauts = Integer.parseInt(getcapacity.getString("nodestatus"));//
        if (valuestauts != 1) {
            valuestauts = 2;
        }
        JSONObject Jarrary = new JSONObject();
        OpticalLibraryStorageSystemStoresDetail[] tapearrary = new OpticalLibraryStorageSystemStoresDetail[1];
        tapearrary[0] = new OpticalLibraryStorageSystemStoresDetail();
        tapearrary[0].setName(getbasicinfo.getString("label"));
        tapearrary[0].setCapacity(Double.parseDouble(getcapacity.getString("totalinfo")));
        tapearrary[0].setUsed(Double.parseDouble(getcapacity.getString(("usedinfo"))));
        tapearrary[0].setFree(Double.parseDouble(getcapacity.getString("totalinfo")) - Double.parseDouble(getcapacity.getString(("usedinfo"))));
        tapearrary[0].setStatus(valuestauts);
        //返回存储池数组
        Jarrary.accumulate("pool", tapearrary);
        return Jarrary;
    }
}
