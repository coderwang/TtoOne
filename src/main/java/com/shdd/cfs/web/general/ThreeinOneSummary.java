package com.shdd.cfs.web.general;

import com.shdd.cfs.dto.dashboard.TotalCapacityInfoDto;
import com.shdd.cfs.dto.dashboard.TotalStatusInfoDetail;
import com.shdd.cfs.utils.json.GetJsonMessage;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ThreeinOneSummary {
	/**
	 * 发送总容量信息
	 * @param TotalInfo
	 * @return
	 */
	@GetMapping(value = "api/dashboard/capacitystatus")
	@ApiOperation(value = "发送总容量信息", notes = "获取三合一系统的概要信息，包含总任务、运行任务、完成任务、新增任务、总容量、总告警")

	public TotalCapacityInfoDto sendTotalCapacityStatusInfo(String TotalInfo) {
		log.info(TotalInfo);
		//定义获取光盘库容量信息的对象
		JSONObject getnodecapacity = new JSONObject();
		//获取光盘库容量, 节点状态
		String nodecapacity = "{\"protoname\":\"nodeconnect\"}";
		//获取光盘库节点容量信息
		getnodecapacity = GetJsonMessage.GetJsonStr("192.168.100.199", 8000, nodecapacity);
		Double optUserCapacity = Double.parseDouble(getnodecapacity.getString(("usedinfo")));
		//分布式基础访问接口
		String baseurl = "";
		//访问分布式容量url接口
		String disstorageinfo = baseurl + "api/monitor/clustes/storage/";
		String discapacity = GetJsonMessage.getURLContent(disstorageinfo);
		//获取分布式回传的json报文
		JSONObject disjsoncapacity = JSONObject.fromObject(discapacity);
		//获取分布式存储集群总的使用容量
		Double disUseCapacity = Double.parseDouble(disjsoncapacity.getString("storage_used"));
		//组织发送给UI的报文
		TotalCapacityInfoDto capacityStatusInfo = new TotalCapacityInfoDto();
		TotalStatusInfoDetail[] allCapacityInfo = new TotalStatusInfoDetail[1];
		allCapacityInfo[0] = new TotalStatusInfoDetail();
		allCapacityInfo[0].setTask(1);
		allCapacityInfo[0].setRunning(1);
		allCapacityInfo[0].setCompleted(1);
		allCapacityInfo[0].setAdded(1);
		allCapacityInfo[0].setCapacity(optUserCapacity + disUseCapacity);
		allCapacityInfo[0].setWarning(1);
		capacityStatusInfo.setData(allCapacityInfo);
		//返回给JSON报文
		return capacityStatusInfo;
	}
}
