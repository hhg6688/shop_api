package com.fh.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.dao.AddressDao;
import com.fh.model.CartAddress;
import com.fh.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private  HttpServletRequest request;

    @Override
    public List<CartAddress> queryAddress() {
        Map user= (Map) request.getAttribute("login_hhg");
        String iphoneNum = (String) user.get("iphoneNum");
        QueryWrapper<CartAddress> qw= new QueryWrapper<>();
        qw.eq("vipId",iphoneNum);
        return addressDao.selectList(qw);
    }
}
