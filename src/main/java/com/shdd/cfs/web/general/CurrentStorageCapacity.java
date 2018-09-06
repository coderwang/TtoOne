/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.general;

import com.shdd.cfs.dto.dashboard.CDDiskCurStorageCapacity;
import com.shdd.cfs.dto.dashboard.DistCurStorageCapacity;
import com.shdd.cfs.dto.dashboard.DistPoolStorageCapacity;
import com.shdd.cfs.dto.dashboard.TapeCurStorageCapacity;
import com.shdd.cfs.utils.json.GetJsonMessage;
import com.shdd.cfs.utils.json.HttpRequest;
import com.shdd.cfs.utils.xml.iamp.HttpResult;
import com.shdd.cfs.utils.xml.iamp.IampRequest;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Slf4j
public class CurrentStorageCapacity {
    /**
     * 获取仪表盘当前容量信息
     *
     * @param SetValue
     * @return
     */
    @Autowired
    IampRequest iampRequest;

    @GetMapping(value = "api/dashboard/curcapacitystatus")
    @ApiOperation(value = "获取仪表盘当前容量信息", notes = "获取各存储系统当前的容量使用情况，包含总容量和已使用容量")

    public JSONObject SendCurrentCapacity(String SetValue) throws DocumentException {

        //定义Json发送对象
        DistCurStorageCapacity distCurStorageCapacity;
        TapeCurStorageCapacity tapeCurStorageCapacity;
        CDDiskCurStorageCapacity cddiskCurStorageCapacity;

        //向下级系统获取数据
        distCurStorageCapacity = GetDistCurStorageCapacity();
        tapeCurStorageCapacity = GetTapeCurStorageCapacity();
        //cddiskCurStorageCapacity = GetCDDiskCurStorageCapacity();

        log.info(distCurStorageCapacity.getDevType());

        JSONArray devices = new JSONArray();
        devices.add(distCurStorageCapacity);
        devices.add(tapeCurStorageCapacity);
        //devices.add(cddiskCurStorageCapacity);

        //数据打包
        JSONObject jobject = new JSONObject();
        jobject.accumulate("device", devices);
        //给UI 发送 JSON对象
        return jobject;
    }

    /**
     * @return
     * @author wangpeng 20180904
     */
    private DistCurStorageCapacity GetDistCurStorageCapacity() {
        //{
        //    "devType":"distributed",    //分布式
        //        "data":[{                            //分布式存储需返回各个存储池内										   存使用情况
        //                    "poolName":"xx",        //存储池名称
        //                    "capacity":20.34,        //存储池总容量
        //                    "usedCapacity":15.4,    //存储池已使用容量
        //                },
        //                {
        //                    ...
        //                }
        //        ]
        //}

        DistCurStorageCapacity distCurStorageCapacity = new DistCurStorageCapacity();

        //访问下级分布式系统接口api/volumes
        HttpRequest httpRequest = new HttpRequest();

        String result = httpRequest.sendGet("http://192.168.1.32:8000/api/volumes", " ");
        JSONArray volumeArray = JSONArray.fromObject(result);

        ArrayList poolList = new ArrayList();
        DistPoolStorageCapacity distPoolStorageCapacity = new DistPoolStorageCapacity();

        JSONObject volumeObject;
        int volumeCount = volumeArray.size();
        String volumeID;

        JSONObject volumeStorageObject;

        for (int i = 0; i < volumeCount; i++) {
            volumeObject = volumeArray.getJSONObject(i);

            volumeID = volumeObject.getString("vol_id");

            result = httpRequest.sendGet("http://192.168.1.32:8000/api/volumes/" + volumeID + "/storage", " ");
            volumeStorageObject = JSONObject.fromObject(result);

            //TODO 容量数据存入数据库
            distPoolStorageCapacity.setCapacity(volumeStorageObject.getString("size"));
            distPoolStorageCapacity.setPoolName(volumeStorageObject.getString("vol_name"));
            distPoolStorageCapacity.setUsedCapacity(volumeStorageObject.getString("used"));

            poolList.add(distPoolStorageCapacity);

        }

        //组织返回JSON数据对象
        distCurStorageCapacity.setDevType("distributed");
        distCurStorageCapacity.setPoolList(poolList);

        return distCurStorageCapacity;
    }

    /**
     * @return
     * @author jiafuzeng 20180904
     */
    private TapeCurStorageCapacity GetTapeCurStorageCapacity() throws DocumentException {
        TapeCurStorageCapacity tapeCurStorageCapacity = new TapeCurStorageCapacity();

        //获取磁带库容量信息
        String sessonKey = iampRequest.SessionKey();
        HttpResult tape_lists = iampRequest.inquiry_tape_lists(sessonKey);
        ArrayList<Integer> alltapelist = iampRequest.all_of_tape_status(tape_lists);
        Integer alltapesize = alltapelist.size();
        Double singleTapeCapacity = 2.5;
        int fulltape = 0;
        for (Integer list : alltapelist) {
            if (list == 1) {
                fulltape = fulltape + 1;
            }  //空白磁带
        }
        //磁带库总容量大小
        Double allTapeCapacity = alltapesize * singleTapeCapacity;
        //磁带库使用容量大小
        Double useTapeCapacity = (alltapesize - fulltape) * singleTapeCapacity;

        //TODO 容量数据存入数据库
        //给磁带库当前容量赋值
        tapeCurStorageCapacity.setCapacity(allTapeCapacity);
        tapeCurStorageCapacity.setUsedCapacity(useTapeCapacity);
        tapeCurStorageCapacity.setDevType("tape");

        return tapeCurStorageCapacity;
    }

    /**
     * @return
     * @author jiafuzeng 20180904
     */
    private CDDiskCurStorageCapacity GetCDDiskCurStorageCapacity() {
        CDDiskCurStorageCapacity cddiskCurStorageCapacity = new CDDiskCurStorageCapacity();

        //光盘库节点容量获取对象
        JSONObject getopticalcapacity;
        //获取光盘库容量, 节点状态
        String capacity = "{\"protoname\":\"nodeconnect\"}";
        //获取光盘库节点返回报文
        getopticalcapacity = GetJsonMessage.GetJsonStr("192.168.100.199", 8000, capacity);
        Double allOptCapacity = Double.parseDouble(getopticalcapacity.getString("totalinfo"));
        Double useOptCapacity = Double.parseDouble(getopticalcapacity.getString("usedinfo"));

        //TODO 容量数据存入数据库
        //给光盘库当前容量赋值
        cddiskCurStorageCapacity.setCapacity(allOptCapacity);
        cddiskCurStorageCapacity.setDevType("cdstorage");
        cddiskCurStorageCapacity.setUsedCapacity(useOptCapacity);

        return cddiskCurStorageCapacity;
    }
}