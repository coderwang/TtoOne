package com.shdd.cfs.commit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Http 客户端请求 <br/>
 * 参考链接：https://blog.csdn.net/qq_35712358/article/details/71426070
 * 
 * @author zhel <br/>
 * 2018年8月13日
 */
@Component("httpClientOperate")
public class HttpClientOperate implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 将 required 设置为 false: 为了避免 RequestConfig 没被注进来的时候，其他方法都不能用，报
     * createbeanfailedexception
     */
    @Autowired(required = false)
    private RequestConfig requestConfig;

    private CloseableHttpClient getHttpClient() {
        return this.beanFactory.getBean(CloseableHttpClient.class);
    }

    /**
     * 封装 get 请求
     * 
     * @param uri
     * @param params
     * @return
     */
    public HttpResult doGet(String uri, Map<String, String> params){
        // url 设置参数
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(uri);
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
            return HttpResult.FAIL_URISyntaxException();
        }
        if (params != null) {
            for (String key : params.keySet()) {
                uriBuilder.setParameter(key, params.get(key));
            }
        }
        
        // 创建http GET请求
        HttpGet httpGet = new HttpGet();
        httpGet.setConfig(requestConfig);// 设置请求参数
        try {
            httpGet.setURI(uriBuilder.build());
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
            return HttpResult.FAIL_URISyntaxException();
        }
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = this.getHttpClient().execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                return HttpResult.SUCCESS(content);
            } else {
                return HttpResult.SUCCESS("get请求中，响应编号不是200，联系开发人员");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return HttpResult.FAIL_ClientProtocolException();
        } catch (IOException e) {
            e.printStackTrace();
            return HttpResult.FAIL_IOEXCEPTION();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 封装 post 请求
     * 
     * @param uri
     * @param params
     * @return
     */
    public HttpResult doPost(String uri, Map<String, String> params) {
        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder(uri);
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
            return HttpResult.FAIL_URISyntaxException();
        }

        // 创建 http POST 请求
        HttpPost httpPost = new HttpPost();
        httpPost.setConfig(requestConfig);
        try {
            httpPost.setURI(uriBuilder.build());
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
            return HttpResult.FAIL_URISyntaxException();
        }
        if (params != null) {
            // 重构post提交的参数列表
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
            for (String key : params.keySet()) {
                parameters.add(new BasicNameValuePair(key, params.get(key)));
            }
            // 构造一个 form 表单式的实体
            UrlEncodedFormEntity formEntity;
            try {
                formEntity = new UrlEncodedFormEntity(parameters);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return HttpResult.FAIL_IOEXCEPTION();
            }
            // 将请求实体设置到 httpPost 对象中
            httpPost.setEntity(formEntity);
        }
        
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = this.getHttpClient().execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                return HttpResult.SUCCESS(content);
            } else {
                return HttpResult.SUCCESS("get请求中，响应编号不是200，联系开发人员");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return HttpResult.FAIL_ClientProtocolException();
        } catch (IOException e) {
            e.printStackTrace();
            return HttpResult.FAIL_IOEXCEPTION();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 封装 post 请求（Json 请求）
     * @param json
     * @return
     */
    public HttpResult doPostJson(String uri, String json) {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(requestConfig);
        if (null != json) {
            // 标识出传递的参数是 application/json
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
        }
        
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = this.getHttpClient().execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                return HttpResult.SUCCESS(content);
            } else {
                return HttpResult.SUCCESS("get请求中，响应编号不是200，联系开发人员");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return HttpResult.FAIL_ClientProtocolException();
        } catch (IOException e) {
            e.printStackTrace();
            return HttpResult.FAIL_IOEXCEPTION();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }        
    }

    


}