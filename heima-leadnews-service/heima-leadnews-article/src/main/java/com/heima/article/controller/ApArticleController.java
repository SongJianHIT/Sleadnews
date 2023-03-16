/**
 * @projectName heima-leadnews
 * @package com.heima.article.controller
 * @className com.heima.article.controller.ApArticleController
 */
package com.heima.article.controller;

import com.heima.article.common.ApArticleConstant;
import com.heima.article.service.ApArticleService;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.article.dtos.ArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ApArticleController
 * @description
 * @author SongJian
 * @date 2023/3/16 08:29
 * @version
 */
@RestController
@RequestMapping("/api/v1/article")
public class ApArticleController {

    @Autowired
    private ApArticleService apArticleService;

    /**
     * 查询首页，上拉
     * @param dto
     * @return
     */
    @PostMapping("/load")
    public ResponseResult load(@RequestBody ArticleDto dto) {
        // 上拉
        return apArticleService.loadApArticle(dto, ApArticleConstant.UPBEHAVE);
    }

    /**
     * 查询更多文章，上拉
     * @param dto
     * @return
     */
    @PostMapping("/loadmore")
    public ResponseResult loadMore(@RequestBody ArticleDto dto) {
        //1 代表上拉(小于判断)
        return apArticleService.loadApArticle(dto, ApArticleConstant.UPBEHAVE);
    }

    /**
     * 查询更新文章，下拉
     * @param dto
     * @return
     */
    @PostMapping("/loadnew")
    public ResponseResult loadNew(@RequestBody ArticleDto dto) {
        //1 代表上拉(小于判断)
        return apArticleService.loadApArticle(dto, ApArticleConstant.DOWNBEHAVE);
    }
}

