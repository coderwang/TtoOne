/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.general;

import com.shdd.cfs.dto.dashboard.TotalStatusInfoDetail;
import com.shdd.cfs.utils.base.UnitHandle;
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

	public JSONObject sendTotalCapacityStatusInfo(String TotalInfo) throws DocumentException {

		//data:{
		//			"distcapacity": 1,      //分布式总容量
		//			"distfree": 1,       	//分布式可用容量
		//			"tapecapacity": 1,    	//磁带库总磁带数
		//			"tapefree": 1，        	//磁带库可用磁带数
		//			"cdcapacity": 1，       //光盘库总光盘数
		//			"cdfree": 1     		//光盘库可用光盘数
		//}
		TotalStatusInfoDetail allDistCapacityInfo = new TotalStatusInfoDetail();
		ArrayList rstList = new ArrayList();

		//获取光盘库在线信息
		Map<String, Integer> cdOline = OpticalJsonHandle.OnlineCdInfo();

		allDistCapacityInfo.setCdcapacity(cdOline.get("totalOlineCard"));
		allDistCapacityInfo.setCdfree(cdOline.get("freeOlineCard"));

		//磁带库存储系统
		//获取磁带库总磁带个数
		String sessonKey = iampRequest.SessionKey();
		HttpResult tape_lists = iampRequest.inquiry_tape_lists(sessonKey);
		ArrayList<Integer> alltapelist = iampRequest.all_of_tape_status(tape_lists);
		//通过磁带列表获取所有磁带组ID
		ArrayList<String> groupList = iampRequest.get_GroupId_From_Tapgelist(tape_lists);
		Integer alltapesize  = 	alltapelist.size();
		int fulltape = 0;
		for(Integer list: alltapelist){
			if (list == 1){
				fulltape = fulltape + 1;
			}  //空白磁带
		}
		//遍历磁带组ID，将没有分配到磁带组中的磁带按照空白磁带处理
		for(String group:groupList){
			if (group.equals("00000000-0000-0000-0000-000000000000")){
				fulltape++;
			}
		}

		allDistCapacityInfo.setTapecapacity(alltapesize);
		allDistCapacityInfo.setTapefree(fulltape);

		//分布式存储系统
		//访问下级分布式系统接口api/monitor/clusters/storage/
		HttpRequest httpRequest = new HttpRequest();

		String result = httpRequest.sendGet("http://192.168.1.32:8000/api/monitor/clusters/storage/", " ");
		JSONObject distStorageObject = JSONObject.fromObject(result);

		String totalCapacity = distStorageObject.getString("storage_total");
		String freeCapacity = distStorageObject.getString("storage_free");
		Double total = UnitHandle.unitConversionToTB(totalCapacity);
		Double free = UnitHandle.unitConversionToTB(freeCapacity);
		allDistCapacityInfo.setDistcapacity(total);
		allDistCapacityInfo.setDistfree(free);

		//添加数据到数据缓存区
		rstList.add(allDistCapacityInfo);

		//返回给JSON报文
		JSONObject rstObject = new JSONObject();
		rstObject.accumulate("data", rstList);

		return rstObject;
	}
}
