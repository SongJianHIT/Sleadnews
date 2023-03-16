/**
 * @projectName heima-leadnews
 * @package com.heima.common.minio
 * @className com.heima.common.minio.MinIOConfiguration
 */
package com.heima.common.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinIOConfiguration
 * @description
 * @author SongJian
 * @date 2023/3/16 11:28
 * @version
 */
@Configuration
@EnableConfigurationProperties(MinIOConfigProperties.class)
public class MinIOConfiguration {

    @Autowired
    private MinIOConfigProperties minIOConfigProperties;

    /**
     * 单例client
     * @return
     */
    @Bean
    public MinioClient minioClient() {
        MinioClient client = MinioClient.builder()
                .endpoint(minIOConfigProperties.getEndpoint())
                .credentials(minIOConfigProperties.getAccessKey(), minIOConfigProperties.getSecretKey())
                .build();
        return client;
    }
}

