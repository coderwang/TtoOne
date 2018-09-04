package com.shdd.cfs.web;

import com.shdd.cfs.utils.xml.iamp.HttpResult;
import com.shdd.cfs.utils.xml.iamp.IampRequest;
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
	private IampRequest iampRequest;
	@GetMapping("/hello")
	public String hello() throws DocumentException, org.dom4j.DocumentException {
		HttpResult logon = iampRequest.logon("shuju", "69MOQca0Hv6NsOJH");
		String teString = logon.getContent();
		String session = iampRequest.SessionKey();
		HttpResult tapelist = iampRequest.inquiry_tape_lists(session);
		ArrayList<String> arrayList = iampRequest.get_tapes_id(tapelist);
		for(String list :arrayList){
			System.out.println(list);
		}
		System.out.println(iampRequest.tape_online_info(tapelist, "000790G"));
//		HttpResult glist = iampRequest.inquiry_gtape_lists(session);
//		ArrayList<Map<String,String>> group = iampRequest.tape_group_info(glist);
//		for(Map<String,String> list : group){
//			System.out.println(list.get("groupname"));
//			System.out.println(list.get("alltapenum"));
//			System.out.println(list.get("emptytapenum"));
//		}
		return teString;
	}
}
