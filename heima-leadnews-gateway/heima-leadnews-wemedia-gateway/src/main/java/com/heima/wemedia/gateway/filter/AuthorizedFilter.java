/**
 * @projectName heima-leadnews
 * @package com.heima.app.gateway.filter
 * @className com.heima.app.gateway.filter.AuthorizedFilter
 */
package com.heima.wemedia.gateway.filter;

import com.heima.model.user.pojos.ApUser;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.JwtUtils;
import com.heima.utils.common.Payload;
import com.heima.utils.common.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.PublicKey;

/**
 * AuthorizedFilter
 * @description 网关鉴权过滤器
 *              需要实现全局过滤器 GlobalFilter 的接口
 * @author SongJian
 * @date 2023/3/15 16:56
 * @version
 */
@Component
// 优先级定义，数值越大，优先级越低
@Order(0)
@Slf4j
public class AuthorizedFilter implements GlobalFilter {

    @Value("${leadnews.jwt.publicKeyPath}")
    private String publicKeyPath;

    /**
     * 自定义网关鉴权过滤器
     * @param exchange 服务网络交换器 ，存放着重要的 请求-响应属性、请求实例 和 响应实例 等等
     * @param chain 过滤器链
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求和响应
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //获取请求路径
        //  /user/api/v1/login/login_auth
        String uri = request.getURI().getPath();
        //判断该请求是否为登录请求
        if(uri.contains("/login")){
            //直接放行
            return chain.filter(exchange);
        }

        //获取token请求头
        String token = request.getHeaders().getFirst("token");
        log.debug(token);
        if(StringUtils.isEmpty(token)){
            //为空代表不合法，返回401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //终止请求
            return response.setComplete();
        }

        //获取公钥
        try {
            PublicKey publicKey = RsaUtils.getPublicKey(publicKeyPath);

            //验证token是否合法
            Payload<WmUser> payload = JwtUtils.getInfoFromToken(token, publicKey, WmUser.class);

            //取出登录用户ID
            WmUser user = payload.getInfo();
            log.debug(user.getName());
            //存入请求头
            request.mutate().header("userId",user.getId().toString());

            //放行
            return chain.filter(exchange);
        } catch (Exception e) {
            //为空代表不合法，返回401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //终止请求
            return response.setComplete();
        }
    }
}

