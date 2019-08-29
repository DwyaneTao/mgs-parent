package com.mgs.organization.remote.dto;

import lombok.Data;


@Data
public class AreaDataDTO   {

    /**
     * id
     */
    private  Integer dataId;


    /**
     * 名称
     */
    private  String dataName;

    /**
     * 编码
     */
    private  String dataCode;

    /**
     * 上级ID
     */
    private  Integer superId;


    /**
     * 第一个字母
     */
    private  String firstLetter;


    /**
     * 级别
     */
    private  Integer level;

    /**
     * 拼音
     */
    private  String pinyin;

    /**
     * 有效性
     */
    private  Integer active;


}
