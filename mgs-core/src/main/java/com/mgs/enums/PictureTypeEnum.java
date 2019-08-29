package com.mgs.enums;

/**
 * @author py
 * @date 2019/6/28 18:22
 **/
public enum  PictureTypeEnum {
    ONE(0, "企业营业执照照片"), TWO(1, "企业LOGO");
    public Integer key;
    public String desc;
    PictureTypeEnum(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
