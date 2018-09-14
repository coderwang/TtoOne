package com.shdd.cfs.utils.base;


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




}
