/**
 * @projectName heima-leadnews
 * @package com.heima.common.exception
 * @className com.heima.common.exception.LeadNewsException
 */
package com.heima.common.exception;

/**
 * LeadNewsException
 * @description
 * @author SongJian
 * @date 2023/3/15 13:07
 * @version
 */

import com.heima.common.dtos.AppHttpCodeEnum;
import lombok.Getter;

/**
 * 自定义业务异常类
 * 需要继承异常类，可以继承 RuntimeException ，也可以继承 Exception
 */
@Getter
public class LeadNewsException extends RuntimeException {

    /**
     * 状态码 200 401,400 等
     */
    private Integer status;

    /**
     * 自定义异常类构造器
     * @param status 异常状态码
     * @param message 异常信息
     */
    public LeadNewsException(AppHttpCodeEnum appHttpCodeEnum){
        super(appHttpCodeEnum.getErrorMessage());
        this.status = appHttpCodeEnum.getCode();
    }
}
