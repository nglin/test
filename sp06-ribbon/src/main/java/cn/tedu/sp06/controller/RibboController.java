package cn.tedu.sp06.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.pojo.Util.JsonResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class RibboController {

    @Autowired
    private RestTemplate restTemplate;

    //调用远程的商品服务
    //若调转失败，跳转到另一段代码执行
    @HystrixCommand(fallbackMethod = "getItemsFB")
    @GetMapping("/item-service/{orderId}")
    public JsonResult<List<Item>> getItems(@PathVariable String orderId){
        //调用远程服务
        //{1}是RestTemplate定义的一种占位符格式，后面的orderId会对占位符进行填充
        return restTemplate.getForObject("http://localhost:8001/{1}",JsonResult.class,orderId);
    }

    @HystrixCommand(fallbackMethod = "decreaseNumbeFB")
    @PostMapping("item-service/decreaseNumbe")
    public JsonResult<?> decreaseNumbe(@RequestBody List<Item> items){
        //调用商品服务，减少商品库存
        return restTemplate.patchForObject("http://localhost:8001/decreaseNumbe",
                items,  //post请求协议体数据
                JsonResult.class);
    }

    @HystrixCommand(fallbackMethod = "getUserFB")
    @GetMapping("/user-service/{userId}")
    public JsonResult<User> getUser(@PathVariable Integer userId){
        return restTemplate.getForObject("http://user-service/{1}", JsonResult.class,userId);
    }

    @HystrixCommand(fallbackMethod = "addScoreFB")
    @GetMapping("/user-service/{userId}/score")
    public JsonResult<?> addScore(@PathVariable Integer userId,Integer score){
        return restTemplate.getForObject("http:/user-service/{1}/score?score={2}",
                JsonResult.class,
                userId,score);
    }

    @HystrixCommand(fallbackMethod = "getOrderFB")
    @GetMapping("/order-service/{orderId}")
    public JsonResult<Order> getOrder(@PathVariable String orderId){
        return restTemplate.getForObject("http://order-service/{1}", JsonResult.class,orderId);
    }

    @HystrixCommand(fallbackMethod = "addOrderFB")
    @GetMapping("/order-serice/")
    public JsonResult<?> addOrder(){
        return restTemplate.getForObject("http://order-service/", JsonResult.class);
    }

    ///////////////////////////////////////

    public JsonResult<List<Item>> getItemsFB(String orderId){
        return JsonResult.err().msg("获取订单的商品列表失败");
    }

    public JsonResult<?> decreaseNumbeFB(List<Item> items){
        return JsonResult.err().msg("减少商品库存失败");
    }

    public JsonResult<User> getUserFB(Integer userId){
        return JsonResult.err().msg("获取用户失败");
    }

    public JsonResult<?> addScoreFB(Integer userId,Integer score){
        return JsonResult.err().msg("增加用户积分失败");
    }

    public JsonResult<Order> getOrderFB(String orderId){
        return JsonResult.err().msg("获取订单失败");
    }

    public JsonResult<?> addOrderFB(){
        return JsonResult.err().msg("保存订单失败");
    }

}
