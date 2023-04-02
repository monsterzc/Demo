package com.can.aop;

/**
 * @className: SaveOrderConvert
 * @description: SaveOrderConvert
 * @author: zhengcan
 * @date: 2023/4/2
 **/
public class SaveOrderConvert implements Convert<SaveOrder> {
    @Override
    public OperateLogDo convert(SaveOrder saveOrder) {
        OperateLogDo operateLogDo = new OperateLogDo();
        operateLogDo.setOrderId(saveOrder.getId());
        return operateLogDo;
    }
}
