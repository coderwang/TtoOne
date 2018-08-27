package com.shdd.threeinone.web.DataVisualization;

import com.shdd.threeinone.dto.DataVisualization.CurrentPathFileName;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;


@RestController
@Slf4j
public class GetCurrentPathFileName {

    /*
     * 获取目录下文件名称
     * */
    @GetMapping(value = "gg/file")
    @ApiOperation(value = "获取目录下的文件名")

    public JSONObject SendRootPathFolderName(String val){
        log.info(val);
        JSONObject jfile = new  JSONObject();
        ArrayList<CurrentPathFileName> fileList = new ArrayList<CurrentPathFileName>();
        /* 将目录下的文件名和文件类型添加到文件列表*/
        File file = new File("D:\\AstServer");
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                String fileAndPath = tempList[i].toString();
                File fileName = new File(fileAndPath.trim());
                String filename = fileName.getName();
                String type = FilenameUtils.getExtension(filename);
                System.out.println("Mime Type of " + filename + " is " + type);
                CurrentPathFileName fileAttribute = new CurrentPathFileName();
                fileAttribute.setName(filename);
                fileAttribute.setType(type);
                fileList.add(fileAttribute);
            }
        }
        /* 将文件名和文件属性赋值给Json数组中*/
        CurrentPathFileName[] fileNameArr = new CurrentPathFileName[fileList.size()];
        for(int i = 0; i< fileList.size(); i++){
            fileNameArr[i] = new CurrentPathFileName();
            fileNameArr[i].setId(i + 1);
            fileNameArr[i].setName(fileList.get(i).getName());
            fileNameArr[i].setType(fileList.get(i).getType());
        }
        /*将文件数组塞入Json对象中*/
        jfile.accumulate("file",fileNameArr);
        /* 发送Json 协议*/
        return  jfile;
    }
}
