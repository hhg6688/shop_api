package com.fh.service.Impl;

import com.fh.dao.AreaDao;
import com.fh.model.Area;
import com.fh.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> selectArea() {
        //错误
        int a=1/0;
        return areaDao.selectList(null);
    }
}
