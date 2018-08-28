package com.shdd.cfs.utils.json;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class GetJsonMessage {
    /*
     * 处理Json报文类型
     * */
    public static JSONObject GetJsonStr(String hostip, Integer hostport, String JsonStr) {
        /*
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
}


