package com.shdd.cfs.web;

import com.shdd.cfs.utils.xml.iamp.HttpResult;
import com.shdd.cfs.utils.xml.iamp.IampRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@Autowired
	private IampRequest iampRequest;
	@GetMapping("/hello")
	public String hello() throws DocumentException, org.dom4j.DocumentException {
		HttpResult logon = iampRequest.logon("shuju", "69MOQca0Hv6NsOJH");
		String teString = logon.getContent();
		String session = iampRequest.SessionKey();
		iampRequest.inquiry_tape_lists(session);
		return teString;
	}
}
