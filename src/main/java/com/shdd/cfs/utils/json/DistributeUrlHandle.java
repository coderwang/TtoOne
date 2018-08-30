package com.shdd.cfs.utils.json;

import net.sf.json.JSONObject;

public class DistributeUrlHandle {

	private static String baseurl;

	/**
	 *通过url请求向分布式后台请求集群信息
	 * @return 分布式后台返回的json对象
	 */
	public static JSONObject ClusterInfo () {
		//获取分布式单个集群信息
		String disstorageinfo = baseurl + "api/monitor/clustes/storage/";
		String discapacity = GetJsonMessage.getURLContent(disstorageinfo);
		//获取分布式回传的json报文
		JSONObject disjsonobject = JSONObject.fromObject(discapacity);
		return disjsonobject;
	}
	/**
	 * 通过URL请求向分布式式后台请求整个集群信息
	 * @return 分布式后台返回的json对象
	 */
	public static JSONObject Poolinfo(){
		//访问分布式整个集群url接口
		String volsinfo = baseurl + "api/volumes/";
		String volinfo = GetJsonMessage.getURLContent(volsinfo);
		//获取分布式回传的json报文
		JSONObject vols = JSONObject.fromObject(volinfo);
		return  vols;
	}
	/**
	 * 通过URL请求获取分布式后台节点详细情况
	 */
	public static JSONObject Nodeinfo(){
		//访问分布式整个集群url接口
		String nodeinfo = baseurl + "api/hosts/host_id";
		String node = GetJsonMessage.getURLContent(nodeinfo);
		//获取分布式回传的json报文
		JSONObject jnode = JSONObject.fromObject(node);
		return  jnode;
	}
}


