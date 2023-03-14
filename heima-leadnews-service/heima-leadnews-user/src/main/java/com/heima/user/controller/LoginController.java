/**
 * @projectName heima-leadnews
 * @package com.heima.user.controller
 * @className com.heima.user.controller.LoginController
 */
package com.heima.user.controller;

import com.heima.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;

import com.heima.user.service.ApUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginController
 * @description
 * @author SongJian
 * @date 2023/3/14 17:22
 * @version
 */
@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private ApUserService apUserService;

    /**
     * 用户登入接口
     * @return
     */
    @PostMapping("/login_auth")
    public ResponseResult login(@RequestBody LoginDto loginDto) {

        return apUserService.login(loginDto);

    }
}

