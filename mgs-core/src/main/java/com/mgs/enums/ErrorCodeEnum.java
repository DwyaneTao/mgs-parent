package com.mgs.enums;

/**
 * 系统错误编码枚举
 */
public enum ErrorCodeEnum {

    /***********公共业务异常类错误代码 以0开头***********/
    INVALID_INPUTPARAM("001","INVALID_INPUTPARAM","无效的入参对象"),


    /***********系统异常类错误代码 以9开头***********/
    SYSTEM_EXCEPTION("900","SYSTEM_EXCEPTION","系统异常"),
    OUTER_IF_EXCEPTION("901","OUTER_IF_EXCEPTION","外部接口调用异常"),
    UNKNOWN_EXCEPTION("902","UNKNOWN_EXCEPTION","未知异常"),
    CONNECT_TIME_OUT("903","CONNECT_TIME_OUT","连接超时"),
    READ_TIME_OUT("904","READ_TIME_OUT","访问超时"),
    INSERT_DATA_EXCEPTION("905","INSERT_DATA_EXCEPTION","插入数据异常"),
    NOT_LOGIN("906","NOT_LOGIN","该用户未登陆"),
    PERMISSION_DENIED("907", "PERMISSION_DENIED", "该用户没有权限"),
    FAIL_SIGNATURE("908", "FAIL_SIGNATURE", "token解析失败"),
    EXPIRED_SIGNATURE("909","EXPIRED_SIGNATURE","token过期"),
    EXCEPTION_SIGNATURE("910","EXCEPTION_SIGNATURE","token解析异常"),


    /***********订单异常 以2开头***********/
    ORDER_NOT_EXISTS("201","ORDER_NOT_EXISTS","订单不存在"),
    CONFIRM_OTA_ORDER_ERROR("202","CONFIRM_OTA_ORDER_ERROR","确认OTA订单失败"),

    /***用户异常以及权限 以1开头***/
     NOT_ADMIN("100","NOT_ADMIN","该账号不为管理员账号，不能重置"),
     REPEAT_ACCOUNT("101","REPEAT_ACCOUNT","该账号已存在，请换个账号重试"),
     NO_ADMIN_AUTH("102","NO_ADMIN_AUTH","不能增加总管理员权限"),
     ADMIN_OR_NOT_EXIST_USER("103","ADMIN_OR_NOT_EXIST_USER","该用户为总管理员或不存在该用户"),
     THIS_USER_CANNOT_MODIFY("104","THIS_USER_CANNOT_MODIFY","登录用户不能被修改"),
     NOT_EXIST_USER("105","NOT_EXIST_USER", "不存在该用户"),
     PASSWORD_ERROR("106", "PASSWORD_ERROR", "账号或密码错误"),
     USER_IS_DISABLED("107", "USER_IS_DISABLED", "该账号已被禁用，无法登录"),


    /**** 酒店信息异常 以3开头****/
    EXIST_HOTEL("301", "EXIST_HOTEL", "已存在该名称的酒店"),
    NOT_EXIST_HOTEL("302", "NOT_EXIST_HOTEL", "不存在该酒店请重新操作"),
    NOT_EXIST_ROOM("303", "NOT_EXIST_ROOM", "不存在该房型或者不存在该酒店请重新操作"),
    EXIST_ROOM("304", "EXIST_ROOM", "已存在该名称的房型或者该酒店不存在"),
    NOT_EXIST_PHOTO("305", "NOT_EXIST_PHOTO", "不存在该图片"),
    EXIST_PRODUCT("306", "EXIST_PRODUCT", "存在产品，不能删除"),
	
	 /***********产品异常 以4开头***********/
    PRODUCTONSALEWITHNODELETE("401","PRODUCTONSALE","产品处于销售状态，不能删除"),

    /***********OTA对接异常 以5开头***********/
    INTERFACENOTEXISTS("501","INTERFACENOTEXISTS","接口不存在，调用失败");

    public String errorNo;
    public String errorCode;//业务场景编号
    public String errorDesc;//业务场景描述

    private ErrorCodeEnum(String errorNo, String errorCode, String errorDesc)
    {
        this.errorNo = errorNo;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public static String getKeyByValue(String errorCode) {
        String key = "000";
        for(ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()) {
            if(errorCodeEnum.errorCode.equals(errorCode)) {
                key = errorCodeEnum.errorNo;
                break;
            }
        }
        return key;
    }

    public static String getDescByValue(String errorCode) {
        String desc = "";
        for(ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()) {
            if(errorCodeEnum.errorCode.equals(errorCode)) {
                desc = errorCodeEnum.errorDesc;
                break;
            }
        }
        return desc;
    }

    public static String getErrorCodeByKey(String key) {
        String errorCode = "";
        for(ErrorCodeEnum errorCodeEnum : ErrorCodeEnum.values()) {
            if(errorCodeEnum.errorNo.equals(key)) {
                errorCode = errorCodeEnum.errorCode;
                break;
            }
        }
        return errorCode;
    }
}
