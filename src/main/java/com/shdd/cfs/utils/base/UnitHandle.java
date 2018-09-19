package com.shdd.cfs.utils.base;


import com.shdd.cfs.dto.data.DirPathDetailInfo;
import com.shdd.cfs.dto.data.FilePathDetailInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FilenameUtils;

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
	 * @param fileDir  要访问目录的绝对路径
	 * @param num 记录目录层级，当前目录num=0,当前目录下的为第一级目录num=1，依次类推
	 */
	private static void consoleFile(String fileDir, int num, JSONArray dirarrary, JSONArray filearrary) {
		File file = new File(fileDir);
		File[] files = file.listFiles();// 获取目录下的所有文件或文件夹
		if (files == null) {// 如果目录为空，直接退出
			return;
		}
		// 遍历，目录下的所有文件夹
		for (File f : files) {
			if (f.isDirectory()) {
				DirPathDetailInfo diratt = new DirPathDetailInfo();
				diratt.setId(num);
				diratt.setName(spliteStr(f.getAbsolutePath()));
				dirarrary.add(diratt);
				consoleFile(f.getAbsolutePath(), num+1,dirarrary,filearrary);
			}else if(f.isFile()){
				FilePathDetailInfo fileatt = new FilePathDetailInfo();
				fileatt.setId(num);
				fileatt.setName(spliteStr(f.getAbsolutePath()));
				File fileName = new File(f.getAbsolutePath().trim());
				String filename = fileName.getName();
				String type = FilenameUtils.getExtension(filename);
				fileatt.setType(type);
				filearrary.add(fileatt);
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
	public static DirPathDetailInfo[] SendCurrentFolderName(String pathfolder){

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
		}
		return  rootforder;
	}

	public static FilePathDetailInfo[] getCurrentPathFilename (String path) {
		ArrayList<FilePathDetailInfo> fileList = new ArrayList<FilePathDetailInfo>();
		/* 将目录下的文件名和文件类型添加到文件列表*/
		File file = new File(path);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				String fileAndPath = tempList[i].toString();
				File fileName = new File(fileAndPath.trim());
				String filename = fileName.getName();
				String type = FilenameUtils.getExtension(filename);
				FilePathDetailInfo fileAttribute = new FilePathDetailInfo();
				fileAttribute.setName(filename);
				fileAttribute.setType(type);
				fileList.add(fileAttribute);
			}
		}
		/* 将文件名和文件属性赋值给Json数组中*/
		FilePathDetailInfo[] fileNameArr = new FilePathDetailInfo[fileList.size()];
		for (int i = 0; i < fileList.size(); i++) {
			fileNameArr[i] = new FilePathDetailInfo();
			fileNameArr[i].setId(i + 1);
			fileNameArr[i].setName(fileList.get(i).getName());
			fileNameArr[i].setType(fileList.get(i).getType());
		}
		return fileNameArr;
	}

	public static void main(String[] args) {
		JSONArray dirarrary = new JSONArray();
		JSONArray filearrary = new JSONArray();
		consoleFile("D:\\test", 0, dirarrary, filearrary);
		for(int i = 0 ; i < dirarrary.size(); i++){
			System.out.println(dirarrary.getJSONObject(i).get("id")+"=dir="+ dirarrary.getJSONObject(i).get("name"));
		}
		for(int i = 0 ; i < filearrary.size(); i++){
			System.out.println(filearrary.getJSONObject(i).get("id")+"=file="+ filearrary.getJSONObject(i).get("name")+"==="+ filearrary.getJSONObject(i).get("type"));
		}
	}

}
