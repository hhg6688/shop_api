package com.fh.service;

import java.util.List;

public interface CartService {
    Integer addShopToCart(Integer proId, Integer count);

    List queryCartAll();

    void updateCartSum(Integer id);

    void updateJiaCartSum(Integer id);

    void deleteCart(Integer id);

    void updateCheck(Integer id);
}
