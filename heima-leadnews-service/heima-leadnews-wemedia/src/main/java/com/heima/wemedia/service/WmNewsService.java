package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.PageResponseResult;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;

public interface WmNewsService extends IService<WmNews> {
    /**
     * 条件、分页查询自媒体文章
     * @param dto
     * @return
     */
    PageResponseResult findList(WmNewsPageReqDto dto);

    /**
     * 发布文章，也可提交草稿、修改文章
     * @param dto
     * @return
     */
    ResponseResult submit(WmNewsDto dto);
}
