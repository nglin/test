package cn.tedu.sp04.service;

import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Override
    public Order getOrder(String orderId) {
        /*
        获取订单
        调用用户获取用户数据，调用商品获取商品列表
         */

        //TODO: 远程调用商品服务，获取商品
        //TODO: 远程调用用户，获取用户数据

        Order order = new Order();
        order.setId(orderId);
        //order.setItems(商品列表);
        //order.setUser(用户);
        return order;
    }
    @Override
    public void addOrder(Order order) {
        log.info("添加订单： "+order);
    }
}
