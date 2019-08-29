package com.mgs.enums;

public enum RemarkEnum {


    CompanyToAgent(0, "运营商给客户","客户"),
    AgentToCompany(1, "客户给运营商","运营商"),

    CompanyToSupplier(2, "运营商给供应商",""),
    SupplierToCompany(3, "供应商给运营商","运营商"),

    Internal(4, "内部备注","内部备注");


    public Integer key;
    public String desc;
    public String remark;

    RemarkEnum(Integer key, String desc,String remark) {
        this.key = key;
        this.desc = desc;
        this.remark=remark;
    }

    public static String getremark(int key){
        for(RemarkEnum RemarkEnum: RemarkEnum.values()){
            if(key == RemarkEnum.key){
                return RemarkEnum.remark;
            }
        }
        return null;
    }



}
