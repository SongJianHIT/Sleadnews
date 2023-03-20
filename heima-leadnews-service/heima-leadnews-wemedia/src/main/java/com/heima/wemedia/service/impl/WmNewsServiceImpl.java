/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia.service.impl
 * @className com.heima.wemedia.service.impl.WmNewsServiceImpl
 */
package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.dtos.AppHttpCodeEnum;
import com.heima.common.dtos.PageResponseResult;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.ThreadLocalUtils;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.service.WmNewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * WmNewsServiceImpl
 * @description
 * @author SongJian
 * @date 2023/3/20 19:40
 * @version
 */
@Service
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    /**
     * 条件、分页查询自媒体文章列表
     * @param dto
     * @return
     */
    @Override
    public PageResponseResult findList(WmNewsPageReqDto dto) {
        //参数检测
        dto.checkParam();

        //获取当前登录用户
        WmUser wmUser = (WmUser) ThreadLocalUtils.get();
        if(wmUser==null){
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        IPage<WmNews> iPage = new Page<>(dto.getPage(),dto.getSize());

        QueryWrapper<WmNews> queryWrapper = new QueryWrapper<>();
        //判断用户
        queryWrapper.eq("user_id",wmUser.getId());

        //状态
        if(dto.getStatus()!=null){
            queryWrapper.eq("status",dto.getStatus());
        }

        //关键词
        if(StringUtils.isNotEmpty(dto.getKeyword())){
            queryWrapper.like("title",dto.getKeyword());
        }

        //频道
        if(dto.getChannelId()!=null){
            queryWrapper.eq("channel_id",dto.getChannelId());
        }

        //发布时间
        if(dto.getBeginPubDate()!=null && dto.getEndPubDate()!=null){
            queryWrapper.between("publish_time",dto.getBeginPubDate(),dto.getEndPubDate());
        }

        //排序
        queryWrapper.orderByDesc("created_time");

        iPage = page(iPage,queryWrapper);

        //封装分页数据
        PageResponseResult pageResponseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)iPage.getTotal());
        pageResponseResult.setData(iPage.getRecords());
        pageResponseResult.setCode(200);
        pageResponseResult.setErrorMessage("查询成功");
        return pageResponseResult;
    }
}

