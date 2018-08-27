package com.shdd.threeinone.web.DataVisualization;

import com.shdd.threeinone.dto.DataVisualization.RootFolderName;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;



@RestController
@Slf4j
public class RootDirFolderName {
    /*
     * 获取目录下文件夹名称
     * */

    @GetMapping(value = "dashboard/rootdirs")
    @ApiOperation(value = "获取目录下的文件夹名")

    public JSONObject SendRootPathFolderName(String val){
        log.info(val);

        JSONObject rootFolder = new  JSONObject();
        ArrayList<String> folderList = new ArrayList<String>();
        /* 将目录下的文件夹添加到文件夹列表*/
        File file = new File("/");
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
        RootFolderName[] rootforder = new RootFolderName[folderList.size()];
        for(int i = 0; i< folderList.size(); i++){
            rootforder[i] = new RootFolderName();
            rootforder[i].setId(i + 1);
            rootforder[i].setName(folderList.get(i));
        }
        /*将文件夹数组塞入Json对象中*/
        rootFolder.accumulate("folder",rootforder);
        /* 发送Json 协议*/
        return  rootFolder;
    }
}
