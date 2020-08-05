package com.fh.controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.model.Area;
import com.fh.common.jsonData;
import com.fh.model.Type;
import com.fh.service.TypeService;
import com.fh.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("TypeController")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @RequestMapping("queryType")
    @ResponseBody
    public Map  selectTypes(){
        Map map = new HashMap();
        try {
            List<Type>  typeList=typeService.queryAllData();
            map.put("code",200);//
            map.put("data",typeList);
            map.put("message","success");
        }catch (Exception e){
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @RequestMapping("shuaXin")
    @ResponseBody
    public jsonData shuaXin(){
        //先查询所有数据库数据
        List<Type>  typeList=typeService.queryAllData();
        //转换成string
        String s = JSONObject.toJSONString(typeList);
        //连接redis
        Jedis jedis= new Jedis("192.168.239.134");
        //给redis赋值
        jedis.del("type");
        jedis.set("type", s);
        jedis.close();
        return jsonData.getJsonSuccess("数据刷新成功");
    }

    @RequestMapping("queryType2")
    @ResponseBody
    public jsonData queryType(){
        Jedis jedis= new Jedis("192.168.239.134");
        if (jedis.get("type")!= null){
            return jsonData.getJsonSuccess(jedis.get("type"));
        }else{
            List<Type>  typeList=typeService.queryAllData();
            return jsonData.getJsonSuccess(typeList);
        }
    }

    @RequestMapping("queryType3")
    @ResponseBody
    public jsonData queryType2(){
        Jedis jedis = RedisUtils.getJedis();
        String type = jedis.get("type");
        RedisUtils.returnJedis(jedis);
        return jsonData.getJsonSuccess(type);
    }

}
