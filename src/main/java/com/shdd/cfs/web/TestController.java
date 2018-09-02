package com.shdd.cfs.web;

import com.shdd.cfs.commit.HttpResult;
import com.shdd.cfs.commit.iamp.IampRequest;
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
		if (!logon.isFlag()) {
			return "wrong";
		}
		String teString = logon.getContent();
		Document document2 = DocumentHelper.parseText(teString);
		System.out.println(document2.selectSingleNode("/xml/session/key").getText());
		System.out.println(document2.selectSingleNode("/xml/session/expired").getText());
		System.out.println("gg" + iampRequest.SessionKey());
		return teString;
	}
}
