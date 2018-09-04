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
		TotalCapacityInfoDto capacityStatusInfo = new TotalCapacityInfoDto();
		TotalStatusInfoDetail allCapacityInfo = new TotalStatusInfoDetail();

		//光盘库存储系统
		//定义获取光盘库容量信息的对象
		JSONObject getnodecapacity = new JSONObject();
		//获取光盘库容量, 节点状态
		String nodecapacity = "{\"protoname\":\"nodeconnect\"}";
		//获取光盘库节点容量信息
//		getnodecapacity = GetJsonMessage.GetJsonStr("192.168.100.199", 8000, nodecapacity);
//		Double optUserCapacity = Double.parseDouble(getnodecapacity.getString(("usedinfo")));
		//获取分布式集群信息
//		JSONObject disjsoncapacity = DistributeUrlHandle.ClusterInfo();
		//获取分布式存储集群总的使用容量
//		Double disUseCapacity = Double.parseDouble(disjsoncapacity.getString("storage_used"));
		allCapacityInfo.setCdcapacity(1);
		allCapacityInfo.setCdfree(25);

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
		allCapacityInfo.setTapecapacity(alltapesize);
		allCapacityInfo.setTapefree(fulltape);

		//分布式存储系统
		//访问下级分布式系统接口api/monitor/clusters/storage/
		HttpRequest httpRequest = new HttpRequest();

		String result = httpRequest.sendGet("http://192.168.1.32:8000/api/monitor/clusters/storage/", " ");
		JSONObject distStorageObject = JSONObject.fromObject(result);

		allCapacityInfo.setDistcapacity(distStorageObject.getString("storage_total"));
		allCapacityInfo.setDistfree(distStorageObject.getString("storage_free"));

		capacityStatusInfo.setData(allCapacityInfo);
		//返回给JSON报文
		return capacityStatusInfo;
	}


}
