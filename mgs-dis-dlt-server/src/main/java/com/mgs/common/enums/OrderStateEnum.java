package com.mgs.common.enums;
/**
 * @Statetion : 订单状态枚举类
 * @author : Zhiping Sun
 * @date : 2018年1月10日下午4:58:51
 */
public enum OrderStateEnum {

	NEW("new", "新建"),
	PROCESSING("processing", "处理中"),
	CONFIRMED("confirmed", "已确认"),
	REFUSED("refused", "已拒绝"),
	APPLYING_CANCEL("applying_cancel", "申请取消中"),
	APPLYING_MODIFY("applying_modify", "申请修改中"),
	CANCELED("canceled", "已取消");

	public String code;

	public String state;

	OrderStateEnum(String code, String state) {
		this.code = code;
		this.state = state;
	}
	
	public static OrderStateEnum getEnumByCode(String code) {
		if (null == code || "".equals(code)) {
			return null;
		}
		for (OrderStateEnum se : OrderStateEnum.values()) {
			if (se.code.equals(code)) {
				return se;
			}
		}
		return null;
	}
	
	public static String getStateByCode(String code) {
		String des = "";
		if (null == code || "".equals(code)) {
			return null;
		}
		for (OrderStateEnum se : OrderStateEnum.values()) {
			if (se.code.equals(code)) {
				des = se.state;
				break;
			}
		}
		return des;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
