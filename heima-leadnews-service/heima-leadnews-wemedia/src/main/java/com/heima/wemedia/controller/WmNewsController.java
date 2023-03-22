/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia.controller
 * @className com.heima.wemedia.controller.WmNewsController
 */
package com.heima.wemedia.controller;

import com.heima.common.dtos.PageResponseResult;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 发布文章，也可提交草稿、修改文章
     * @param dto
     * @return
     */
    @PostMapping("/submit")
    public ResponseResult submit(@RequestBody WmNewsDto dto) {
        return wmNewsService.submit(dto);
    }

    /**
     * 修改回显文章
     */
    @GetMapping("/one/{id}")
    public ResponseResult<WmNews> findOne(@PathVariable("id") Integer id){
        return ResponseResult.okResult(wmNewsService.getById(id));
    }
}
