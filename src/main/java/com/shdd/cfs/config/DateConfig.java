package com.shdd.cfs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateConfig {
   //获取配置文件挂载点路径
   @Value("${mount.distributemount}")
   private String distributemount;
   //获取光盘库ip
   @Value("${OpticalLib.opticalip}")
   private String opticalIP;
   //获取光盘库后台访问端口
   @Value("${OpticalLib.opticalport}")
   private String opticalPort;
   //获取光盘库中单个光盘存储容量
   @Value("${OpticalLib.singleDiskCapacity}")
   private String singleDiskCapacity;
   //获取光盘库中单个光盘匣盛放光盘个数
   @Value("${OpticalLib.singleBoxCardNum}")
   private String singleBoxCardNum;
   //获取磁带库网页访问用户名
   @Value("${TapeLib.webusername}")
   private String tapewebusername;
   //获取磁带库sessionKey
   @Value("${TapeLib.sessionKey}")
   private String sessionKey;
   //获取分布式Url 接口
   @Value("${Distribute.url}")
   private String distributeUrl;
   //获取光盘库挂载点
   @Value("${mount.opticalmount}")
   private String opticalmount;
   //获取磁带库挂载点
   @Value("${mount.tapemount}")
   private String tapemount;

   public String getOpticalMount(){
      return  opticalmount;
   } //获取光盘库挂载点

   public String getTapeMount() {
      return  tapemount;
   }// 获取磁带库挂载点

   public String getDistributeUrl(){
      return distributeUrl;
   }//获取分布式URL

   public String getTapewebusername(){
      return tapewebusername;
   }//获取磁带库web用户名

   public String getSessionKey(){
      return sessionKey;
   } //获取网页sessionkey

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

