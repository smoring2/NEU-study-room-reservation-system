package com.group2.nustudy.enums;

public enum PaymentTypeEnum {
    ALIPAY(1,"Paypal"),
    WEIXIN(2,"Credit Card" );

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer status ;
    private String comment ;


    PaymentTypeEnum(Integer status, String comment ){
        this.status = status;
        this.comment=comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
