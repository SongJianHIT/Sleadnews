/**
 * @projectName heima-leadnews
 * @package com.heima.article.service.impl
 * @className com.heima.article.service.impl.ApArticleServiceImpl
 */
package com.heima.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleConfigMapper;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.article.service.ApArticleService;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.article.dtos.ApArticleDto;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.utils.common.BeanHelper;
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

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

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

    /**
     * 保存文章（新增或修改）
     * @param dto
     * @return article id
     */
    @Override
    public Long saveApArticle(ApArticleDto dto) {
        ApArticle apArticle = BeanHelper.copyProperties(dto, ApArticle.class);
        // 判断新增或者修改
        if (dto.getId() == null) {
            // 新增 ap_article 表
            save(apArticle);

            // 新增 ap_article_config 表
            ApArticleConfig apArticleConfig = new ApArticleConfig();
            apArticleConfig.setArticleId(apArticle.getId());
            apArticleConfig.setIsForward(true);
            apArticleConfig.setIsComment(true);
            apArticleConfig.setIsDown(false);
            apArticleConfig.setIsDelete(false);
            apArticleConfigMapper.insert(apArticleConfig);

            // 新增 ap_article_content 表
            ApArticleContent apArticleContent = new ApArticleContent();
            apArticleContent.setArticleId(apArticle.getId());
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.insert(apArticleContent);

        } else {
            // 修改 ap_article 表
            updateById(apArticle);

            // 修改 ap_article_content 表
            QueryWrapper<ApArticleContent> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("article_id", apArticle.getId());
            // 先查再改
            ApArticleContent apArticleContent = apArticleContentMapper.selectOne(queryWrapper);
            apArticleContent.setContent(dto.getContent());
            apArticleContentMapper.updateById(apArticleContent);
        }
        return apArticle.getId();
    }
}

