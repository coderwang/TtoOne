/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.general;

import com.shdd.cfs.dto.dashboard.TotalCapacityInfoDto;
import com.shdd.cfs.dto.dashboard.TotalStatusInfoDetail;
import com.shdd.cfs.utils.json.OpticalJsonHandle;
import com.shdd.cfs.utils.xml.iamp.HttpResult;
import com.shdd.cfs.utils.xml.iamp.IampRequest;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@Slf4j
public class ThreeinOneSummary {
	/**
	 * 发送总容量信息
	 * @param TotalInfo
	 * @return
	 */
	@Autowired
	IampRequest iampRequest;
	@GetMapping(value = "api/dashboard/capacitystatus")
	@ApiOperation(value = "发送总容量信息", notes = "获取三合一系统的概要信息，包含总任务、运行任务、完成任务、新增任务、总容量、总告警")

	public TotalCapacityInfoDto sendTotalCapacityStatusInfo(String TotalInfo) throws DocumentException {
		log.info(TotalInfo);
		//获取光盘库在线信息
		Map<String, Integer> cdOline = OpticalJsonHandle.OnlineCdInfo();
		Integer totalCdOline = cdOline.get("totalOlineCard");
		Integer freeCdOline = cdOline.get("freeOlineCard");
		//获取分布式集群信息
//		JSONObject disjsoncapacity = DistributeUrlHandle.ClusterInfo();
		//获取分布式存储集群总的使用容量
//		Double disUseCapacity = Double.parseDouble(disjsoncapacity.getString("storage_used"));
		//获取磁带库总磁带个数
		String sessonKey = iampRequest.SessionKey();
		HttpResult tape_lists = iampRequest.inquiry_tape_lists(sessonKey);
		ArrayList<Integer> alltapelist = iampRequest.all_of_tape_status(tape_lists);
		Integer alltapesize  = 	alltapelist.size();
		int fulltape = 0;
		for(Integer list: alltapelist){
			if (list == 1){
				fulltape = fulltape + 1;
			}  //空白磁带
		}
		//组织发送给UI的报文
		TotalCapacityInfoDto capacityStatusInfo = new TotalCapacityInfoDto();
		TotalStatusInfoDetail[] allCapacityInfo = new TotalStatusInfoDetail[1];
		allCapacityInfo[0] = new TotalStatusInfoDetail();
		allCapacityInfo[0].setDistcapacity(23.1);
		allCapacityInfo[0].setDistfree(12.3);
		allCapacityInfo[0].setTapecapacity(alltapesize);
		allCapacityInfo[0].setTapefree(fulltape);
		allCapacityInfo[0].setCdcapacity(totalCdOline);
		allCapacityInfo[0].setCdfree(freeCdOline);
		capacityStatusInfo.setData(allCapacityInfo);
		//返回给JSON报文
		return capacityStatusInfo;
	}
}
