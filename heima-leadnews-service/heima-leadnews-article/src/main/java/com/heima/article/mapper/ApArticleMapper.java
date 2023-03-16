/**
 * @projectName heima-leadnews
 * @package com.heima.article.mapper
 * @className com.heima.article.mapper.ApArticleMapper
 */
package com.heima.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.article.pojos.ApArticle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ApArticleMapper
 * @description 文章查询mapper
 * @author SongJian
 * @date 2023/3/16 08:16
 * @version
 */
public interface ApArticleMapper extends BaseMapper<ApArticle> {
    /**
     * 根据用户行为，条件、分页查询文章列表
     * @param dto
     * @param behaveType
     * @return
     */
    List<ApArticle> loadApArticle(@Param("dto") ArticleDto dto, @Param("behaveType") int behaveType);
}
