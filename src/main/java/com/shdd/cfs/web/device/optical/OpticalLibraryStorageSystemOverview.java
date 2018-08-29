package com.shdd.cfs.web.device.optical;

import com.shdd.cfs.dto.device.optical.OpticalSystemInfoDetail;
import com.shdd.cfs.utils.json.GetJsonMessage;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OpticalLibraryStorageSystemOverview {

    @GetMapping(value = "api/dashboard/disks")
    @ApiOperation(value = "获取光盘库存储系统概况")
    public JSONObject TapeLibraryInfo(String val) {
        //光盘库存储信息组织接口
        JSONObject getbasicinfo = new JSONObject();
        JSONObject getnodecapacity = new JSONObject();
        JSONObject getcpuinfo = new JSONObject();
        JSONObject getmeminfo = new JSONObject();
        GetJsonMessage jsoninfo = new GetJsonMessage();
        String cmdstr = "{\"protoname\":\"basicinfo\",\"jukeid\":\"001\"}";// 获取光盘库型号
        String nodecapacity = "{\"protoname\":\"nodeconnect\"}"; //获取光盘库容量, 节点状态
        String cpuinfo = "{\"protoname\":\"cpuinfo\"}";    //获取单个节点CPU型号和总个数
        String memcapacityinfo = "{\"protoname\":\"memcapacity\"}";// 获取单节点内存容量
        getbasicinfo = GetJsonMessage.GetJsonStr("192.168.100.199", 8000, cmdstr);
        getnodecapacity = GetJsonMessage.GetJsonStr("192.168.100.199", 8000, nodecapacity);
        getcpuinfo = GetJsonMessage.GetJsonStr("192.168.100.199", 8000, cpuinfo);
        getmeminfo = GetJsonMessage.GetJsonStr("192.168.100.199", 8000, memcapacityinfo);
        System.out.println(getbasicinfo);
        System.out.println(getnodecapacity);
        System.out.println(getcpuinfo);
        System.out.println(getmeminfo);
        //1.工作中 2.服务未启动 -98.创建中 3.硬盘报警 4.网络错误 -99.删除中
        //光盘库1为工作中，1之外的状态认为是非工作状态
        int valuestauts = Integer.parseInt(getnodecapacity.getString("nodestatus"));
        if (valuestauts != 1) {
            valuestauts = 2;
        }
        //光盘库存储信息发送接口
        JSONObject Jarrary = new JSONObject();
        OpticalSystemInfoDetail[] tapearrary = new OpticalSystemInfoDetail[1];
        tapearrary[0] = new OpticalSystemInfoDetail();
        tapearrary[0].setCpuCount(Integer.parseInt(getcpuinfo.getString("cpucount")));
        tapearrary[0].setCpuType(getcpuinfo.getString("cputype"));
        tapearrary[0].setHardDiskCount(Integer.parseInt(getnodecapacity.getString("diskcnt")));
        tapearrary[0].setMemCapacity(Double.parseDouble(getmeminfo.getString("memtotal")));
        tapearrary[0].setName(getbasicinfo.getString("label"));
        tapearrary[0].setStatus(valuestauts);

        Jarrary.accumulate("optical", tapearrary);
        return Jarrary;
    }
}
