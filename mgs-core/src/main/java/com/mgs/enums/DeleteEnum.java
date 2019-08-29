package com.mgs.enums;

/**
 * @author py
 * @date 2019/6/22 11:27
 * 删除数据修改状态枚举
 **/
public enum DeleteEnum {
    STATUS_EXIST(1, "存在"), STATUS_DELECT(0, "已删除");

    public Integer key;
    public String desc;

    DeleteEnum(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }
}
