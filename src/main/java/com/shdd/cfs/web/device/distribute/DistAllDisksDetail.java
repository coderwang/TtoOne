/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.dto.device.DiskDetailInfo;
import com.shdd.cfs.utils.json.HttpRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Slf4j
public class DistAllDisksDetail {
    /**
     * 集群信息状态下获取分布式存储系统节点详细概况
     *
     * @param value
     * @return
     */
    @GetMapping(value = "api/dashboard/distribute/disks")
    @ApiOperation(value = "集群信息状态下获取分布式存储系统节点详细概况", notes = "分布式存储池中的所有磁盘的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "page_num", value = "翻页页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "count", value = "每页所含最大条目数", required = true)
    })

    public JSONObject DistributeStorageInfo(String value, int page_num, int count) {

        //访问下级分布式系统接口api/devices
        HttpRequest httpRequest = new HttpRequest();

        String result = httpRequest.sendGet("http://192.168.1.32:8000/api/devices", " ");
        JSONArray deviceArray = JSONArray.fromObject(result);
        JSONArray hostsArray;
        JSONArray disksArray;

        //处理对象
        JSONObject deviceObject;
        JSONObject diskObject = null;

        //device下主机数量
        int hostCount;
        //单个主机下disk数量
        int diskCount;
        //所有disk数量
        int allDiskCount = 0;
        //下级系统中所有device数量
        int deviceCount;

        //页码处理
        int page_count = 0;

        deviceCount = deviceArray.size();
        ArrayList diskDetailInfoList = new ArrayList();

        //遍历来自下级系统的所有device设备数组成员
        for (int i = 0; i < deviceCount; i++) {
            //获取成员对象
            deviceObject = deviceArray.getJSONObject(i);
            //从device信息中拿到host主机信息
            hostsArray = deviceObject.getJSONArray("hosts");
            hostCount = hostsArray.size();

            //遍历device下的所有host数组成员
            for (int j = 0; j < hostCount; j++) {
                //从host信息中获取disk数组信息
                diskObject = hostsArray.getJSONObject(j);

                //获取到host中的disk数组信息
                disksArray = diskObject.getJSONArray("disks");
                diskCount = disksArray.size();
                allDiskCount += diskCount;

                //获取disk所属主机名称
                String hostName = diskObject.getString("host_name");

                //遍历host下的所有disk成员
                for (int k = 0; k < diskCount; k++) {
                    //从disk数组中获取disk信息
                    diskObject = disksArray.getJSONObject(k);

                    if (diskObject.getString("system_disk").equalsIgnoreCase("true")) {
                        allDiskCount -= 1;
                        continue;
                    }

                    //翻页
                    if ((i + 1) * (j + 1) * (k + 1) <= (page_num - 1) * count) {
                        continue;
                    }

                    DiskDetailInfo diskDetailInfo = new DiskDetailInfo();

                    //处理从下级系统获取到的数据
                    diskDetailInfo.setId(diskObject.getString("disk_id"));
                    diskDetailInfo.setName(diskObject.getString("disk_name"));
                    diskDetailInfo.setHostname(hostName);

                    Double Capacity = 0.0;
                    Double used = 0.0;
                    String totalString = diskObject.getString("total");
                    String usedString = diskObject.getString("used");
                    String unitCapacity = "";
                    String unitUsed = "";
                    unitCapacity = totalString.substring(totalString.length() - 1, totalString.length());
                    unitUsed = usedString.substring(usedString.length() - 1, usedString.length());

                    if (unitCapacity.equalsIgnoreCase("T")) {
                        //根据单位信息，转成GB级别数据
                        Capacity = Double.parseDouble(totalString.substring(0, totalString.length() - 1));
                        Capacity = Capacity *1024;
                    } else if (unitCapacity.equalsIgnoreCase("G")) {
                        Capacity = Double.parseDouble(totalString.substring(0, totalString.length() - 1)) / 1024;
                    } else if (unitCapacity.equalsIgnoreCase("M")) {
                        //根据单位信息为MB，转成GB级别数据
                        Capacity = Double.parseDouble(totalString.substring(0, totalString.length() - 1)) / 1024 ;
                    } else {
                        //根据单位信息为Byte，转成GB级别数据
                        Capacity = Double.parseDouble(totalString.substring(0, totalString.length() - 1)) / 1024 / 1024;
                    }

                    if (unitUsed.equalsIgnoreCase("T")) {
                        //根据单位信息，转成GB级别数据
                        used = Double.parseDouble(usedString.substring(0, usedString.length() - 1));
                        used = used * 1024;
                    } else if (unitUsed.equalsIgnoreCase("G")) {
                        //根据单位信息为GB，转成GB级别数据
                        used = Double.parseDouble(usedString.substring(0, usedString.length() - 1)) / 1024;
                    } else if (unitUsed.equalsIgnoreCase("M")) {
                        //根据单位信息为MB，转成GB级别数据
                        used = Double.parseDouble(usedString.substring(0, usedString.length() - 1)) / 1024 ;
                    } else {
                        //根据单位信息为Byte，转成GB级别数据
                        used = Double.parseDouble(usedString.substring(0, usedString.length() - 1)) / 1024 / 1024 ;
                    }

                    diskDetailInfo.setCapacity(Capacity);
                    diskDetailInfo.setUsed(used);

                    if (diskObject.getString("disk_state").equalsIgnoreCase("true")) {
                        diskDetailInfo.setStatus(1);
                    } else {
                        diskDetailInfo.setStatus(0);
                    }

                    //将信息添加到hashMap
                    diskDetailInfoList.add(diskDetailInfo);
                    page_count += 1;
                    if (page_count == count) {
                        break;
                    }
                }
            }
        }

        //计算总页数
        int totalPage = 0;
        if (count != 0) {
            totalPage = (allDiskCount % count != 0) ? ((allDiskCount / count) + 1) : (allDiskCount / count);
        }

        //数据打包发送至UI页面前端
        JSONObject jarrary = new JSONObject();
        Object diskArray[] = diskDetailInfoList.toArray();

        jarrary.accumulate("totalPage", totalPage);
        jarrary.accumulate("disk", diskDetailInfoList);

        return jarrary;
    }
}
