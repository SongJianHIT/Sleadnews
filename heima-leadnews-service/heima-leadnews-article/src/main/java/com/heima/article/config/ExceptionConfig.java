/**
 * @projectName heima-leadnews
 * @package com.heima.user.config
 * @className com.heima.user.config.ExceptionConfig
 */
package com.heima.article.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * ExceptionConfig
 * @description 配置全局异常拦截器
 *              由于 user 服务的启动类，只能扫描到 com.heima.user 同级和子包里的类，
 *              因此无法直接扫到 common 服务的全局异常拦截器，
 *              需要在这里配置！
 * @author SongJian
 * @date 2023/3/15 13:34
 * @version
 */
@Configuration
@ComponentScan("com.heima.common.exception")
public class ExceptionConfig {
}

