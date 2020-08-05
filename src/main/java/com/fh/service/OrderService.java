package com.fh.service;

import com.fh.common.Interceptor.CountException;
import com.fh.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Map addCreateOrder(Integer addressId, Integer payType, String cartIds) throws CountException;

    Map createMoneyPhoto(Integer orderId) throws Exception;


    Integer queryPayStatus(Integer orderId) throws Exception;


    Map selectOrderData();
}
