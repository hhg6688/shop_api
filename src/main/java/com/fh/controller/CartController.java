package com.fh.controller;

import com.fh.common.jsonData;
import com.fh.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("CartController")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("addCart")
    public jsonData addCart(@RequestParam("typeId")Integer proId, Integer count){
        Integer countType=cartService.addShopToCart(proId,count);
        return jsonData.getJsonSuccess(countType);
    }


    @RequestMapping("queryCartAll")
    public jsonData queryCartAll(){
       List list= cartService.queryCartAll();
        return jsonData.getJsonSuccess(list);
    }

    @RequestMapping("jianCartSum")
    public jsonData jianCartSum(Integer id){
        cartService.updateCartSum(id);
    return jsonData.getJsonSuccess("数量减少成功");
    }

    @RequestMapping("jiaCartSum")
    public jsonData jiaCartSum(Integer id){
        cartService.updateJiaCartSum(id);
        return jsonData.getJsonSuccess("数量增加成功");
    }
    //删除redis中的key
    @RequestMapping("deleteCart")
    public jsonData deleteCart(Integer id){
        cartService.deleteCart(id);
        return jsonData.getJsonSuccess("删除购物车成功");
    }
    //修改key的field的值
    @RequestMapping("updateCheck")
    public jsonData updateCheck(Integer id){
        cartService.updateCheck(id);
        return jsonData.getJsonSuccess("勾选状态修改成功");
    }

}
