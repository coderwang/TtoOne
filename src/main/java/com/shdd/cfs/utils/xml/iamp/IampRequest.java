package com.shdd.cfs.utils.xml.iamp;

import com.shdd.cfs.utils.xml.HttpClientOperate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class IampRequest {
    /**
     * iamp5.x 版本中，支持使用的 API 接口名称<br/>
     * 参考依据：iamp_5.x_developer_reference_manual.docx
     *
     * @author zhel <br/>
     * 2018年8月13日
     */
    private enum IampApiEnum {
        inquiry_task_status("/inquiry_task_status.websvc", "2.1 查询任务状态"),
        inquiry_task_items("/inquiry_task_items.websvc", "2.2 查询任务清单"),
        inquiry_task_lists("/inquiry_task_lists.websvc", "2.3查询任务列表"),
        create_task("/create_task.websvc", "2.4 创建任务"),
        control_task("/control_task.websvc", "2.5 任务控制"),
        inquiry_tape_status("/inquiry_tape_status.websvc", "3.1 查询磁带状态"),
        inquiry_tape_lists("/inquiry_tape_lists.websvc", "3.2 查询磁带列表"),
        inquiry_tape_copys("/inquiry_tape_copys.websvc", "3.3 查询磁带副本"),
        inquiry_gtape_status("/inquiry_gtape_status.websvc", "3.4 查询磁带组状态"),
        modify_gtape_property("/modify_gtape_property.websvc", "3.5 更改磁带组属性"),
        recycle_gtape_tape("/recycle_gtape_tape.websvc", "3.6 磁带组回收磁带"),
        assign_tape("/assign_tape.websvc", "3.7 给磁带组分配磁带"),
        set_tape_place("/set_tape_place.websvc", "3.8 磁带出入库"),
        inquiry_gtape_lists("/inquiry_gtape_lists.websvc", "3.9 查询磁带组列表"),
        create_gtape("/create_gtape.websvc", "3.10 创建磁带组"),
        inquiry_tape_doc_lists("/inquiry_tape_doc_lists.websvc", "3.11 查询磁带中的案卷列表"),
        inquiry_doc_status("/inquiry_doc_status.websvc", "4.1 查询案卷状态"),
        inquiry_gdoc_status("/inquiry_gdoc_status.websvc", "4.2 查询案卷组状态"),
        inquiry_doc_store_lists("/inquiry_doc_store_lists.websvc", "4.5 查询案卷所占用的磁带列表"),
        inquiry_gdoc_store_lists("/inquiry_gdoc_store_lists.websvc", "4.6 查询案卷组所占用的磁带列表"),
        create_gdoc("/create_gdoc.websvc", "4.7 创建案卷组"),
        inquiry_production_struct("/inquiry_production_struct.websvc", "5.1 查询生产区结构"),
        logon("/logon.websvc", "6.1 登录授权");
        private final String path;

        private IampApiEnum(String path, String name) {
            this.path = path;
        }

        public String getPath() {
            return uri + path;
        }
    }

    @Resource
    private HttpClientOperate httpClientOperate;
    private static String uri;

    public IampRequest(String uri) {
        IampRequest.uri = uri;
    }

    /**
     * @param username 磁带库 数据管理员用户名
     * @param password 磁带库 数据管理员webskey
     * @return 数据请求结果字符窜
     */
    public HttpResult logon(String username, String password) {
        Map<String, String> param = new HashMap<>();
        param.put("username", username);
        param.put("password", password);
        HttpResult result = httpClientOperate.doGet(IampApiEnum.logon.getPath(), param);
        return result;
    }

    /**
     * 通过用户名获取访问秘钥
     *
     * @return 获取到的访问秘钥
     */
    public String SessionKey() throws DocumentException {
        String sessonkey = "";
        HttpResult loginfo = logon("shuju", "69MOQca0Hv6NsOJH");
        if (!loginfo.isFlag()) {
            return "wrong";
        }
        String teString = loginfo.getContent();
        Document document = DocumentHelper.parseText(teString);
        sessonkey = document.selectSingleNode("/xml/session/key").getText();
        return sessonkey;
    }

    /**
     * @param id
     * @param session_key
     * @return
     */
    public HttpResult inquiry_task_items(String id, String session_key) {
        Map<String, String> param = new HashMap<>();
        param.put("id", id);
        param.put("session_key", session_key);
        HttpResult result = httpClientOperate.doGet(IampApiEnum.inquiry_task_status.getPath(), param);
        return result;
    }

    /**
     * 向磁带库后台请求所有磁带信息
     *
     * @param session_key 访问秘钥
     * @return 所有磁带信息
     */
    public HttpResult inquiry_tape_lists(String session_key) {
        Map<String, String> param = new HashMap<>();
        param.put("session_key", session_key);
        HttpResult result = httpClientOperate.doGet(IampApiEnum.inquiry_tape_lists.getPath(), param);
        return result;
    }

    /**
     * 分析磁带库后台返回的所有磁带容量信息 （解析字段：【tape/capacity/status】 1空白 2未满 3已满）
     *
     * @param tape_lists 请求所有磁带xml结果
     * @return 单个磁带库所有磁带的状态信息
     */
    public ArrayList<Integer> all_of_tape_status(HttpResult tape_lists) throws DocumentException {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        String result = tape_lists.getContent();
        Document document = DocumentHelper.parseText(result);
        Element root = document.getRootElement();
        // 遍历root节点下的所有子节点
        for (Iterator itemGroup = root.elementIterator(); itemGroup.hasNext(); ) {
            // 得到root节点下所有子节点
            Element tape_group = (Element) itemGroup.next();
            // 遍历遍历root子节点下的所有子节点
            for (Iterator itemCapacity = tape_group.elementIterator(); itemCapacity.hasNext(); ) {
                Element capacity = (Element) itemCapacity.next();
                //获取所有磁带属性值【tape/capacity/status】 参数值：1空白 2未满 3已满
                if (capacity.getName().equals("capacity")) {
                    arrayList.add(Integer.parseInt(capacity.attributeValue("status")));
                }
            }
        }
        return arrayList;
    }

    /**
     * 获取所有磁带组ID
     *
     * @param tape_lists 后台返回的磁带列表
     * @return 磁带组ID数组
     */
    public ArrayList<String> get_tapes_id(HttpResult tape_lists) throws DocumentException {
        ArrayList<String> arrayList = new ArrayList<String>();
        String result = tape_lists.getContent();
        Document document = DocumentHelper.parseText(result);
        Element root = document.getRootElement();
        // 遍历root节点下的所有子节点
        for (Iterator itemGroup = root.elementIterator(); itemGroup.hasNext(); ) {
            // 得到磁带组ID
            Element tape_group = (Element) itemGroup.next();
            String tape_id = tape_group.attributeValue("id");
            arrayList.add(tape_id);
        }
        return arrayList;
    }

    /**
     * 根据磁带id获取磁带所在磁带组id
     *
     * @param tape_lists 磁带库返回的磁带列表
     * @param tapeid     磁带id
     * @return 磁带组id
     */
    public String get_group_id(HttpResult tape_lists, String tapeid) throws DocumentException {
        String result = tape_lists.getContent();
        Document document = DocumentHelper.parseText(result);
        Element root = document.getRootElement();
        String groupId = "";
        // 遍历root节点下的所有子节点
        for (Iterator itemGroup = root.elementIterator(); itemGroup.hasNext(); ) {
            Element tape_group = (Element) itemGroup.next();
            String tape_id = tape_group.attributeValue("id");
            if (tape_id.equals(tapeid)) {
                groupId = tape_group.attributeValue("group");
            }
        }
        return groupId;
    }

    /**
     * 根据磁带id获取磁带总容量和剩余容量信息
     *
     * @param tape_lists 后台返回的磁带列表
     * @param tapeid     单个磁带的id
     * @return 单个磁带id对应的磁带总容量和剩余容量
     */
    public Map<String, String> get_tape_capacityinfo(HttpResult tape_lists, String tapeid) throws DocumentException {
        String result = tape_lists.getContent();
        Document document = DocumentHelper.parseText(result);
        Element root = document.getRootElement();
        Map<String, String> capacityoftotalused = new HashMap<>();
        // 遍历root节点下的所有子节点
        for (Iterator itemGroup = root.elementIterator(); itemGroup.hasNext(); ) {
            // 得到磁带组ID
            Element tape_group = (Element) itemGroup.next();
            String tape_id = tape_group.attributeValue("id");
            if (tape_id.equals(tapeid)) {
                for (Iterator itemCapacity = tape_group.elementIterator(); itemCapacity.hasNext(); ) {
                    Element capacity = (Element) itemCapacity.next();
                    //获取所有磁带属性值【tape/capacity/total,remaining】
                    for (Iterator capacityinfo = capacity.elementIterator(); capacityinfo.hasNext(); ) {
                        Element tapecapacity = (Element) capacityinfo.next();
                        if (tapecapacity.getName().equals("total")) {
                            capacityoftotalused.put("total", tapecapacity.getText());
                        }
                        if (tapecapacity.getName().equals("remaining")) {
                            capacityoftotalused.put("remaining", tapecapacity.getText());
                        }
                    }
                }
            }
        }
        return capacityoftotalused;
    }

    /**
     * 根据磁带id获取磁带是否在线
     *
     * @param tape_lists 磁带库后台返回的所有磁带信息
     * @param tapeid     磁带组ID
     * @return 磁带是否在线
     */
    public int tape_online_info(HttpResult tape_lists, String tapeid) throws DocumentException {
        String result = tape_lists.getContent();
        Document document = DocumentHelper.parseText(result);
        Element root = document.getRootElement();
        String onlinestr = "";
        // 遍历root节点下的所有子节点
        for (Iterator itemGroup = root.elementIterator(); itemGroup.hasNext(); ) {
            // 得到磁带组ID
            Element tape_group = (Element) itemGroup.next();
            String tape_id = tape_group.attributeValue("id");
            if (tape_id.equals(tapeid)) {
                for (Iterator itemCapacity = tape_group.elementIterator(); itemCapacity.hasNext(); ) {
                    Element capacity = (Element) itemCapacity.next();
                    //获取所有磁带属性值【tape/capacity/total,remaining】
                    if (capacity.getName().equals("place")) {
                        onlinestr = capacity.getText();
                    }
                }
            }
        }
        if (onlinestr.equals("1")) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 向磁带库后台请求所有磁带组信息
     *
     * @param session_key 访问秘钥
     * @return 所有磁带组信息
     */
    public HttpResult inquiry_gtape_lists(String session_key) {
        Map<String, String> param = new HashMap<>();
        param.put("session_key", session_key);
        HttpResult result = httpClientOperate.doGet(IampApiEnum.inquiry_gtape_lists.getPath(), param);
        return result;
    }

    /**
     * 分析磁带库后台返回的所有磁带组信息
     *
     * @param tape_lists 请求的磁带组信息
     * @return 单个磁带库所有磁带组的磁带信息
     */
    public ArrayList<Map<String, String>> all_of_gtape_status(HttpResult tape_lists) throws DocumentException {
        String groupname = "";
        String empty = "";
        String full = "";
        String other = "";
        String groupid = "";
        ArrayList<Map<String, String>> arrayList = new ArrayList<>();
        String result = tape_lists.getContent();
        Document document = DocumentHelper.parseText(result);
        Element root = document.getRootElement();
        // 遍历root节点下的所有子节点
        ArrayList<Map<String, String>> arrayList1 = arrayList;
        for (Iterator itemGroup = root.elementIterator(); itemGroup.hasNext(); ) {
            // 得到root节点下所有子节点
            Element tape_group = (Element) itemGroup.next();
            groupname = tape_group.attributeValue("name");
            groupid = tape_group.attributeValue("id");
            // 遍历遍历root子节点下的所有子节点
            for (Iterator itemCapacity = tape_group.elementIterator(); itemCapacity.hasNext(); ) {
                Element tapes = (Element) itemCapacity.next();
                //遍历磁盘组内磁盘信息
                for (Iterator itemtapes = tapes.elementIterator(); itemtapes.hasNext(); ) {
                    Element capacity = (Element) itemtapes.next();
                    for (Iterator itemtapestatus = capacity.elementIterator(); itemtapestatus.hasNext(); ) {
                        Element tapestatus = (Element) itemtapestatus.next();
                        if (tapestatus.getName().equals("empty")) {
                            empty = tapestatus.getText();
                        }
                        if (tapestatus.getName().equals("other")) {
                            other = tapestatus.getText();
                        }
                        if (tapestatus.getName().equals("full")) {
                            full = tapestatus.getText();
                        }
                    }
                }
            }
            Map<String, String> gtapeMap = new HashMap<>();
            gtapeMap.put("id", groupid);
            gtapeMap.put("group", groupname);
            gtapeMap.put("empty", empty);
            gtapeMap.put("other", other);
            gtapeMap.put("full", full);
            arrayList1.add(gtapeMap);
        }

        return arrayList;
    }

    /**
     * 获取磁带组名称，磁带组中磁带总个数，磁带组中空白磁带个数
     */
    public ArrayList<Map<String, String>> tape_group_info(HttpResult gtape_list) throws DocumentException {
        ArrayList<Map<String, String>> tapeNameNum = new ArrayList<>();
        ArrayList<Map<String, String>> listTape = all_of_gtape_status(gtape_list);
        for (Map<String, String> list : listTape) {
            Integer alltapeNum = Integer.valueOf(list.get("empty")) + Integer.valueOf(list.get("other")) + Integer.valueOf(list.get("full"));
            Integer emptytapeNum = Integer.parseInt(list.get("empty"));
            String tapeName = list.get("group");
            Map<String, String> tapeInfo = new HashMap();
            tapeInfo.put("id", list.get("id"));
            tapeInfo.put("groupname", tapeName);
            tapeInfo.put("alltapenum", alltapeNum.toString());
            tapeInfo.put("emptytapenum", emptytapeNum.toString());
            tapeNameNum.add(tapeInfo);
        }
        return tapeNameNum;
    }

    /**
     * 向磁带库后台请求所有任务信息
     *
     * @param session_key 访问秘钥
     * @return 任务信息列表
     */
    public HttpResult inquiry_task_lists(String session_key) {
        Map<String, String> param = new HashMap<>();
        param.put("session_key", session_key);
        HttpResult result = httpClientOperate.doGet(IampApiEnum.inquiry_task_lists.getPath(), param);
        return result;
    }

    /**
     * 获取所有任务日志
     *
     * @param messagelist 磁带库后台返回的任务列表
     * @return 所有操作日志
     * @throws DocumentException
     */
    public String task_message(HttpResult messagelist, String taskid) throws DocumentException {
        String result = messagelist.getContent();
        Document document = DocumentHelper.parseText(result);
        Element root = document.getRootElement();
        String messages = null;
        for (Iterator itemGroup = root.elementIterator(); itemGroup.hasNext(); ) {
            // 得到root节点下所有子节点
            Element tape_group = (Element) itemGroup.next();
            // 遍历遍历root子节点下的所有子节点
            if (tape_group.attributeValue("id").equals(taskid)) {
                for (Iterator itemCapacity = tape_group.elementIterator(); itemCapacity.hasNext(); ) {
                    Element tapes = (Element) itemCapacity.next();
                    if (tapes.getName().equals("remark")) {
                        messages = tapes.getText();
                    }
                }
            }
        }
        return messages;
    }

    /**
     * 获取所有任务ID
     *
     * @param takelist 磁带库后台返回的任务列表
     * @return 所有任务ID
     */
    public ArrayList<String> take_id(HttpResult takelist) throws DocumentException {
        String result = takelist.getContent();
        ArrayList<String> arrayList = new ArrayList<>();
        Document document = DocumentHelper.parseText(result);
        Element root = document.getRootElement();
        for (Iterator itemtakeid = root.elementIterator(); itemtakeid.hasNext(); ) {
            Element takeid = (Element) itemtakeid.next();
            if (takeid.getName().equals("task")) {
                arrayList.add(takeid.attributeValue("id"));
            }
        }
        return arrayList;
    }

    /**
     * 根据任务ID获取任务时间
     *
     * @param messagelist 磁带库后台返回的任务列表
     * @return 任务创建时间和结束时间
     */
    public Map<String, String> task_time(HttpResult messagelist, String taskid) throws DocumentException {
        String result = messagelist.getContent();
        Document document = DocumentHelper.parseText(result);
        Element root = document.getRootElement();
        Map<String, String> timeMap = new HashMap<>();
        String createtime = null;
        String cpmpletedtime = null;
        for (Iterator itemGroup = root.elementIterator(); itemGroup.hasNext(); ) {
            // 得到root节点下所有子节点
            Element tape_group = (Element) itemGroup.next();
            // 遍历遍历root子节点下的所有子节点
            if (tape_group.attributeValue("id").equals(taskid)) {
                for (Iterator itemCapacity = tape_group.elementIterator(); itemCapacity.hasNext(); ) {
                    Element tapes = (Element) itemCapacity.next();
                    for (Iterator itemtime = tapes.elementIterator(); itemtime.hasNext(); ) {
                        Element time = (Element) itemtime.next();
                        if (time.getName().equals("created")) {
                            createtime = time.getText();
                        }
                        if (time.getName().equals("created")) {
                            cpmpletedtime = time.getText();
                        }
                    }
                }
            }
        }
        timeMap.put("create", createtime);
        timeMap.put("cpmplete", cpmpletedtime);
        return timeMap;
    }
}
