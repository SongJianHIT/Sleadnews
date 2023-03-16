/**
 * @projectName heima-leadnews
 * @package com.heima.common.minio
 * @className com.heima.common.minio.MinIOProperties
 */
package com.heima.common.minio;

/**
 * MinIOProperties
 * @description MInIO属性类
 * @author SongJian
 * @date 2023/3/16 11:32
 * @version
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 定义MinIO的参数属性类
 */
@Data
@ConfigurationProperties(prefix = "minio") // 加载yml文件中的前缀
public class MinIOConfigProperties {

    /**
     * minio账号，密码
     */
    private String accessKey;
    private String secretKey;

    /**
     * url
     */
    private String endpoint;

    /**
     * 指定桶
     */
    private String bucket;

    /**
     * 文件读取路径
     */
    private String readPath;
}
