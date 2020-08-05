package com.fh.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.dao.CartDao;
import com.fh.dao.ShopDao;
import com.fh.model.vo.ShopCart;
import com.fh.service.CartService;
import com.fh.util.RedisUser;
import com.fh.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl  implements CartService {
    @Autowired
    private ShopDao cartDao;
    @Autowired
    private HttpServletRequest request;
    @Override
    public Integer addShopToCart(Integer proId, Integer count) {
        //获取当前登录用户
        Map login_hhg = (Map) request.getAttribute("login_hhg");
        String iphoneNum = (String) login_hhg.get("iphoneNum");
        //获取购物车信息
        String getCart = RedisUser.hget("cart_" + iphoneNum, proId + "");
        if (StringUtils.isEmpty(getCart)){
            //查询当前要存入的商品信息
            ShopCart shopCart=cartDao.queryShopCartById(proId);
            shopCart.setCheck(true);
            //给购买数量赋值
            shopCart.setCount(count);
            //计算小计
            BigDecimal money = shopCart.getShopPrice().multiply(new BigDecimal(count));
            shopCart.setMoney(money);
            //将商品信息转换成json字符串
            String shopCartString = JSONObject.toJSONString(shopCart);
            //将数据存放到redis中
            RedisUser.hset("cart_"+iphoneNum,proId+"",shopCartString);
        }else {
            //将字符串转换成javabean
            ShopCart shopCart = JSONObject.parseObject(getCart, ShopCart.class);
            //修改个数    已存的数量加新数量
            shopCart.setCount(shopCart.getCount()+count);
            //重新计算小计
            BigDecimal money = shopCart.getShopPrice().multiply(new BigDecimal(shopCart.getCount()));
            shopCart.setMoney(money);
            //将商品信息转换成json字符串
            String shopCartString = JSONObject.toJSONString(shopCart);
            //将数据存放到redis中
            RedisUser.hset("cart_"+iphoneNum,proId+"",shopCartString);
        }
        //怎么获取商品种类的个数
        long hlen = RedisUser.hlen("cart_" + iphoneNum);
        return (int)hlen;
    }

    @Override
    public List queryCartAll() {
        Map login_hhg = (Map) request.getAttribute("login_hhg");
        String iphoneNum = (String) login_hhg.get("iphoneNum");
       List<String> cartAllData = RedisUser.hvals("cart_" + iphoneNum);
        return cartAllData;
    }

    @Override
    public void updateCartSum(Integer id) {
        Map login_hhg = (Map) request.getAttribute("login_hhg");
        String iphoneNum = (String) login_hhg.get("iphoneNum");
        String getCart = RedisUser.hget("cart_" + iphoneNum, id + "");
        if (!StringUtils.isEmpty(getCart)){
            ShopCart shopCart = JSONObject.parseObject(getCart, ShopCart.class);
            shopCart.setCount(shopCart.getCount()-1);
            //重新计算小计
            BigDecimal money = shopCart.getShopPrice().multiply(new BigDecimal(shopCart.getCount()));
            shopCart.setMoney(money);
            //将商品信息转换成json字符串
            String shopCartString = JSONObject.toJSONString(shopCart);
            //将数据存放到redis中
            RedisUser.hset("cart_"+iphoneNum,id+"",shopCartString);
        }
    }

    @Override
    public void updateJiaCartSum(Integer id) {
        Map login_hhg = (Map) request.getAttribute("login_hhg");
        String iphoneNum = (String) login_hhg.get("iphoneNum");
        String getCart = RedisUser.hget("cart_" + iphoneNum, id + "");
        if (!StringUtils.isEmpty(getCart)){
            ShopCart shopCart = JSONObject.parseObject(getCart, ShopCart.class);
            shopCart.setCount(shopCart.getCount()+1);
            //重新计算小计
            BigDecimal money = shopCart.getShopPrice().multiply(new BigDecimal(shopCart.getCount()));
            shopCart.setMoney(money);
            //将商品信息转换成json字符串
            String shopCartString = JSONObject.toJSONString(shopCart);
            //将数据存放到redis中
            RedisUser.hset("cart_"+iphoneNum,id+"",shopCartString);
        }
    }

    @Override
    public void deleteCart(Integer id) {
        Map login_hhg = (Map) request.getAttribute("login_hhg");
        String iphoneNum = (String) login_hhg.get("iphoneNum");
        Jedis jedis = RedisUtils.getJedis();
        jedis.hdel("cart_" + iphoneNum, id + "");
        RedisUtils.returnJedis(jedis);
    }

    @Override
    public void updateCheck(Integer id) {
        Map login_hhg = (Map) request.getAttribute("login_hhg");
        String iphoneNum = (String) login_hhg.get("iphoneNum");
        String getCart = RedisUser.hget("cart_" + iphoneNum, id + "");
        ShopCart shopCart = JSONObject.parseObject(getCart, ShopCart.class);
        if (shopCart.isCheck()==true){
            shopCart.setCheck(false);
        }else if (shopCart.isCheck()==false){
            shopCart.setCheck(true);
        }
        String shopCartString = JSONObject.toJSONString(shopCart);
        //将数据存放到redis中
        RedisUser.hset("cart_"+iphoneNum,id+"",shopCartString);
    }
}
