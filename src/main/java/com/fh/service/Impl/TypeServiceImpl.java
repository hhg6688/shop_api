package com.fh.service.Impl;

import com.fh.dao.TypeDao;
import com.fh.model.Area;
import com.fh.model.Type;
import com.fh.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeDao typeDao;


    @Override
    public List<Type> queryAllData() {
        return typeDao.selectList(null);
    }
}
