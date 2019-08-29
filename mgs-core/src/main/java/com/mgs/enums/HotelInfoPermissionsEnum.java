package com.mgs.enums;

/**
 * @author py
 * @date 2019/7/8 19:36
 **/
public enum HotelInfoPermissionsEnum {
    ONE(0, "支持基本信息的查询（不能新增编辑删除）"),
    Two(1, "支持酒店和房型的新增编辑（不能删除），及图片的新增删除"),
    THREE(2, "支持酒店和房型的编辑（不能新增删除），及图片的新增删除"),
    FOUR(3, "支持所有基本信息的新增编辑删除  适用于独立部署的运营商");
    public Integer key;
    public String desc;

    HotelInfoPermissionsEnum(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }

}
