package com.can.aop;

/**
 * @className: UpdateOrderConvert
 * @description: UpdateOrderConvert
 * @author: zhengcan
 * @date: 2023/4/2
 **/
public class UpdateOrderConvert implements Convert<UpdateOrder>{

    @Override
    public OperateLogDo convert(UpdateOrder updateOrder) {
        OperateLogDo operateLogDo = new OperateLogDo();
        operateLogDo.setOrderId(updateOrder.getOrderId());
        return operateLogDo;
    }
}
