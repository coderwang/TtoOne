/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.websocket;

import com.shdd.cfs.dto.message.WarningInfo;
import com.shdd.cfs.utils.xml.iamp.HttpResult;
import com.shdd.cfs.utils.xml.iamp.IampRequest;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/28
 */
@Controller
@Slf4j
public class WarningDataController {

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 获取告警信息
     *
     * @param
     * @return
     * @throws Exception
     */
    @Autowired
    IampRequest iampRequest;

    @ApiOperation(value = "获取告警信息")
    @MessageMapping("/warning_info")
    @SendTo("/log/warning_info")
    public JSONObject getWarningDataData() throws Exception {
        Thread.sleep(1000); // simulated delay
        //定义返回对象
        JSONObject rstObject = new JSONObject();
        ArrayList warnInfoList = new ArrayList();

        //与下级磁带库系统系统通讯
        String session = iampRequest.SessionKey();
        HttpResult logsList = iampRequest.inquiry_task_warn(session);
        //处理数据
        WarningInfo tapeWarnInfo = new WarningInfo();
        tapeWarnInfo.setName("tape");
        tapeWarnInfo.setStatus(iampRequest.get_task_warn(logsList));
        //将磁带库告警信息存入数据缓存区
        warnInfoList.add(tapeWarnInfo);

        //与下级分布式系统通信
        //处理数据
        WarningInfo distWarnInfo = new WarningInfo();
        distWarnInfo.setName("distribute");
        distWarnInfo.setStatus(0);
        //将分布式告警信息存入数据缓存区
        warnInfoList.add(distWarnInfo);

        //与下级光盘库系统通信
        //处理数据
        WarningInfo cddiskWarnInfo = new WarningInfo();
        cddiskWarnInfo.setName("disk");
        cddiskWarnInfo.setStatus(0);
        //将分布式告警信息存入数据缓存区
        warnInfoList.add(cddiskWarnInfo);

        rstObject.accumulate("status", warnInfoList);
        return rstObject;
    }

    //@Scheduled(cron = "0/2 * * * * ? ")//每两秒触发
    public void publishUpdates() {
        WarningInfo warningInfo = new WarningInfo();

        //向下级系统通信，并获取指定数据信息，进行数据填充
        warningInfo.setName("distribute");
        warningInfo.setStatus(1);

        template.convertAndSend("/log/warning_info", warningInfo);
    }
}
