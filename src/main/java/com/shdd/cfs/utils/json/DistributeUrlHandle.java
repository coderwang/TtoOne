package com.shdd.cfs.utils.json;

import net.sf.json.JSONObject;

public class DistributeUrlHandle {

	private static String baseurl;

	/**
	 *通过url请求向分布式后台请求集群信息
	 * @return 分布式后台返回的json对象
	 */
	public static JSONObject Poolinfo () {
		//获取分布式单个集群信息
		String disstorageinfo = baseurl + "api/monitor/clustes/cluster_id/storage/";
		String discapacity = GetJsonMessage.getURLContent(disstorageinfo);
		//获取分布式回传的json报文
		JSONObject disjsonobject = JSONObject.fromObject(discapacity);
		return disjsonobject;
	}

	/**
	 * 通过URL请求向分布式式后台请求整个集群信息
	 * @return 分布式后台返回的json对象
	 */
	public static JSONObject CulsterInfo(){
		//访问分布式整个集群url接口
		String disstorageinfo = baseurl + "api/monitor/clustes/storage/";
		String discapacity = GetJsonMessage.getURLContent(disstorageinfo);
		//获取分布式回传的json报文
		JSONObject disjsonobject = JSONObject.fromObject(discapacity);
		return  disjsonobject;
	}
}

