/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia.controller
 * @className com.heima.wemedia.controller.WmChannelController
 */
package com.heima.wemedia.controller;

import com.heima.common.dtos.ResponseResult;
import com.heima.wemedia.service.WmChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WmChannelController
 * @description
 * @author SongJian
 * @date 2023/3/20 19:24
 * @version
 */
@RestController
@RequestMapping("/api/v1/channel")
public class WmChannelController {

    @Autowired
    private WmChannelService wmChannelService;

    /**
     * 查询所有频道
     * @return
     */
    @GetMapping("/channels")
    public ResponseResult channels() {
        return ResponseResult.okResult(wmChannelService.list());
    }
}

