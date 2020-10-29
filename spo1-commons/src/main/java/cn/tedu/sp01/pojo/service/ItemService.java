package cn.tedu.sp01.pojo.service;

import cn.tedu.sp01.pojo.Item;

import java.util.List;

public interface ItemService {

    List<Item> getItems(String OrderId);

    void decreaseNumber(List<Item> items);
}
