package com.shdd.cfs.commit;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;


/**
 * 请求的返回结果
 * 
 * @author zhel <br/>
 * 2018年8月13日
 */
@Data
@ToString
public class HttpResult implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 请求成功与否（出现异常及为请求失败）
     */
    private boolean flag;
    
    // * 响应码（一定是 200，如果出现其他的，说明服务器有问题了）
    // private int statusCode;
    
    /**
     * 返回值 / 错误信息
     */
    private String content;
    
    private HttpResult() {}
    private HttpResult(boolean flag, String content) {
        this.flag = flag;
        this.content = content;
    }
    
    public final static HttpResult getResult(boolean flag, String content) {
        return new HttpResult(flag, content);
    }
    
    public final static HttpResult SUCCESS(String content) {
        return new HttpResult(true, content);
    }
    
    public final static HttpResult FAIL_ClientProtocolException() {
        return new HttpResult(false, "服务器连接失败，请检测 HTTP 请求通畅。");
    }
    
    public final static HttpResult FAIL_URISyntaxException() {
        return new HttpResult(false, "无法解析为 URI 引用，请求检测请求的正确性。");
    }

    public final static HttpResult FAIL_IOEXCEPTION() {
        return new HttpResult(false, "请求过程中出现 IO 错误，请联系开发人员。");
    }
    
    
}
