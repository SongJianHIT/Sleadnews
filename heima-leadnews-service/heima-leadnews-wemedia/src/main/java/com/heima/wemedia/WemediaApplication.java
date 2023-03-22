/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia
 * @className com.heima.wemedia.WemediaApplication
 */
package com.heima.wemedia;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * WemediaApplication
 * @description 自媒体微服务
 * @author SongJian
 * @date 2023/3/20 10:01
 * @version
 */
@SpringBootApplication
@MapperScan("com.heima.wemedia.mapper")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.heima.article.feign")
public class WemediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WemediaApplication.class);
    }

    /**
     * Mybatis 分页配置
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor () {
        return new PaginationInterceptor();
    }
}

