package com.shdd.cfs.utils.xml;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 *
 */
@RestController
@Slf4j
public class XmlFromURL {

    public JSONArray abc(String SetValue) {

        String value = "[\n" +
                "    {\n" +
                "        \"abc\": \"cbd\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"xxx\": \"cbd\",\n" +
                "        \"yyy\": \"cbd\"\n" +
                "    }\n" +
                "]";
        JSONArray jarray1 = new JSONArray();

        JSONObject jobj = new JSONObject();
        //1、读取文件
        //   Document doc = new SAXReader().read();
        String response = "";

        try {
            URL url = new URL("http://localhost:8090/gg/insturment");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openConnection().getInputStream()));

            StringBuilder sb = new StringBuilder();
            while ((response = reader.readLine()) != null) {

                sb.append(response);

            }
            response = sb.toString();

            Document doc = new SAXReader().read(new URL("http://www.w3school.com.cn/example/xmle/note.xml"));

            Element rootElement = doc.getRootElement();
            List<Element> elements = rootElement.elements();
            log.info(rootElement.getName());
            log.info(elements.get(2).getData().toString());
            log.info(response);
            jobj = JSONObject.fromObject(response);
            log.info(jobj.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();

            JSONArray jarray = new JSONArray();

            JSONObject o1 = new JSONObject();
            o1.accumulate("abc", "cbd");

            JSONObject o2 = new JSONObject();
            o2.accumulate("xxx", "cbd");
            o2.accumulate("yyy", "cbd");

            jarray.add(o1);
            jarray.add(o2);

            return jarray;
        }

        return jarray1;
    }
}
