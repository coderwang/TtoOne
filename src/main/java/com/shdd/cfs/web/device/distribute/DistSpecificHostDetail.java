package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.dto.device.distribute.HostInfoDetail;
import com.shdd.cfs.utils.json.GetJsonMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DistSpecificHostDetail {

    /**
     * 获取分布式存储系统主机概况
     *
     * @param value
     * @return
     */
    @GetMapping(value = "api/device/distribute/host")
    @ApiOperation(value = "获取分布式存储系统主机概况" , notes = "获取分布式存储系统指定服务器节点的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "deviceId", value = "指定分布式存储系统服务器节点ID", required = true)
    })

    public JSONObject GetHostfDistribute(String value) {
        //向分布式厂家发送获取单个主机的信息的请求

        //获取分布式集群中单个主机信息
        String baseurl = "";
        String getdishstinfo = baseurl + "api/hosts/host_id";
        String dishostinfo = GetJsonMessage.getURLContent(getdishstinfo);
        JSONObject hostinfo = JSONObject.fromObject(dishostinfo);
        String hostname = hostinfo.getString("host_name");
        //发送单个主机的详细情况
        JSONObject hostInfo = new JSONObject();
        HostInfoDetail[] arrdetail = new HostInfoDetail[1];
        arrdetail[0] = new HostInfoDetail();
        arrdetail[0].setName(hostname);
        arrdetail[0].setCpuType("phytium");
        arrdetail[0].setCpuCount(2);
        arrdetail[0].setMemCapacity(20.5);
        arrdetail[0].setHardDiskCount(12);
        arrdetail[0].setStatus(1);

        hostInfo.accumulate("host", arrdetail);
        return hostInfo;
    }
}
