/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia.service.impl
 * @className com.heima.wemedia.service.impl.WmChannellServiceImpl
 */
package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.service.WmChannelService;
import org.springframework.stereotype.Service;

/**
 * WmChannellServiceImpl
 * @description
 * @author SongJian
 * @date 2023/3/20 19:22
 * @version
 */
@Service
public class WmChannellServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {
}

