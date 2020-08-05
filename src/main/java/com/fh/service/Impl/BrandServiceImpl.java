package com.fh.service.Impl;

import com.fh.dao.BrandDao;
import com.fh.model.Brand;
import com.fh.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDao brandDao;

    @Override
    public List<Brand> selectBrand() {
        return brandDao.selectList(null);
    }
}
