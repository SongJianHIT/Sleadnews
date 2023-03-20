/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia.controller
 * @className com.heima.wemedia.controller.LoginController
 */
package com.heima.wemedia.controller;

import com.heima.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmLoginDto;
import com.heima.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginController
 * @description
 * @author SongJian
 * @date 2023/3/20 10:24
 * @version
 */
@RestController
public class LoginController {

    @Autowired
    private WmUserService wmUserService;

    /**
     * 自媒体登入
     * @param wmLoginDto
     * @return
     */
    @PostMapping("/login/in")
    public ResponseResult login(@RequestBody WmLoginDto wmLoginDto) {
        return wmUserService.login(wmLoginDto);
    }
}

