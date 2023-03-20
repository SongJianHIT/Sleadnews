/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia.controller
 * @className com.heima.wemedia.controller.WmNewsController
 */
package com.heima.wemedia.controller;

import com.heima.common.dtos.PageResponseResult;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WmNewsController
 * @description
 * @author SongJian
 * @date 2023/3/20 19:37
 * @version
 */
@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController {
    @Autowired
    private WmNewsService wmNewsService;

    /**
     * 自媒体文章查询
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public PageResponseResult findList(@RequestBody WmNewsPageReqDto dto){
        return wmNewsService.findList(dto);
    }
}
