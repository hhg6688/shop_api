package com.fh.controller;

import com.fh.common.Interceptor.CountException;
import com.fh.model.Order;
import com.fh.service.OrderService;
import com.fh.common.jsonData;
import com.fh.util.RedisUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("OrderController")
public class OrderController {
    @Autowired
    private OrderService orderService;
@RequestMapping("createOrder")
    public jsonData createOrder(Integer addressId,Integer payType,String cartIds,String flag) throws CountException {

    boolean exists = RedisUser.exists(flag);//判断redis是否存在key
    if(exists==true){//二次请求
        return jsonData.getJsonError(300,"请求处理中");
    }else{
        RedisUser.set(flag,"",10);
    }
        Map map =orderService.addCreateOrder(addressId,payType,cartIds);
        return jsonData.getJsonSuccess(map);
    }
    //创建二维码
    @RequestMapping("createMoneyPhoto")
    public jsonData createMoneyPhoto(Integer orderId) throws Exception {
        Map moneyPhoto=orderService.createMoneyPhoto(orderId);
    return jsonData.getJsonSuccess(moneyPhoto);
    }

    @RequestMapping("queryPayStatus")
    public jsonData queryPayStatus(Integer orderId) throws Exception {
    Integer status=orderService.queryPayStatus(orderId);
    return jsonData.getJsonSuccess(status);
    }

    @RequestMapping("queryOrderData")
    public jsonData queryOrderData(){
       Map map=orderService.selectOrderData();
    return jsonData.getJsonSuccess(map);
    }
}
