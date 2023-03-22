/**
 * @projectName heima-leadnews
 * @package com.heima.article.feign
 * @className com.heima.article.feign.ApArticleFeign
 */
package com.heima.article.feign;

import com.heima.model.article.dtos.ApArticleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ApArticleFeign
 * @description
 * @author SongJian
 * @date 2023/3/22 18:56
 * @version
 */

/**
 * Feign接口注意实现：
 *   1）所有形参都必须添加注解！！！(不能省略注解）
 *   2）返回对象（List）必须声明泛型类型
 */

@FeignClient(name = "leadnews-article", path = "/api/v1/article")
public interface ApArticleFeign {

    /**
     * 保存 app 文章（新增或修改）
     * @param dto
     * @return
     */
    @PostMapping("/save")
    public Long save(@RequestBody ApArticleDto dto);
}

