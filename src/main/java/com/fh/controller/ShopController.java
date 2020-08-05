package com.fh.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.model.Shop;
import com.fh.service.ShopService;
import com.fh.common.jsonData;
import com.fh.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.List;

@Controller
@RequestMapping("ShopController")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @RequestMapping("queryShop")
    @ResponseBody
    public jsonData selectShop(){
        List<Shop> shopList=shopService.selectShop();
        return jsonData.getJsonSuccess(shopList);
    }


    @RequestMapping("queryShop2")
    @ResponseBody
    public jsonData queryShop(){
        Jedis jedis= new Jedis("192.168.239.134");
        if (jedis.get("shop")!= null){
            return jsonData.getJsonSuccess(jedis.get("shop"));
        }else{
            List<Shop> shopList=shopService.selectShop();
            String sale = JSONObject.toJSONString(shopList);
            jedis.set("shop",sale);
            return jsonData.getJsonSuccess(shopList);
        }
    }
    @RequestMapping("queryShopByTypeIds")
    @ResponseBody
        public jsonData queryShopByTypeIds(String typeId){
        List<Shop> shopList=shopService.queryShopByTypeIds(typeId);
        return jsonData.getJsonSuccess(shopList);
        }


    @RequestMapping("queryShopBySale")
    @ResponseBody
    public jsonData queryShopBySale(){
        Jedis jedis = RedisUtils.getJedis();
        String saleShop = jedis.get("saleShop");
        if (StringUtils.isEmpty(saleShop)==true) {
            List<Shop> saleList=shopService.queryShopBySale();
            String sale = JSONObject.toJSONString(saleList);
            jedis.set("saleShop",sale);
        }
        RedisUtils.returnJedis(jedis);
        return jsonData.getJsonSuccess(jedis.get("saleShop"));
    }


}
