package com.shdd.cfs.web.data.distribute;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class directoryInfo {
    /**
     * 点击某文件夹时，返回该文件夹路径下文件夹
     */
    @GetMapping(value = "api/dashboard/disk/dirs")
    @ApiOperation(value = "点击某文件夹时，返回该文件夹路径下文件夹")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String",
                    name = "path", value = "码表类型", required = true)
    })

    public JSONObject GetDirsData(String path) {
        log.info(path);

        return null;
    }
}
