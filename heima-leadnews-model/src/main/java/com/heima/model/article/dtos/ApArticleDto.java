/**
 * @projectName heima-leadnews
 * @package com.heima.model.article.dtos
 * @className com.heima.model.article.dtos.ApArticleDto
 */
package com.heima.model.article.dtos;

import com.heima.model.article.pojos.ApArticle;
import lombok.Data;

/**
 * ApArticleDto
 * @description 在自媒体端传递到App端的数据对象
 * @author SongJian
 * @date 2023/3/22 18:33
 * @version
 */
@Data
public class ApArticleDto extends ApArticle {
    /**
     * 文章内容
     */
    private String content;
}
