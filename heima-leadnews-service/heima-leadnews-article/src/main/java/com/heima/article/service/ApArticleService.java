/**
 * @projectName heima-leadnews
 * @package com.heima.article.service
 * @className com.heima.article.service.ApArticleService
 */
package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.article.dtos.ApArticleDto;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.pojos.ApArticle;

/**
 * ApArticleService
 * @description
 * @author SongJian
 * @date 2023/3/16 08:21
 * @version
 */
public interface ApArticleService extends IService<ApArticle> {
    /**
     * 根据 用户行为 查询文章
     * @param dto
     * @param behave
     * @return
     */
    ResponseResult loadApArticle(ArticleDto dto, Integer behave);

    /**
     * 保存文章（新增或修改）
     * @param dto
     * @return article id
     */
    public Long saveApArticle(ApArticleDto dto);
}

