package com.can.aop;


/**
 * @className: OperateLogDo
 * @description: OperateLogDo
 * @author: zhengcan
 * @date: 2023/4/2
 **/



public class OperateLogDo {

    private Long orderId;

    private String desc;

    private String record;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
