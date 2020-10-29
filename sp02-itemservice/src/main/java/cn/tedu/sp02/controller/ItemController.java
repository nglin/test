package cn.tedu.sp02.controller;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Util.JsonResult;
import cn.tedu.sp01.pojo.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@Slf4j
public class ItemController {
    /*
    测试负载均衡时，在浏览器显示端口号
     */
    @Value("${server.port}")
    private int port;
    @Autowired
    private ItemService itemService;

    @GetMapping("/{orderId}") //和下面一行等价
    //@RequestMapping(path="/{orderId}",method = RequestMethod.GET)  // GET

    //@RequestMapping("/{orderId}")  // GET、POST

    public JsonResult<List<Item>> getItems(@PathVariable String orderId) throws InterruptedException {
        log.info("获取订单的商品列表, orderId="+orderId);

        //随机的延迟
        if (Math.random() < 0.9) {
            //延迟
            long t = new Random().nextInt(5000);
            log.info("延迟： "+t);
            Thread.sleep(t);
        }

        List<Item> items = itemService.getItems(orderId); //订单的商品列表
        return JsonResult.ok().msg("port="+port).data(items);
    }

    /*
    @RequestBody 完整接收请求协议体的内容，转成商品集合
     */
    @PostMapping("/decreaseNumber")
    public JsonResult<?> decreaseNumber(@RequestBody List<Item> items) {
        itemService.decreaseNumber(items); //减少商品库存
        return JsonResult.ok().msg("减少商品库存成功");
    }

}
