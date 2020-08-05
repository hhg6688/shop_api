package com.fh.controller;

import com.fh.model.CartAddress;
import com.fh.service.AddressService;
import com.fh.common.jsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("AddressController")
public class AddressController {
        @Autowired
        private AddressService addressService;

        @RequestMapping("selectAddress")
        public jsonData selectAddress(){
            List<CartAddress> cartList=addressService.queryAddress();
            return jsonData.getJsonSuccess(cartList);
        }
}
