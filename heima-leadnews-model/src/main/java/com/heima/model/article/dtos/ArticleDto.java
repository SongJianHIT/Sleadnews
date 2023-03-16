/**
 * @projectName heima-leadnews
 * @package com.heima.model.article.dtos
 * @className com.heima.model.article.dtos.ArticleDto
 */
package com.heima.model.article.dtos;

import lombok.Data;

import java.util.Date;

/**
 * ArticleDto
 * @description
 * @author SongJian
 * @date 2023/3/16 08:29
 * @version
 */
@Data
public class ArticleDto {

    /**
     * 最大时间
     */
    Date maxBehotTime;

    /**
     * 最小时间
     */
    Date minBehotTime;

    /**
     * 分页size
     */
    Integer size;

    /**
     * 频道I	D
     */
    String tag;
}
