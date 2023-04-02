package com.can;

import com.can.aop.OrderService;
import com.can.aop.SaveOrder;
import com.can.aop.UpdateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootAopApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAopApplication.class, args);
    }


    @Autowired
    private OrderService orderService;

    @Override
    public void run(String... args) throws Exception {

        SaveOrder saveOrder = new SaveOrder();
        saveOrder.setId(1L);

        orderService.saveOrder(saveOrder);

        UpdateOrder updateOrder = new UpdateOrder();
        updateOrder.setOrderId(2L);

        orderService.updateOrder(updateOrder);
    }
}
