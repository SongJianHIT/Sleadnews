/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia.service
 * @className com.heima.wemedia.service.WmMaterialService
 */
package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.PageResponseResult;
import com.heima.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

/**
 * WmMaterialService
 * @description
 * @author SongJian
 * @date 2023/3/20 14:03
 * @version
 */
public interface WmMaterialService extends IService<WmMaterial> {
    /**
     * 上传素材图片
     * @param multipartFile
     * @return
     */
    ResponseResult uploadPicture(MultipartFile multipartFile);

    /**
     * 列表查询，根据条件、排序、进行分页查询
     * @param wmMaterialDto
     * @return
     */
    PageResponseResult findList(WmMaterialDto wmMaterialDto);
}
