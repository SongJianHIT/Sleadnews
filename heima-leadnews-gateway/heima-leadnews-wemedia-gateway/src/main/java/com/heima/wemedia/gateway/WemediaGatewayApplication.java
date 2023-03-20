/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia.gateway
 * @className com.heima.wemedia.gateway.WemediaGatewayApplication
 */
package com.heima.wemedia.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * WemediaGatewayApplication
 * @description
 * @author SongJian
 * @date 2023/3/20 10:48
 * @version
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WemediaGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(WemediaGatewayApplication.class, args);
    }
}

