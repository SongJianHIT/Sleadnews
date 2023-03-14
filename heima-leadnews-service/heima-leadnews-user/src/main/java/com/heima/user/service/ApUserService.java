/**
 * @projectName heima-leadnews
 * @package com.heima.user.service
 * @className com.heima.user.service.ApUserService
 */
package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;

/**
 * ApUserService
 * @description
 * @author SongJian
 * @date 2023/3/14 16:12
 * @version
 */

/**
 * IService 提供了大量的单表操作接口
 */
public interface ApUserService extends IService<ApUser> {
    /**
     * 用户登入接口
     * @param loginDto
     * @return
     */
    ResponseResult login(LoginDto loginDto);
}
