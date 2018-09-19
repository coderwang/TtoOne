package com.shdd.cfs.utils.base;


import com.shdd.cfs.dto.data.DirPathDetailInfo;
import net.sf.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class UnitHandle {

	public static Double unitConversionToGB(String val){
		String unit = val.substring(val.length()-1,val.length());
		Double Capacity = 0.0;
		if (unit.equals("T")) {
			//根据单位信息，转成GB级别数据
			Double value = Double.parseDouble(val.substring(0,val.length()-1));
			Capacity = value * 1024;
		} else if (unit.equals("G")) {
			Double value = Double.parseDouble(val.substring(0,val.length()-1));
			Capacity = value;
		} else if (unit.equals("M")) {
			//根据单位信息为MB，转成GB级别数据
			Double value = Double.parseDouble(val.substring(0,val.length()-1));
			Capacity = value / 1024 ;
		}else {
			//根据单位信息为Byte，转成GB级别数据
			Capacity = Double.parseDouble(val) /1024 / 1024 / 1024;
		}
		return Capacity;
	}

	public static Double unitConversionToTB(String val){
		String unit = val.substring(val.length()-1,val.length());
		Double Capacity = 0.0;
		if (unit.equals("T")) {
			//根据单位信息，转成TB级别数据
			Double value = Double.parseDouble(val.substring(0,val.length()-1));
			Capacity = value ;
		} else if (unit.equals("G")) {
			//根据单位信息，转成TB级别数据
			Double value = Double.parseDouble(val.substring(0,val.length()-1));
			Capacity = value /1024 ;
		} else if (unit.equals("M")) {
			//根据单位信息为MB，转成TB级别数据
			Double value = Double.parseDouble(val.substring(0,val.length()-1));
			Capacity = value / 1024 /1024;
		}else if(unit.equals("k") || unit.equals("kb") || unit.equals("KB")){
			Double value = Double.parseDouble(val.substring(0,val.length()-1));
			Capacity = value / 1024 / 1024 /1024;
		} else {
			//根据单位信息为Byte，转成TB级别数据
			Capacity = Double.parseDouble(val) /1024 /1024 / 1024 / 1024;
		}
		return Capacity;
	}

	/**
	 * @param fileDir  根目录
	 * @param num 递归层次
	 */
	private static void consoleFile(String fileDir, int num) {
		File file = new File(fileDir);
		File[] files = file.listFiles();// 获取目录下的所有文件或文件夹
		if (files == null) {// 如果目录为空，直接退出
			return;
		}
		// 遍历，目录下的所有文件夹
		num+=1;
		for (File f : files) {
			System.out.println(f.getAbsoluteFile());
			if (f.isDirectory()) {
				consoleFile(f.getAbsolutePath(), num);
			}
		}
	}
	/**
	 * 截取最后一个/后面的所有字符
	 * @param Str  要截取的字符串
	 * @return 最后一个/后面的所有字符
	 */
	private static String spliteStr(String Str) {
		return Str.substring(Str.lastIndexOf("\\")+1);
	}

	/**
	 * 获取指定文件夹下的所有文件夹名称
	 * @param pathfolder  指定的文件夹路径
	 * @return
	 */
	public static JSONObject SendPathFolderName(String pathfolder){

		JSONObject rootFolder = new JSONObject();
		ArrayList<String> folderList = new ArrayList<String>();
		/* 将目录下的文件夹添加到文件夹列表*/
		File file = new File(pathfolder);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isDirectory()) {
				String folderAndPath = tempList[i].toString();
				File fName = new File(folderAndPath.trim());
				String folderName = fName.getName();
				folderList.add(folderName);
			}
		}
		/* 将文件夹名赋值给Json数组中*/
		DirPathDetailInfo[] rootforder = new DirPathDetailInfo[folderList.size()];
		for(int i = 0; i< folderList.size(); i++){
			rootforder[i] = new DirPathDetailInfo();
			rootforder[i].setId(i + 1);
			rootforder[i].setName(folderList.get(i));
			System.out.println(folderList.get(i));
		}
		/*将文件夹数组塞入Json对象中*/
		rootFolder.accumulate("folder",rootforder);
		/* 发送Json 协议*/
		return  rootFolder;
	}

	public static void main(String[] args) {
		//consoleFile("D:\\cdrskin", 0);
	}
}
