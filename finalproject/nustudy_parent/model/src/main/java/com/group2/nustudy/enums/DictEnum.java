package com.group2.nustudy.enums;

public enum DictEnum {


    CAMPUSTYPE("Campustype", "campus  Type"),
    CERTIFICATES_TYPE("CertificatesType", "Certificates Type"),
    ;

    private String dictCode;
    private String msg;

    DictEnum(String dictCode, String msg) {
        this.dictCode = dictCode;
        this.msg = msg;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
