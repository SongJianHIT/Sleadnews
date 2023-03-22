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
import com.heima.common.dtos.ResponseResult;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmNewsMaterial;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.BeanHelper;
import com.heima.utils.common.JsonUtils;
import com.heima.utils.common.ThreadLocalUtils;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.service.WmNewsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * WmNewsServiceImpl
 * @description
 * @author SongJian
 * @date 2023/3/20 19:40
 * @version
 */
@Service
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    @Autowired
    private WmMaterialMapper wmMaterialMapper;

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

    /**
     * 发布文章，也可提交草稿、修改文章
     * @param dto
     * @return
     */
    @Override
    public ResponseResult submit(WmNewsDto dto) {
        // 获取登入用户信息
        WmUser user = (WmUser) ThreadLocalUtils.get();
        if (user == null) {
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }
        // 准备实体对象
        WmNews wmNews = BeanHelper.copyProperties(dto, WmNews.class);
        // 设置作者id
        wmNews.setUserId(user.getId());

        // 提取文章内容中的所有图片
        List<String> contentImages = getImagesFromContent(wmNews);

        // 处理自动封面情况，是否为自动封面
        if (dto.getType() == -1) {
            // 自动封面，根据内容中的图片进行选择
            int size = contentImages.size();
            if (size == 0) {
                // 无图
                wmNews.setType((short) 0);
                wmNews.setImages(null);
            }
            if (size >= 1 && size <= 2) {
                // 单图
                wmNews.setType((short) 1);
                // 截取第一张作为封面
                wmNews.setImages(contentImages.get(0));
            }
            if (size >= 3) {
                // 三图
                wmNews.setType((short) 3);
                List<String> subList = contentImages.subList(0, 3);
                String collect = subList.stream().collect(Collectors.joining(","));
                wmNews.setImages(collect);
            }
        } else {
            // 非自动封面
            // List -> String
            List<String> images = dto.getImages();
            if (CollectionUtils.isNotEmpty(images)) {
                // joining() 使用指定分隔符拼接字符串
                String coverImages = images.stream().collect(Collectors.joining(","));
                wmNews.setImages(coverImages);
            }
        }

        // 判断是新增、修改
        if (dto.getId() == null) {
            // 新增、直接添加
            wmNews.setCreatedTime(new Date());
            save(wmNews);
        } else {
            // 修改，修改文章，并删除当前文章和素材的所有关系
            updateById(wmNews);
            wmNewsMaterialMapper.delete(
                new QueryWrapper<WmNewsMaterial>().eq("news_id", wmNews.getId())
            );
        }

        // 绑定文章与素材关系
        // 内容素材与文章绑定(type = 0)
        if (CollectionUtils.isNotEmpty(contentImages)) {
            // 把 URL 转化为 id
            List<Integer> materialIds = getMaterialIdFromUrl(contentImages);
            // 保存
            wmNewsMaterialMapper.saveNewsMaterial(materialIds, wmNews.getId(), 0);
        }

        // 封面素材与文章绑定(type = 1)
        if (StringUtils.isNotEmpty(wmNews.getImages())) {
            List<String> images = Arrays.asList(wmNews.getImages().split(","));
            // 把 URL 转化为 id
            List<Integer> materialIds = getMaterialIdFromUrl(images);
            // 保存
            wmNewsMaterialMapper.saveNewsMaterial(materialIds, wmNews.getId(), 1);
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 根据 url 查询素材 id
     * @param urls
     * @return
     */
    private List<Integer> getMaterialIdFromUrl(List<String> urls) {
        QueryWrapper<WmMaterial> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("url", urls);
        List<WmMaterial> wmMaterials = wmMaterialMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(wmMaterials)) {
            return wmMaterials.stream().map(item -> {
                return item.getId();
            }).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 获取文章内容中的所有图片
     * @param wmNews
     * @return
     */
    private List<String> getImagesFromContent(WmNews wmNews) {
        List<String> images = new ArrayList<>();
        if (StringUtils.isNotEmpty(wmNews.getContent())) {
            List<Map> maps = JsonUtils.toList(wmNews.getContent(), Map.class);
            for (Map map : maps) {
                if (map.get("type").equals("image")) {
                    images.add((String) map.get("value"));
                }
            }
        }
        return images;
    }
}

