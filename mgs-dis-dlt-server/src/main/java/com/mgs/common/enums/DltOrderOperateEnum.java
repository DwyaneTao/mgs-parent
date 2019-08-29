package com.mgs.common.enums;

import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *   2018/4/26.
 */
@Getter
public enum DltOrderOperateEnum {

    CTRIP_ACCEPT("ctrip", "0", "ctrip_accept", "接受"),
    CTRIP_REFUSE("ctrip", "1", "ctrip_refuse", "拒绝"),
    CTRIP_CHANGE_CONFIRM_NO("ctrip", "2", "ctrip_change_confirm_no", "更改确认号"),
    CTRIP_ACCEPT_CANCEL("ctrip", "11", "ctrip_accept_cancel", "接受取消"),
    CTRIP_REFUSE_CANCEL("ctrip", "12", "ctrip_refuse_cancel", "拒绝取消"),
    QUNAR_HAVE_ROOM_AND_ACCEPT("qunar", "14", "qunar_have_room_and_accept", "有房并接受"),
    QUNAR_NO_ROOM("qunar", "15", "qunar_no_room", "无房"),
    QUNAR_APPLY_UNSUBSCRIBE("qunar", "16", "qunar_apply_unsubscribe", "申请退订"),
    QUNAR_ACCEPT_UNSUBSCRIBE("qunar", "17", "qunar_accept_unsubscribe", "同意退订"),
    QUNAR_REFUSE_UNSUBSCRIBE("qunar", "18", "qunar_refuse_unsubscribe", "拒绝退订"), ;

    public String channel;
    public String operateId;
    public String code;
    public String desc;

    DltOrderOperateEnum(String channel, String operateId, String code, String desc){
        this.channel = channel;
        this.operateId = operateId;
        this.code = code;
        this.desc = desc;
    }

    public static List<DltOrderOperateEnum> getEnumListByChannel(String channel) {

        List<DltOrderOperateEnum> list = new ArrayList<>();
        if (StringUtils.isEmpty(channel)) {
            return list;
        }

        for (DltOrderOperateEnum ooe : DltOrderOperateEnum.values()) {
            if (ooe.channel.equals(channel)) {
                list.add(ooe);
            }
        }
        return list;
    }

    public static String getOperateIdByCode(String code) {

        if (StringUtils.isEmpty(code)) {
            return null;
        }

        for (DltOrderOperateEnum ooe : DltOrderOperateEnum.values()) {
            if (ooe.code.equals(code)) {
                return ooe.operateId;
            }
        }
        return null;
    }

    public static String getDescByCode(String code) {

        if (StringUtils.isEmpty(code)) {
            return null;
        }

        for (DltOrderOperateEnum ooe : DltOrderOperateEnum.values()) {
            if (ooe.code.equals(code)) {
                return ooe.desc;
            }
        }
        return null;
    }

}
