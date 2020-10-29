package cn.tedu.sp04.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.pojo.Util.JsonResult;
import cn.tedu.sp01.pojo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    //根据订单id获取订单
    @GetMapping("/{orderId}")
    public JsonResult<Order> getOrder(@PathVariable String orderId) {
        log.info("获取订单： orderId"+orderId);

        Order order = orderService.getOrder(orderId);
        return JsonResult.ok().data(order);
    }
    //添加订单，demo演示，不接收任何参数，数据在方法中写死
    @GetMapping("/")
    public JsonResult<?> addOrder() {
        //订单对象中包含： 订单id,用户,购买的商品
        Order order = new Order(
                "6u5u645y34",
                new User(8,"用户8", "密码8"),
                Arrays.asList(new Item[]{
                        new Item(1, "商品1", 2),
                        new Item(2, "商品2", 1),
                        new Item(3, "商品3", 1),
                        new Item(4, "商品4", 4),
                        new Item(5, "商品5", 3)
                }));
        orderService.addOrder(order);
        return JsonResult.ok().msg("添加订单成功");
    }



}
