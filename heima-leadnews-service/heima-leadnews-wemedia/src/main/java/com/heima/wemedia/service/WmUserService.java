/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia.service
 * @className com.heima.wemedia.service.WmUserService
 */
package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmLoginDto;
import com.heima.model.wemedia.pojos.WmUser;

/**
 * WmUserService
 * @description
 * @author SongJian
 * @date 2023/3/20 10:20
 * @version
 */
public interface WmUserService extends IService<WmUser> {
    /**
     * 自媒体登入
     * @param wmLoginDto
     * @return
     */
    ResponseResult login(WmLoginDto wmLoginDto);
}

