package com.mgs.organization.remote.dto;

import com.mgs.common.BaseDTO;
import lombok.Data;

/**
 * @author py
 * @date 2019/6/27 22:02
 **/
@Data
public class PictureAddDTO  extends BaseDTO {
    /**
     * 图片Id
     */
    private Integer pictureId;
    /**
     * 图片名称
     */
    private  String pictureName;
    /**
     * 图片URL
     */
    private  String pictureUrl;
    /**
     * 图片类别（1-企业营业执照，2-企业LOGO）
     */
    private  Integer pictureType;
    /**
     * 企业Id
     */
    private  Integer companyId;
}
