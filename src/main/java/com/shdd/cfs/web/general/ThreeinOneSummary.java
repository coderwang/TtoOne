/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.general;

import com.shdd.cfs.dto.dashboard.TotalCapacityInfoDto;
import com.shdd.cfs.dto.dashboard.TotalStatusInfoDetail;
import com.shdd.cfs.utils.json.HttpRequest;
import com.shdd.cfs.utils.json.OpticalJsonHandle;
import com.shdd.cfs.utils.xml.iamp.HttpResult;
import com.shdd.cfs.utils.xml.iamp.IampRequest;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
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

		//data:{
		//			"distcapacity": 1,      //分布式总容量
		//			"distfree": 1,       	//分布式可用容量
		//			"tapecapacity": 1,    	//磁带库总磁带数
		//			"tapefree": 1，        	//磁带库可用磁带数
		//			"cdcapacity": 1，       //光盘库总光盘数
		//			"cdfree": 1     		//光盘库可用光盘数
		//}
		TotalStatusInfoDetail allDistCapacityInfo = new TotalStatusInfoDetail();



		log.info(TotalInfo);
		//获取光盘库在线信息
		Map<String, Integer> cdOline = OpticalJsonHandle.OnlineCdInfo();
		Integer totalCdOline = cdOline.get("totalOlineCard");
		Integer freeCdOline = cdOline.get("freeOlineCard");

		//获取分布式集群信息
//		JSONObject disjsoncapacity = DistributeUrlHandle.ClusterInfo();
		//获取分布式存储集群总的使用容量
//		Double disUseCapacity = Double.parseDouble(disjsoncapacity.getString("storage_used"));
		allDistCapacityInfo.setCdcapacity(1);
		allDistCapacityInfo.setCdfree(25);

		//磁带库存储系统
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

		allDistCapacityInfo.setTapecapacity(alltapesize);
		allDistCapacityInfo.setTapefree(fulltape);

		//分布式存储系统
		//访问下级分布式系统接口api/monitor/clusters/storage/
		HttpRequest httpRequest = new HttpRequest();

		String result = httpRequest.sendGet("http://192.168.1.32:8000/api/monitor/clusters/storage/", " ");
		JSONObject distStorageObject = JSONObject.fromObject(result);

		allDistCapacityInfo.setDistcapacity(distStorageObject.getString("storage_total"));
		allDistCapacityInfo.setDistfree(distStorageObject.getString("storage_free"));


		TotalCapacityInfoDto capacityStatusInfo = new TotalCapacityInfoDto();
		TotalStatusInfoDetail[] allCDCapacityInfo = new TotalStatusInfoDetail[1];
		allCDCapacityInfo[0] = new TotalStatusInfoDetail();
		allCDCapacityInfo[0].setDistcapacity("23.1");
		allCDCapacityInfo[0].setDistfree("12.3");
		allCDCapacityInfo[0].setTapecapacity(alltapesize);
		allCDCapacityInfo[0].setTapefree(fulltape);
		allCDCapacityInfo[0].setCdcapacity(totalCdOline);
		allCDCapacityInfo[0].setCdfree(freeCdOline);

		capacityStatusInfo.setData(allCDCapacityInfo);
		//返回给JSON报文
		return capacityStatusInfo;
	}


}
