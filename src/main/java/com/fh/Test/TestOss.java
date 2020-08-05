package com.fh.Test;

import com.fh.util.OSSUtils;
import org.junit.Test;

import java.io.File;

public class TestOss {
    @Test
    public void test(){
        File img=new File("C:\\Users\\黄怀光\\Pictures\\360downloads\\01.jpg");
        String s = OSSUtils.uploadFile(img);
        System.out.println(s);
    }




}
