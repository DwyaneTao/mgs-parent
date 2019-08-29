package com.mgs.organization.domain;

import com.mgs.common.BasePO;
import lombok.Data;

import javax.persistence.*;

/**
 * @author py
 * @date 2019/6/28 11:34
 **/
@Data
@Table(name = "t_org_picture")
public class PicturePO  extends BasePO {
    /**
     * 图片Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pictureId;
    /**
     * 图片名称
     */
    @Column(name="picture_name")
    private String pictureName;
    /**
     * 图片URL地址
     */
    @Column(name="picture_url")
    private  String pictureUrl;
    /**
     *图片类型（1-企业营业执照，2-企业LOGO）
     */
    @Column(name="picture_type")
    private  Integer pictureType;
    /**
     * 企业编码
     */
    private  String companyCode;
}
