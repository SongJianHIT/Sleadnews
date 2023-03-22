/**
 * @projectName heima-leadnews
 * @package com.heima.wemedia.mapper
 * @className com.heima.wemedia.mapper.WmNewsMaterialMapper
 */
package com.heima.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.wemedia.pojos.WmNewsMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * WmNewsMaterialMapper
 * @description
 * @author SongJian
 * @date 2023/3/22 13:19
 * @version
 */
public interface WmNewsMaterialMapper extends BaseMapper<WmNewsMaterial> {
    /**
     * 内容素材与文章id绑定
     * @param materialIds
     * @param id
     * @param i
     */
    void saveNewsMaterial(@Param("materialIds") List<Integer> materialIds,
                          @Param("newsId") Integer newsId,
                          @Param("type") int type);

}
