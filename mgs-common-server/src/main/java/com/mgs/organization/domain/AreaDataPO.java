package com.mgs.organization.domain;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "t_baseinfo_areadata")
public class AreaDataPO {

    /**
     * id
     */
    @Id
    @Column(name = "date_id")
    private  String dateId;


    /**
     * 名称
     */
    @Column(name = "date_name")
    private  String dateName;

    /**
     * 编码
     */
    @Column(name = "date_code")
    private  String dateCode;

    /**
     * 上级ID
     */
    @Column(name = "super_id")
    private  String superId;


    /**
     * 第一个字母
     */
    @Column(name = "frist_name")
    private  String fristName;


    /**
     * 级别
     */
    private  String level;

    /**
     * 拼音
     */
    private  String pinyin;

    /**
     * 有效性
     */
    private  String active;

}
