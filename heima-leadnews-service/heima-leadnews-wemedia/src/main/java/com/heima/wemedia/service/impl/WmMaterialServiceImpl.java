/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia.service.impl
 * @className com.heima.wemedia.service.impl.WmMaterialServiceImpl
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
import com.heima.common.minio.MinIOFileStorageService;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.ThreadLocalUtils;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.service.WmMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * WmMaterialServiceImpl
 * @description
 * @author SongJian
 * @date 2023/3/20 14:03
 * @version
 */
@Slf4j
@Service
@Transactional
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {

    @Autowired
    private MinIOFileStorageService minIOFileStorageService;

    /**
     * 上传素材图片
     * @param multipartFile
     * @return
     */
    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {

        // 参数校验
        if (multipartFile == null) {
            throw new LeadNewsException(AppHttpCodeEnum.PARAM_INVALID);
        }

        // 从 ThreadLocal 中取出登入用户信息
        WmUser user = (WmUser) ThreadLocalUtils.get();
        if (user == null) {
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        try {
            // 把图片上传至 MinIO
            // 文件名
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String originalFilename = multipartFile.getOriginalFilename();
            // .jpg
            String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = uuid + extName;
            String url = minIOFileStorageService.uploadImgFile(null, fileName, multipartFile.getInputStream());

            // 写入 DB
            WmMaterial wmMaterial = new WmMaterial();
            wmMaterial.setUserId(user.getId());
            wmMaterial.setType((short) 0);
            wmMaterial.setIsCollection((short) 0);
            wmMaterial.setUrl(url);
            wmMaterial.setCreatedTime(new Date());
            save(wmMaterial);

            return ResponseResult.okResult(wmMaterial);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 列表查询，根据条件、排序、进行分页查询
     * @param wmMaterialDto
     * @return
     */
    @Override
    public PageResponseResult findList(WmMaterialDto wmMaterialDto) {
        // 校验
        wmMaterialDto.checkParam();

        // 1. 获取用户信息
        // 从 ThreadLocal 中取出登入用户信息
        WmUser user = (WmUser) ThreadLocalUtils.get();
        if (user == null) {
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        // 2. 封装条件
        IPage<WmMaterial> iPage = new Page<>(wmMaterialDto.getPage(), wmMaterialDto.getSize());
        QueryWrapper<WmMaterial> wmMaterialQueryWrapper = new QueryWrapper<>();
        wmMaterialQueryWrapper.eq("user_id", user.getId());
        // 收藏
        if (wmMaterialDto.getIsCollection() != null && wmMaterialDto.getIsCollection() == 1) {
            wmMaterialQueryWrapper.eq("is_collection", wmMaterialDto.getIsCollection());
        }
        // 按照创建时间倒叙
        wmMaterialQueryWrapper.orderByDesc("created_time");

        // 3. 执行查询
        IPage<WmMaterial> page = page(iPage, wmMaterialQueryWrapper);

        // 4. 封装结果
        PageResponseResult responseResult = new PageResponseResult(wmMaterialDto.getPage(), wmMaterialDto.getSize(), (int) iPage.getTotal());
        responseResult.setCode(200);
        responseResult.setErrorMessage("查询成功");
        responseResult.setData(iPage.getRecords());
        return responseResult;
    }
}

