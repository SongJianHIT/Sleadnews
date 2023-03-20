/**
 * @projectName heima-leadnews
 * @package com.heima.model.wemedia.dtos
 * @className com.heima.model.wemedia.dtos.WmMaterialDto
 */
package com.heima.model.wemedia.dtos;

import com.heima.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * WmMaterialDto
 * @description
 * @author SongJian
 * @date 2023/3/20 18:47
 * @version
 */
@Data
public class WmMaterialDto extends PageRequestDto {

    /**
     * 1 收藏
     * 0 未收藏
     */
    private Short isCollection;
}

