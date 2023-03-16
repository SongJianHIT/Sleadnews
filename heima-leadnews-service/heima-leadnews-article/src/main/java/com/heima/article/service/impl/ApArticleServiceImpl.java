/**
 * @projectName heima-leadnews
 * @package com.heima.article.service.impl
 * @className com.heima.article.service.impl.ApArticleServiceImpl
 */
package com.heima.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ApArticleService;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.pojos.ApArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * ApArticleServiceImpl
 * @description
 * @author SongJian
 * @date 2023/3/16 08:22
 * @version
 */
@Service
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {

    @Autowired
    private ApArticleMapper apArticleMapper;

    /**
     * 根据 用户行为 查询文章
     * @param dto
     * @param behave
     * @return
     */
    @Override
    public ResponseResult loadApArticle(ArticleDto dto, Integer behave) {
        // 给 dto 填上默认值，避免为空
        if (dto.getMinBehotTime() == null) {
            dto.setMinBehotTime(new Date());
        }
        if (dto.getMaxBehotTime() == null) {
            dto.setMaxBehotTime(new Date());
        }
        if (dto.getSize() == null) {
            dto.setSize(10);
        }
        if (dto.getTag() == null) {
            dto.setTag("__all__");
        }
        // 调用 mapper
        int behaveType = behave.intValue();
        List<ApArticle> articles = apArticleMapper.loadApArticle(dto, behaveType);
        return ResponseResult.okResult(articles);
    }
}

