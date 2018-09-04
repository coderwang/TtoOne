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
        DistCurStorageCapacity distCurStorageCapacity = new DistCurStorageCapacity();

        ArrayList poolList = new ArrayList();
        DistPoolStorageCapacity distPoolStorageCapacity = new DistPoolStorageCapacity();
        distPoolStorageCapacity.setCapacity(23.4);
        distPoolStorageCapacity.setPoolName("123");
        distPoolStorageCapacity.setUsedCapacity(34.1);
        poolList.add(distPoolStorageCapacity);
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

        //给磁带库当前容量赋值
        tapeCurStorageCapacity.setCapacity(allTapeCapacity);
        tapeCurStorageCapacity.setUsedCapacity(useTapeCapacity);
        tapeCurStorageCapacity.setDevType("tape");

        return tapeCurStorageCapacity;
    }

    /**
     * @author jiafuzeng 20180904
     * @return
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

        //给光盘库当前容量赋值
        cddiskCurStorageCapacity.setCapacity(allOptCapacity);
        cddiskCurStorageCapacity.setDevType("cdstorage");
        cddiskCurStorageCapacity.setUsedCapacity(useOptCapacity);

        return cddiskCurStorageCapacity;
    }
}