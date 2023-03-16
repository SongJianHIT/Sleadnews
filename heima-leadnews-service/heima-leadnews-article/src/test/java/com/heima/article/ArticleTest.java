/**
 * @projectName heima-leadnews
 * @package com.heima.article
 * @className com.heima.article.ArticleTest
 */
package com.heima.article;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.common.minio.MinIOFileStorageService;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.utils.common.JsonUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ArticleTest
 * @description
 * @author SongJian
 * @date 2023/3/16 13:10
 * @version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleTest {

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Autowired
    private Configuration configuration;

    @Autowired
    private MinIOFileStorageService storageService;

    @Autowired
    private ApArticleMapper apArticleMapper;

    /**
     * 为文章生成静态页面
     */
    @Test
    public void testCreateStaticPage() throws Exception {
        // 读取文章数据
        Long id = 1383827787629252610L;
        ApArticleContent articleContent = apArticleContentMapper.selectOne(
                new QueryWrapper<ApArticleContent>().eq("article_id", id)
        );
        if (articleContent != null && articleContent.getContent() != null) {
            // 读取文章详情页面模版
            Template template = configuration.getTemplate("article.ftl");
            Map<String, Object> data = new HashMap<>();
            // 数据转换，
            List list = JsonUtils.toList(articleContent.getContent(), Map.class);
            data.put("content", list);

            // 生成静态页面
            StringWriter writer = new StringWriter();
            // TODO 是否需要关闭流？
            template.process(data, writer);

            // 把文件上传至MinIO
            String fileName = id + ".html";
            InputStream inputStream = new ByteArrayInputStream(writer.toString().getBytes());
            String url = storageService.uploadHtmlFile(null, fileName, inputStream);

            // 把静态页面地址更新到文章表中
            ApArticle apArticle = new ApArticle();
            apArticle.setId(id);
            apArticle.setStaticUrl(url);
            apArticleMapper.updateById(apArticle);
        }

    }
}

