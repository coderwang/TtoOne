package com.shdd.cfs.utils.xml;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author: wangpeng
 * @version: 1.0 2018/8/30
 */
@RestController
@Slf4j
public class XmlFromURL {
    /**
     * 根据url获取xml格式信息
     *
     * @param url
     * @return
     * @throws MalformedURLException
     * @throws DocumentException
     */
    public Document GetXmlDocument(String url) throws MalformedURLException, DocumentException {

        Document doc = new SAXReader().read(new URL(url));

        Element rootElement = doc.getRootElement();
        List<Element> elements = rootElement.elements();
        //log.info(rootElement.getName());
        //log.info(elements.get(3).getData().toString());
        //log.info(rootElement.element("from").getData().toString());

        return doc;
    }

    /**
     * 根据xml中的键值，获取结果
     *
     * @param document
     * @param key
     */
    public void GetStringsFromXml(Document document, String key) {
        Element rootElement = document.getRootElement();
        log.info(rootElement.element(key).getData().toString());
    }
}
