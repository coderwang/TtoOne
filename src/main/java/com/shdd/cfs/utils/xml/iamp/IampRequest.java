package com.shdd.cfs.utils.xml.iamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import com.shdd.cfs.utils.xml.HttpClientOperate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class IampRequest {
    /**
     * iamp5.x 版本中，支持使用的 API 接口名称<br/>
     * 参考依据：iamp_5.x_developer_reference_manual.docx
     * @author zhel <br/>
     * 2018年8月13日
     */
    private enum IampApiEnum {
        inquiry_task_status("/inquiry_task_status.websvc",                      "2.1 查询任务状态"),
        inquiry_task_items("/inquiry_task_items.websvc",                        "2.2 查询任务清单"),
        create_task("/create_task.websvc",                                       "2.4 创建任务"),
        control_task("/control_task.websvc",                                     "2.5 任务控制"),
        inquiry_tape_status("/inquiry_tape_status.websvc",                      "3.1 查询磁带状态"),
        inquiry_tape_lists("/inquiry_tape_lists.websvc",                        "3.2 查询磁带列表"),
        inquiry_tape_copys("/inquiry_tape_copys.websvc",                        "3.3 查询磁带副本"),
        inquiry_gtape_status("/inquiry_gtape_status.websvc",                    "3.4 查询磁带组状态"),
        modify_gtape_property("/modify_gtape_property.websvc",                  "3.5 更改磁带组属性"),
        recycle_gtape_tape("/recycle_gtape_tape.websvc",                        "3.6 磁带组回收磁带"),
        assign_tape("/assign_tape.websvc",                                       "3.7 给磁带组分配磁带"),
        set_tape_place("/set_tape_place.websvc",                                 "3.8 磁带出入库"),
        create_gtape("/create_gtape.websvc",                                     "3.10 创建磁带组"),
        inquiry_tape_doc_lists("/inquiry_tape_doc_lists.websvc",                "3.11 查询磁带中的案卷列表"),
        inquiry_doc_status("/inquiry_doc_status.websvc",                         "4.1 查询案卷状态"),
        inquiry_gdoc_status("/inquiry_gdoc_status.websvc",                       "4.2 查询案卷组状态"),
        inquiry_doc_store_lists("/inquiry_doc_store_lists.websvc",              "4.5 查询案卷所占用的磁带列表"),
        inquiry_gdoc_store_lists("/inquiry_gdoc_store_lists.websvc",            "4.6 查询案卷组所占用的磁带列表"),
        create_gdoc("/create_gdoc.websvc",                                        "4.7 创建案卷组"),
        inquiry_production_struct("/inquiry_production_struct.websvc",          "5.1 查询生产区结构"),
        logon("/logon.websvc",                                                     "6.1 登录授权");
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
     * @param username  磁带库 数据管理员用户名
     * @param password   磁带库 数据管理员webskey
     * @return  数据请求结果字符窜
     */
    public HttpResult logon(String username, String password) {
        Map<String, String> param = new HashMap<>();
        param.put("username", username);
        param.put("password", password);
        HttpResult result = httpClientOperate.doGet(IampApiEnum.logon.getPath(), param);
        return result;
    }

    /**
     *通过用户名获取访问秘钥
      * @return 获取到的访问秘钥
     */
    public String SessionKey() throws DocumentException {
        String sessonkey = "";
        HttpResult loginfo = logon("shuju","69MOQca0Hv6NsOJH");
        if (!loginfo.isFlag()) {
            return "wrong";
        }
        String teString = loginfo.getContent();
        Document document = DocumentHelper.parseText(teString);
        sessonkey = document.selectSingleNode("/xml/session/key").getText();
        return sessonkey;
    }

    /**
     *
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
     *向磁带库后台请求所有磁带信息
     * @param session_key 访问秘钥
     * @return  所有磁带信息
     */
    public HttpResult inquiry_tape_lists(String session_key){
        Map<String,String> param = new HashMap<>();
        param.put("session_key",session_key);
        HttpResult result = httpClientOperate.doGet(IampApiEnum.inquiry_tape_lists.getPath(),param);
        return result;
    }
    /**
     * 分析磁带库后台返回的所有磁带信息 （解析字段：【tape/capacity/status】 1空白 2未满 3已满）
     * @param session_key 访问秘钥
     * @return
     */
    public String all_of_tape_status(String session_key) throws DocumentException {
        String tapestatus = "";
        HttpResult tape_lists = inquiry_tape_lists(session_key);
        System.out.println(tape_lists);
        String result = tape_lists.getContent();
        //System.out.println(result);
        Document document = DocumentHelper.parseText(result);
        Element rootElm = document.getRootElement();
        //System.out.println(rootElm);
        List nodes = rootElm.elements("tape/capacity/status");
        for (Iterator it = nodes.iterator(); it.hasNext();) {
            Element elm = (Element) it.next();
            System.out.println("gg_test:" + elm.getStringValue());
        }
        return tapestatus;
    }
}
