package com.shdd.threeinone.HandleTools.JsonMessageHandleTool;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class GetJsonMessage {
/*
* 处理Json报文类型
* */
    public String GetJsonStr(String hostip , Integer hostport, String JsonStr) throws IOException {
        /**
         * 向主机 hostip 的hostport 端口发送 报文 JsonStr ，return 接收到的报文
         */
        DataInputStream is;
        DataOutputStream os;
        Socket socket = new Socket(InetAddress.getByName(hostip), hostport);
        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());
        PrintWriter pw = new PrintWriter(os);
        System.out.println("client resquest " + JsonStr);
        pw.println(JsonStr);
        pw.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        is.close();
        os.close();
        return in.readLine();
    }
}


