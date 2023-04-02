package com.can.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @className: OrderService
 * @description: 订单服务
 * @author: zhengcan
 * @date: 2023/4/2
 **/
@Slf4j
@Service
public class OrderService {

    @RecordOperate(desc = "保存订单",convert = SaveOrderConvert.class)
    public Boolean saveOrder(SaveOrder saveOrder){
        log.info("save order: orderId:{}",saveOrder.getId());
        return true;
    }

    @RecordOperate(desc = "更新订单",convert = UpdateOrderConvert.class)
    public Boolean updateOrder(UpdateOrder updateOrder){
        log.info("update order: orderId:{}",updateOrder.getOrderId());
        return true;
    }
}
