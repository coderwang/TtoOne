package com.shdd.cfs.utils.page;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;

public class PageOpt {
	/**
	 *后台处理分页逻辑
	 * @param page_num  前台访问的页码
	 * @param totalPage 总页数
	 * @param count     每页显示的条目号
	 * @param tapenum   总的条目个数
	 * @param tapearrary 接收总条目的数组对象
	 * @return   page_num 页面需要显示的 JsonArray 对象
	 */
	public static JSONArray PagingLogicProcessing (Integer page_num, Integer totalPage, Integer count, Integer tapenum, ArrayList tapearrary ){
		JSONArray Jarray = new JSONArray();
		if(page_num < totalPage || page_num == totalPage){
			int mod = tapenum / count; //取模
			int rem = tapenum % count; //取余
			if(mod == 0 & rem > 0){
				for(int i = 0; i < rem ; i++){
					Jarray.add(tapearrary.get(i));
				}
			}else if((mod > 0 & rem == 0) || !(mod < page_num) ){
				int page = (page_num - 1) * count;
				for(int i = page; i < count+page; i++) {
					Jarray.add(tapearrary.get(i));
				}
			}else {
				int initnum = (page_num- 1)*count;
				for(int i = initnum; i < rem + initnum; i++)
				{
					Jarray.add(tapearrary.get(i));
				}
			}
		}
		return Jarray;
	}
}
