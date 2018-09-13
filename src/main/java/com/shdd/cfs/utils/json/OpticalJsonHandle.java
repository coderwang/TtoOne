package com.shdd.cfs.utils.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OpticalJsonHandle {
	private enum CdApiEnum implements com.shdd.cfs.utils.json.CdApiEnum {
		nodeconnect("{\"protoname\":\"nodeconnect\"}",                      "2.1 查询任务状态"),
		statsinfo("{\"protoname\":\"statsinfo\"}",							"12.光盘库统计信息【statsinfo"),
		cpuinfo("{\"protoname\":\"cpuinfo\"}",								"2.获取节点cpu信息【cpuinfo】"),
		basicinfo("{\"protoname\":\"basicinfo\"}",							"7.光盘库基本信息【basicinfo】-光盘库"),
		memcapacity("{\"protoname\":\"memcapacity\"}",						"6.内存使用量【memcapacity】"),
		rammonitor("{\"protoname\":\"rammonitor\"}",						"4.内存实时利用率【rammonitor】"),
		cpumonitor("{\"protoname\":\"cpumonitor\"}",						"3.CPU实时利用率【cpumonitor】"),
		netmonitor("{\"protoname\":\"netmonitor\"}",						"5.网络实时情况【netmonitor】"),
		cdboxlist("{\"protoname\":\"cdboxlist\"}",							"8.光盘匣列表【cdboxlist】-光盘匣");
		private final String protocol;
		private CdApiEnum(String protocol, String name) {this.protocol = protocol;}
		public String getPath() {return protocol;}
	}
	private static String ip ="192.168.1.17";
	private static Integer  port = 8000;
	private static Double singleDiskCapacity = 23.3;

	/**
	 * 获取光盘库总容量和使用容量
	 * @return 光盘库总容量和使用容量Map对象
	 */
	public static Map<String, Double> getCDLibCapacity(){
		Map<String,Double> libCapacity = new HashMap<>();
		JSONObject cdLibCapacity = GetJsonMessage.GetJsonStr(ip,port,CdApiEnum.nodeconnect.getPath());
		Double allOptCapacity = Double.parseDouble(cdLibCapacity.getString("totalinfo"));
		Double useOptCapacity = Double.parseDouble(cdLibCapacity.getString("usedinfo"));
		System.out.println(allOptCapacity + "123456" + useOptCapacity);
		libCapacity.put("capacity",allOptCapacity);
		libCapacity.put("used",useOptCapacity);
		return libCapacity;
	}
	/**
	 * 获取在线光盘个数和在线空白盘个数
	 * @return 在线光盘信息Map对象
	 */
	public static Map<String, Integer> OnlineCdInfo(){
		Map<String,Integer> onlineCd = new HashMap<>();
		JSONObject cdCardOline = GetJsonMessage.GetJsonStr(ip,port,CdApiEnum.statsinfo.getPath());
		Integer olineCard = cdCardOline.getInt("label04");
		Integer olineFreeCard = cdCardOline.getInt("label05");
		onlineCd.put("totalOlineCard",olineCard);
		onlineCd.put("freeOlineCard",olineFreeCard);
		return onlineCd;
		}
	/**
	 * 获取光盘库节点CPU基本信息
	 * @return CPU 型号，CPU个数
	 */
	public static Map<String,String> CpuInfo(){
		Map<String,String>  cpuinfo = new HashMap<>();
		JSONObject cpuObject = GetJsonMessage.GetJsonStr(ip,port,CdApiEnum.cpuinfo.getPath());
		String cputype = cpuObject.getString("cputype");
		String cpucount = cpuObject.getString("cpucount");
		cpuinfo.put("cputype",cputype);
		cpuinfo.put("cpucount",cpucount);
		return cpuObject;
	}
	/**
	 * 获取光盘库节点基本信息
	 * @return 光盘库节点ID，型号，光盘库名称
	 */
	public static Map<String,String> cdLibBasicInfo(){
		Map<String,String>  cdlibbasic = new HashMap<>();
		JSONObject cdLibObject = GetJsonMessage.GetJsonStr(ip,port,CdApiEnum.basicinfo.getPath());
		String jukeid = cdLibObject.getString("jukeid");//指光盘库ID  序号
		String label = cdLibObject.getString("label");//指光盘库型号
		String name = cdLibObject.getString("name");//指光盘库名称
		cdlibbasic.put("jukeid",jukeid);
		cdlibbasic.put("label",label);
		cdlibbasic.put("name",name);
		return cdlibbasic;
	}

	/**
	 *获取光盘库节点内存使用情况
	 * @return
	 */
	public static Map<String,String> cdLibMemInfo(){
		Map<String,String>  cdLibMemInfo = new HashMap<>();
		JSONObject cdLibMemObject = GetJsonMessage.GetJsonStr(ip,port,CdApiEnum.memcapacity.getPath());
		String memused = cdLibMemObject.getString("memused");//指内存已使用量
		String memtotal = cdLibMemObject.getString("memtotal");//指内存总量
		String memfree = cdLibMemObject.getString("memfree");//指内存剩余量
		cdLibMemInfo.put("memused",memused);
		cdLibMemInfo.put("memtotal",memtotal);
		cdLibMemInfo.put("name",memfree);
		return cdLibMemInfo;
	}
	/**
	 * 光盘库内存利用率部分，获取光盘库当前内存情况
	 * @return 当前内存利用率
	 */
	public static Double cdLibRammonitor(){
		JSONObject cdLibMemObject = GetJsonMessage.GetJsonStr(ip,port,CdApiEnum.rammonitor.getPath());
		Double meminfo = cdLibMemObject.getDouble("rammon");
		return meminfo;
	}
	/**
	 * 光盘库CPU利用率部分，获取光盘库当前CPU使用情况
	 * @return 当前CPU利用率
	 */
	public static Double cdLibCpumonitor(){
		JSONObject cdLibCpuObject = GetJsonMessage.GetJsonStr(ip,port,CdApiEnum.cpumonitor.getPath());
		double cpumon = cdLibCpuObject.getDouble("cpumon");
		return cpumon;
	}
	/**
	 *光盘库网络利用率，获取当前网络情况
	 * @return 当前网络利用率
	 */
	public static Double cdLibnetmonitor(){
		JSONObject cdLibNetObject = GetJsonMessage.GetJsonStr(ip,port,CdApiEnum.netmonitor.getPath());
		double netmon = cdLibNetObject.getDouble("netmon");
		return netmon;
	}
	/**
	 * 获取光盘匣列表
	 * @return 光盘匣数组信息（数组名，数组使用容量，总容量，剩余容量）
	 */
	public static JSONArray cdboxlist(){
		JSONObject cdlist = GetJsonMessage.GetJsonStr(ip,port,CdApiEnum.cdboxlist.getPath());
		JSONArray boxlists = cdlist.getJSONArray("dt");
		return boxlists;
	}

	/**
	 * 通过指定光盘匣ID获取光盘匣内所有光盘信息
	 * @return 光盘匣ID内光盘属性
	 */
	public static JSONArray cdslotlist(String id ){
		String cdslotlist = "{\"protoname\":\"cdslotlist\",\"cdboxid\":\"01\"}";
		String protocol = cdslotlist.replace("01", id);
		JSONObject cdslotlists = GetJsonMessage.GetJsonStr(ip,port,protocol);
		JSONArray cdslotlistarray = cdslotlists.getJSONArray("dt");
		return  cdslotlistarray;
	}

	/**
	 * 通过光盘匣id内光盘总容量属性是否为0判断进线与离线光盘数, 0 表示离线，非0表示在线
	 * @param id
	 * @return
	 */
	public static Integer getCardOlineNumInbox(String id){
		Integer num = 0;
		JSONArray cardArrary = cdslotlist(id);
		for( int i = 0 ; i < cardArrary.size(); i++){
			String cdinfo = cardArrary.getJSONObject(i).get("cdinfo").toString();
			if(Integer.parseInt(cdinfo) != 0){
				//计算在线光盘个数
				num++;
			}
		}
		return  num;
	}

	/**
	 *获取在线光盘匣（光盘匣内有在线光盘为在在光盘匣）
	 * @return
	 */
	public static ArrayList<Integer> getOlineBoxNum(){
		ArrayList<Integer> onlineBox = new ArrayList<>();
		JSONObject statsinfo = GetJsonMessage.GetJsonStr(ip,port,CdApiEnum.statsinfo.getPath());
		Integer boxnum = statsinfo.getInt("label01");
		for(int i = 0 ; i < boxnum ; i++){
			int j = i + 1;
			if(getCardOlineNumInbox(Integer.toString(j)) != 0){
				onlineBox.add(j);
			}

		}
		return  onlineBox;
	}
	/**
	 * 获取含有在线光盘的光盘匣中所有光盘
	 * @return 含有在线光盘的光盘匣中所有光盘
	 */
	public static ArrayList<JSONObject> cdslotlistOnlinePool(){
		ArrayList <JSONObject> cardlist = new ArrayList<>();
		//获取在线光盘匣列表
		ArrayList<Integer> boxid = getOlineBoxNum();
		for(int i = 0 ; i < boxid.size(); i++){
			//获取光盘匣列表中光盘属性
			JSONArray boxlistOline = cdslotlist(boxid.get(i).toString());
			for(int j = 0 ; j < boxlistOline.size(); j++){
				JSONObject card = boxlistOline.getJSONObject(j);
				cardlist.add(card);
			}
		}
		return cardlist;
	}

	/**
	 * 将所有光盘槽属性塞入数组
	 * @return 所有光盘槽属性
	 */
	public static ArrayList<JSONObject> getAllCardInfo(){
		 JSONObject statsinfo = GetJsonMessage.GetJsonStr(ip,port,CdApiEnum.statsinfo.getPath());
		 ArrayList<JSONObject> cdlists = new ArrayList<>();
		 Integer boxnum = statsinfo.getInt("label01");
		 for(int i = 1 ; i <= boxnum ; i++){
		 	JSONArray box = cdslotlist(Integer.toString(i));
		 	for(int j = 0 ; j < box.size(); j++){
				JSONObject cdslot = (JSONObject) box.getJSONObject(j);
				cdlists.add(cdslot);
			}
		 }
		 return cdlists;
	}

	/**
	 * 通过光盘类型判断光盘总容量大小
	 * @param tape 光盘库类型
	 * @return 光盘库容量大小
	 */
	public static Double getCapacityFromCDType(String tape){
		Double capacpty = 0.0; //GB
		//0 未知 1 CD  2 VCD  3DVD  4 BD
		if(tape.equals("4")){//BD
			capacpty = singleDiskCapacity ;
		}else if(tape.equals("2")){//VCD
			capacpty=4.7;
		}else if(tape.equals("1")){//CD
			capacpty=0.7;
		}else if(tape.equals("3")){//DVD
			capacpty=4.7;
		}else {
			capacpty = 0.0;
		}
		return  capacpty;
	}
}
