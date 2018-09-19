package com.shdd.cfs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateConfig {

   @Value("${mount.distributemount}")
   private String distributemount;

   @Value("${OpticalLib.opticalip}")
   private String opticalIP;

   @Value("${OpticalLib.opticalport}")
   private String opticalPort;

   @Value("${OpticalLib.singleDiskCapacity}")
   private String singleDiskCapacity;

   @Value("${OpticalLib.singleBoxCardNum}")
   private String singleBoxCardNum;

   public  String getdistributemount(){
      return distributemount;
   }// 获取分布式挂载点路径

   public String getOpticalIP(){
      return opticalIP;
   }//获取光盘库节点IP

   public String getOpticalPort(){
      return opticalPort;
   }//获取光盘库节点后台访问端口

   public String getSingleDiskCapacity(){
      return singleDiskCapacity;
   }//获取单个光盘容量

   public String getSingleBoxCardNum(){
      return singleBoxCardNum;
   }//获取单个光盘匣光盘个数
}

