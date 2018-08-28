package com.shdd.cfs.utils.json;

import com.shdd.cfs.ColdFusionStorageNexus;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.boot.SpringApplication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

/**
 * 处理Json报文类型
 */
public class GetJsonMessage {
    public static JSONObject GetJsonStr(String hostip, Integer hostport, String JsonStr) {
        /**
         * 向主机 hostip 的hostport 端口发送 报文 JsonStr ，return 接收到的报文
         */
        DataInputStream is;
        DataOutputStream os;

        JSONObject result = new JSONObject();
        try {
            Socket socket = new Socket(InetAddress.getByName(hostip), hostport);
            is = new DataInputStream(socket.getInputStream());
            os = new DataOutputStream(socket.getOutputStream());
            PrintWriter pw = new PrintWriter(os);
            System.out.println("client resquest " + JsonStr);
            pw.println(JsonStr);
            pw.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            JSONObject json = JSONObject.fromObject(in.readLine());
            result = json;
            is.close();
            os.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 请求的url链接  返回的是json字符串
     */
    public static String getURLContent(String urlStr) {
        //请求的url
        URL url = null;
        //建立的http链接
        HttpURLConnection httpConn = null;
        //请求的输入流
        BufferedReader in = null;
        //输入流的缓冲
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(urlStr);
            in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String str = null;
            //一行一行进行读入
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception ex) {
        } finally {
            try {
                if (in != null) {
                    in.close(); //关闭流
                }
            } catch (IOException ex) {
            }
        }
        String result = sb.toString();
        return result;
    }
}


