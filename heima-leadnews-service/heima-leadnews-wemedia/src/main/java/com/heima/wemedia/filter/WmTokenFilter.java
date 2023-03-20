/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia.filter
 * @className com.heima.wemedia.filter.WmTokenFilter
 */
package com.heima.wemedia.filter;

import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * WmTokenFilter
 * @description 自媒体用户信息获取
 * @author SongJian
 * @date 2023/3/20 13:42
 * @version
 */
@Component
@WebFilter(filterName = "wmTokenFilter",urlPatterns = "/*")
@Slf4j
public class WmTokenFilter extends GenericFilter{

    /**
     * 过滤器逻辑
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1. 获取请求头中的user_id
        String userId = request.getHeader("userId");
        if (StringUtils.isNotEmpty(userId)) {
            // 包一层对象
            WmUser user = new WmUser();
            user.setId(Integer.valueOf(userId));
            // 放入 ThreadLocal
            ThreadLocalUtils.set(user);
        }

        // 2. 放行请求，执行 controller 的方法
        try {
            filterChain.doFilter(request, response);
        } finally {
            // 3. 用完数据后，移除 ThreadLocal
            // 避免内存占用过多，导致 OOM
            ThreadLocalUtils.remove();
        }
    }
}

