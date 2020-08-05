package com.fh.service;

import com.fh.model.Shop;

import java.util.List;

public interface ShopService {
    List<Shop> selectShop();

    List<Shop> queryShopBySale();


    List<Shop> queryShopByTypeIds(String typeIds);
}
