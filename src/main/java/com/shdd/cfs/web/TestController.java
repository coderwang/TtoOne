package com.shdd.cfs.web;

import com.shdd.cfs.config.DateConfig;
import com.shdd.cfs.utils.base.UnitHandle;
import com.shdd.cfs.utils.json.OpticalJsonHandle;
import com.shdd.cfs.utils.xml.iamp.HttpResult;
import com.shdd.cfs.utils.xml.iamp.IampRequest;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class TestController {
	@Autowired
	//private IampRequest iampRequest;
	private DateConfig config;
	@GetMapping("/hello")
	public String hello() throws DocumentException {
//		HttpResult logon = iampRequest.logon("shuju", "69MOQca0Hv6NsOJH");
//		String teString = logon.getContent();
//		ArrayList<JSONObject> card = OpticalJsonHandle.getAllCardInfo();
//		for(JSONObject list : card){
//			System.out.println(list);
//		}
//		String session = iampRequest.SessionKey();
//		HttpResult massagelist = iampRequest.inquiry_task_warn(session);
//		Integer arrayList = iampRequest.get_task_warn(massagelist);
//		for(String list: arrayList){
//			iampRequest.task_time(massagelist,list);
//			System.out.println(iampRequest.task_message(massagelist,list));
//		}
//		OpticalJsonHandle.cdboxlist();
        System.out.println("+++++++++++++++++"+ config.getdistributemount());
		System.out.println("+++++++++++++++++"+ config.getOpticalIP());
		System.out.println("+++++++++++++++++"+ config.getOpticalPort());
		System.out.println("+++++++++++++++++"+ config.getSingleDiskCapacity());
		System.out.println("+++++++++++++++++"+ config.getSingleBoxCardNum());
 		return config.getdistributemount();
//		return teString;
	}
}
