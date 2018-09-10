/*
 *
 * THIS FILE IS PART OF COLD FUSION STORAGE NEXUS PROJECT
 * Copyright (c) 2018 盛和大地数据科技公司 版权所有
 *
 */
package com.shdd.cfs.web.device.distribute;

import com.shdd.cfs.dto.device.DiskDetailInfo;
import com.shdd.cfs.utils.json.HttpRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Slf4j

public class DistSpecificPoolDisksDetail {
    /**
     * 主机信息状态下获取分布式存储系统节点详细概况
     * <p>
     * <<<<<<< HEAD
     *
     * @param =======
     * @param poolid
     * @param page_num
     * @param count    >>>>>>> feature/cfs_nexus_distribute_general
     * @return
     */
    @GetMapping(value = "api/dashboard/distribute/pool/disks")
    @ApiOperation(value = "主机信息状态下获取分布式存储系统节点详细概况", notes = "分布式系统指定存储池中的磁盘详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "poolid", value = "分布式存储池ID", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "page_num", value = "翻页页码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "count", value = "每页所含最大条目数", required = true)
    })

    public JSONObject DistributeStorageInfo(int poolid, int page_num, int count) {


        //"disk": [{
        //            "id": 1,
        //            "name": "xx",
        //            "hostName": "xx", //所属主机名
        //            "used": 20,
        //            "capacity": 50,
        //            "status": 1||0    //1在线 0离线
        //}
        //]

        //访问下级分布式系统接口api/volumes/{volume_id}
        HttpRequest httpRequest = new HttpRequest();

        String result = httpRequest.sendGet("http://192.168.1.32:8000/api/volumes/" + poolid, " ");
        JSONObject volumeObject = JSONObject.fromObject(result);

        //brick详细信息
        JSONArray bricksArray = volumeObject.getJSONArray("bricks");
        JSONObject brickObject;
        int brickCount = bricksArray.size();

        //页码处理
        int page_count = 0;

        //数据处理
        DiskDetailInfo diskDetail = new DiskDetailInfo();
        JSONArray diskList = new JSONArray();

        for (int i = 0; i < brickCount; i++) {

            //翻页
            if ((i + 1) <= (page_num - 1) * count) {
                continue;
            }
            brickObject = bricksArray.getJSONObject(i);
            diskDetail.setId(brickObject.getString("disk_id"));
            diskDetail.setName(brickObject.getString("disk_name"));
            diskDetail.setHostname(brickObject.getString("host_name"));
            diskDetail.setUsed(brickObject.getString("used"));
            diskDetail.setCapacity(brickObject.getString("total"));
            diskDetail.setStatus(Integer.parseInt(brickObject.getString("disk_state")));
            //
            diskList.add(diskDetail);
            page_count += 1;
            if (page_count == count) {
                break;
            }

        }

        //数据打包
        JSONObject jarrary = new JSONObject();

        //计算总页数
        int totalPage = 0;
        if (count != 0) {
            totalPage = (brickCount % count != 0) ? ((brickCount / count) + 1) : (brickCount / count);
        }

        jarrary.accumulate("totalPage", totalPage);
        jarrary.accumulate("disk", diskList);
        return jarrary;
    }
}
