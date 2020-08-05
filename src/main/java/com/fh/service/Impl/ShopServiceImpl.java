package com.fh.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.dao.ShopDao;
import com.fh.model.Shop;
import com.fh.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Override
    public List<Shop> selectShop() {
        return shopDao.selectList(null);
    }

    @Override
    public List<Shop> queryShopBySale() {
        return shopDao.queryShopBySale();
    }

    @Override
    public List<Shop> queryShopByTypeIds(String typeIds) {
        QueryWrapper<Shop> queryWrapper=new QueryWrapper<>();
        if (StringUtils.isEmpty(typeIds)){
            return shopDao.selectList(null);
        }else{
            queryWrapper.like("shopType",typeIds+"%");
            List<Shop> shopList=shopDao.selectList(queryWrapper);
            return shopList;
        }

    }
}
