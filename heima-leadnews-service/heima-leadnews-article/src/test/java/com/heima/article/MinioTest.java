/**
 * @projectName heima-leadnews
 * @package com.heima.article
 * @className com.heima.article.MinioTest
 */
package com.heima.article;

import com.heima.common.minio.MinIOFileStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * MinioTest
 * @description
 * @author SongJian
 * @date 2023/3/16 11:50
 * @version
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleApplication.class)
public class MinioTest {

    @Autowired
    private MinIOFileStorageService storageService;
    @Test
    public void TestUpload() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("/Users/mac/Desktop/News/heima-leadnews/heima-leadnews-service/heima-leadnews-article/src/test/java/com/heima/article/111.jpg");
        String s = storageService.uploadImgFile(null, "111.jpg", inputStream);
        System.out.println(s);
    }
}

