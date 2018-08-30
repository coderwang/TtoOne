package com.shdd.threeinone.HandleTools.XmlMessageHandle;

import java.io.*;
import java.net.*;

public class Xmlhandle {
        public static String fromUrlGetxml(String url) throws IOException {
                URLConnection uc = new URL(url).openConnection();
                uc.setConnectTimeout(10000);
                uc.setDoOutput(true);
                InputStream in = new BufferedInputStream(uc.getInputStream());
                StringBuffer temp = new StringBuffer();
                Reader rd = new InputStreamReader(in,"UTF-8");
                int c = 0;
                while ((c = rd.read()) != -1) {
                        temp.append((char) c);
                }
                in.close();
                return temp.toString();
        }
}
