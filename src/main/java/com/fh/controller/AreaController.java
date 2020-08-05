package com.fh.controller;

import com.fh.common.jsonData;
import com.fh.model.Area;
import com.fh.service.AreaService;
import com.fh.util.RedisUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("AreaController")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @RequestMapping("queryArea")
    @ResponseBody
    public Map selectArea(){
        Map map = new HashMap();
        try {
            List<Area> areaList=areaService.selectArea();
            map.put("code",200);//
            map.put("data",areaList);
            map.put("message","success");
        }catch (Exception e){
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @RequestMapping("queryArea2")
    @ResponseBody
    public jsonData selectArea2(){
        try {
            List<Area> areaList=areaService.selectArea();
            return jsonData.getJsonSuccess(areaList);
        }catch (Exception e){
            return jsonData.getJsonError(e.getMessage());
        }
    }

    @RequestMapping("queryArea3")
    @ResponseBody
    public jsonData selectArea3(){
            List<Area> areaList=areaService.selectArea();
        return jsonData.getJsonSuccess(areaList);
    }



    @RequestMapping("queryArea4")
    @ResponseBody

    public jsonData queryArea4(){
        Jedis jedis = RedisUtils.getJedis();
        String type = jedis.get("area");
        RedisUtils.returnJedis(jedis);
        return jsonData.getJsonSuccess(type);
    }

}
