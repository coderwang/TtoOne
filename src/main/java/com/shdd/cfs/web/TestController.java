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

@RestController
public class TestController {
	@Autowired
	private IampRequest iampRequest;
	@GetMapping("/hello")
	public String hello() throws DocumentException, org.dom4j.DocumentException {
		HttpResult logon = iampRequest.logon("shuju", "69MOQca0Hv6NsOJH");
		String teString = logon.getContent();
		String session = iampRequest.SessionKey();
		ArrayList<Integer> arrayList = iampRequest.all_of_tape_status(session);
		for (int i = 0 ; i < arrayList.size(); i++)
			System.out.println(arrayList.get(i));
		return teString;
	}
}
