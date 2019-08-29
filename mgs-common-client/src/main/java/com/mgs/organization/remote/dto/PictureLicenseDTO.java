package com.mgs.organization.remote.dto;

import lombok.Data;

/**
 * @author py
 * @date 2019/6/28 18:01
 **/
@Data
public class PictureLicenseDTO {
    /**
     * 图片Id
     */
    private  Integer pictureId;
    /**
     * 图片URL
     */
    private  String pictureUrl;
    /**
     * 图片类别（1-企业营业执照，2-企业LOGO）
     */
    private  Integer pictureType;
}
