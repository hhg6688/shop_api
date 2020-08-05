package com.fh.controller;

import com.fh.model.Brand;
import com.fh.common.jsonData;
import com.fh.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("BrandController")
public class BrandController {
    @Autowired
    private BrandService brandService;

     @RequestMapping("queryBrand")
    @ResponseBody
    public Map selectArea(){
        Map map = new HashMap();
        try {
            List<Brand> brandList=brandService.selectBrand();
            map.put("code",200);//
            map.put("data",brandList);
            map.put("message","success");
        }catch (Exception e){
            map.put("code",500);
            map.put("message",e.getMessage());
        }
        return map;
    }

    @RequestMapping("queryBrand2")
    @ResponseBody
    public jsonData selectArea2(){
        try {
            List<Brand> brandList=brandService.selectBrand();
            return jsonData.getJsonSuccess(brandList);
        }catch (Exception e){
            return jsonData.getJsonError(e.getMessage());
        }
    }
}
