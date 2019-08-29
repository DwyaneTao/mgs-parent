package com.mgs.enums;

/**
 * @Auther: Owen
 * @Date: 2019/6/29 16:42
 * @Description:
 */
public enum BedTypesEnum {
    DACHUANG(0,"大床"),
    SHUANGCHUANG(1,"双床"),
    DANCHUANG(2,"单床"),
    SANCHUANG(3,"三床"),
    SICHUANG(4,"四床"),
    SAHNGXIAPU(5,"上下铺"),
    TONGPU(6,"通铺"),
    KANG(7,"炕"),
    SHUICHUANG(8,"水床"),
    YUANCHUANG(9,"圆床"),
    PINCHUANG(10,"拼床"),
    TATAMI(11,"榻榻米");

    public int key;
    public String value;

    private BedTypesEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static int getKeyByValue(String value) {
        int key = 99;
        for (BedTypesEnum bedTypeEnum : BedTypesEnum.values()) {
            if (bedTypeEnum.value == value) {
                key = bedTypeEnum.key;
                break;
            }
        }
        return key;
    }

    public static String getValueByKey(int key) {
        String value = null;
        for (BedTypesEnum bedTypeEnum : BedTypesEnum.values()) {
            if (bedTypeEnum.key == key) {
                value = bedTypeEnum.value;
                break;
            }
        }
        return value;
    }

    public static BedTypesEnum getEnumByKey(int key) {
        BedTypesEnum bedTypesEnum = null;
        for (BedTypesEnum bedTypesEnum1 : BedTypesEnum.values()) {
            if (bedTypesEnum1.key == key) {
                bedTypesEnum = bedTypesEnum1;
                break;
            }
        }
        return bedTypesEnum;
    }
}
