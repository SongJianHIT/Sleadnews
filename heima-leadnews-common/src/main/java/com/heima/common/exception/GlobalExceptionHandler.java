/**
 * @projectName heima-leadnews
 * @package com.heima.common.exception
 * @className com.heima.common.exception.GlobalExceptionHandler
 */
package com.heima.common.exception;

import com.heima.common.dtos.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler
 * @description 全局异常拦截器
 * @author SongJian
 * @date 2023/3/15 13:28
 * @version
 *
 * @RestControllerAdvice 底层是 AOP 切面，包含了许多异常通知
 * https://blog.csdn.net/user2025/article/details/105458842
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常（自定义异常处理器）
     */
    @ExceptionHandler(value = LeadNewsException.class)
    public ResponseResult handleLeadNewsException(LeadNewsException e){
        // 将异常信息处理成用户可接收的格式
        return ResponseResult.errorResult(e.getStatus(), e.getMessage());
    }

    /**
     * 处理系统异常（自定义异常处理器）
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseResult handleException(Exception e){
        return ResponseResult.errorResult(500, "系统异常："+e.getMessage());
    }

}

