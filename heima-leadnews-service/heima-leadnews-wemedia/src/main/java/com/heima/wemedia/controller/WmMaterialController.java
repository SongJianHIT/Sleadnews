/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia.controller
 * @className com.heima.wemedia.controller.WmMaterialController
 */
package com.heima.wemedia.controller;

import com.heima.common.dtos.PageResponseResult;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * WmMaterialController
 * @description
 * @author SongJian
 * @date 2023/3/20 14:13
 * @version
 */
@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController {
    @Autowired
    private WmMaterialService wmMaterialService;

    /**
     * 上传素材
     */
    @PostMapping("/upload_picture")
    public ResponseResult uploadPicture(MultipartFile multipartFile){
        return wmMaterialService.uploadPicture(multipartFile);
    }


    /**
     * 列表查询，根据条件、排序、进行分页查询
     * @param wmMaterialDto
     * @return
     */
    @PostMapping("/list")
    public PageResponseResult list(@RequestBody WmMaterialDto wmMaterialDto) {
        return wmMaterialService.findList(wmMaterialDto);
    }


}

