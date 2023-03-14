/**
 * @projectName heima-leadnews
 * @package com.heima.model.user.dtos
 * @className com.heima.model.user.dtos.LoginDto
 */
package com.heima.model.user.dtos;

import lombok.Data;

/**
 * LoginDto
 * @description 用户登入DTO
 * @author SongJian
 * @date 2023/3/14 17:25
 * @version
 */
@Data
public class LoginDto {
    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;
}

