package com.fh.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.Interceptor.CountException;
import com.fh.dao.*;
import com.fh.enums.PayStatusEnum;
import com.fh.model.CartAddress;
import com.fh.model.Order;
import com.fh.model.OrderCart;
import com.fh.model.Shop;
import com.fh.model.vo.ShopCart;
import com.fh.service.OrderService;
import com.fh.util.RedisUser;
import com.github.wxpay.sdk.FeiConfig;
import com.github.wxpay.sdk.WXPay;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private OrderCartDao orderCartDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private HttpServletRequest request;
    @Override
    public Map addCreateOrder(Integer addressId, Integer payType, String cartIds) throws CountException {
        //用来存放返回的数据
        Map map = new HashMap();
        //订单详情表
        List<OrderCart> list=new ArrayList<>();
        //完善订单表并保存数据库
        Order order= new Order();
        order.setAddressId(addressId);
        order.setCreateDate(new Date());
        order.setPayType(payType);
        order.setPayStatus(PayStatusEnum.PAY_STATUS_INIT.getStatus());
        //设置清单个数  前提库存够
        Integer proTypeCount=0;
        //设置总金额
        BigDecimal totalMoney=new BigDecimal(0);
        Map user= (Map) request.getAttribute("login_hhg");
        String iphoneNum = (String) user.get("iphoneNum");
        //获取所有的value值   类型为String类型
        List<String> cartMessage = RedisUser.hvals("cart_" + iphoneNum);
        for (int i = 0; i <cartMessage.size() ; i++) {
            //转换成javabean  存放在业务javabean中
            ShopCart shopCart = JSONObject.parseObject(cartMessage.get(i), ShopCart.class);
            //在购物车查询出订单商品
            if ((","+cartIds).indexOf(","+shopCart.getShopId()+",") !=-1){
                //根据业务bean中的id去查商品信息
                Shop shop=shopDao.selectListById(shopCart.getShopId());
                //判断购买数量是否大于商品库存
                if (shopCart.getCount()<=shop.getShopCount()){
                    //商品数量加一
                    proTypeCount++;
                    //钱数计算一下
                    totalMoney=totalMoney.add(shopCart.getMoney());
                    //维护订单详情表
                    OrderCart orderCart= new OrderCart();
                    orderCart.setCount(shopCart.getCount());
                    orderCart.setCartId(shopCart.getShopId());
                    list.add(orderCart);

                   int p1= shopDao.updateShop(shop.getShopId(),shopCart.getCount());
                   if (p1==0){
                       throw  new CountException("商品编号为："+shopCart.getShopId()+"的库存不足   库存只有："+shop.getShopCount()+"件了");
                   }
                }else {
                    throw  new CountException("商品编号为："+shopCart.getShopId()+"的库存不足   库存只有："+shop.getShopCount()+"件了");
                }
            }
        }
        order.setProTypeCount(proTypeCount);
        order.setTotalMoney(totalMoney);
        orderDao.insert(order);
        orderDao.addBatch(list,order.getId());
        for (int i = 0; i <cartMessage.size() ; i++) {
            ShopCart shopCart = JSONObject.parseObject(cartMessage.get(i), ShopCart.class);
            //在购物车查询出订单商品
            if ((","+cartIds).indexOf(","+shopCart.getShopId()+",") !=-1){
                RedisUser.hdel("cart_" + iphoneNum,shopCart.getShopId()+"");
            }
        }

        map.put("code",200);
        map.put("orderId",order.getId());
        map.put("totalMoney",totalMoney);
        return map;
    }

    @Override
    public Map createMoneyPhoto(Integer orderId) throws Exception {
        Map map= new HashMap();
        String photoUrl = RedisUser.get("order_" + orderId);
        if (StringUtils.isEmpty(photoUrl)!= true){
            map.put("code",200);
            map.put("url",photoUrl);
            return map;
        }
        Order order=orderDao.selectById(orderId);
        //微信支付  natvie 生成二维码
        //配置信息
        FeiConfig feiConfig=new FeiConfig();
        //得到微信支付对象
        WXPay wxPay=new WXPay(feiConfig);
        //设置请求参数
        Map<String, String> data = new HashMap<String, String>();
        //对订单信息描述
        data.put("body", "自定义购物车-订单支付");
        //String payId = System.currentTimeMillis()+"";
        //设置订单号 （保证唯一 ）
        data.put("out_trade_no","weixin1_order_hhg_"+orderId);
        //设置币种
        data.put("fee_type", "CNY");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        Date d=new Date();
        String dateStr = sdf.format(new Date(d.getTime() + 120000000));
        //设置二维码的失效时间
        data.put("time_expire", dateStr);
        //设置订单金额   单位分
        data.put("total_fee","1");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        //设置支付方式
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        // 统一下单
        Map<String, String> resp = wxPay.unifiedOrder(data);
       // System.out.println(orderId+"下订单结果为:"+ JSONObject.toJSONString(resp));
        if("SUCCESS".equalsIgnoreCase(resp.get("return_code"))&&"SUCCESS".equalsIgnoreCase(resp.get("result_code"))){
            map.put("code",200);
            map.put("url",resp.get("code_url"));
            //更新订单状态
            order.setPayStatus(PayStatusEnum.PAY_STATUS_ING.getStatus());
            orderDao.updateById(order);
            //将二维码存入redis  设置失效时间
            RedisUser.set("order_"+orderId,resp.get("code_url"),30*60);
        }else {
            map.put("code",600);
            map.put("info",resp.get("return_msg"));
        }
        return map;
    }

    @Override
    public Integer queryPayStatus(Integer orderId) throws Exception {

        FeiConfig feiConfig=new FeiConfig();
        WXPay wxPay=new WXPay(feiConfig);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no","weixin1_order_hhg_"+orderId);
        // 查询支付状态
        Map<String, String> resp = wxPay.orderQuery(data);
        System.out.println("查询结果："+JSONObject.toJSONString(resp));
        if("SUCCESS".equalsIgnoreCase(resp.get("return_code"))&&"SUCCESS".equalsIgnoreCase(resp.get("result_code"))){
            if("SUCCESS".equalsIgnoreCase(resp.get("trade_state"))){//支付成功
                //更新订单状态
                Order order=new Order();
                order.setId(orderId);
                order.setPayStatus(PayStatusEnum.PAY_STATUS_SUCCESS.getStatus());
                orderDao.updateById(order);
                return 1;
            }else if("NOTPAY".equalsIgnoreCase(resp.get("trade_state"))){
                return 3;
            }else if("USERPAYING".equalsIgnoreCase(resp.get("trade_state"))){
                return 2;
            }
        }
        return 0;
    }

    @Override
    public Map selectOrderData() {
        Map map=new HashMap();
        Map user= (Map) request.getAttribute("login_hhg");
        String iphoneNum = (String) user.get("iphoneNum");
        QueryWrapper<Order> qw= new QueryWrapper<>();
        qw.eq("vipName",iphoneNum);
        //根据当前用户查询order表
        List<Order> orders = orderDao.selectList(qw);
        for (int i = 0; i <orders.size() ; i++) {
            QueryWrapper<OrderCart> orderCart= new QueryWrapper<>();
            orderCart.eq("orderId",orders.get(i).getId());
            //根据order表的addressid查ordercart表
            List<OrderCart> orderCartList = orderCartDao.selectList(orderCart);
            map.put("orderCartList",orderCartList);
            for (int j = 0; j <orderCartList.size() ; j++) {
                QueryWrapper<Shop> shopQueryWrapper= new QueryWrapper<>();
                shopQueryWrapper.eq("shopId",orderCartList.get(j).getCartId());
                List<Shop> shopList = shopDao.selectList(shopQueryWrapper);
                map.put("shopList",shopList);
            }
        }
        QueryWrapper<CartAddress> cad= new QueryWrapper<>();
        cad.eq("vipId",iphoneNum);
        List<CartAddress> cartAddressList = addressDao.selectList(cad);
        map.put("orders",orders);
        map.put("cartAddressList",cartAddressList);
        return map;
    }


}
