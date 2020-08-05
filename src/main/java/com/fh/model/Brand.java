package com.fh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("shop_brand")
public class Brand {

    @TableId(value = "brandId",type = IdType.AUTO)
    private Integer brandId;
    @TableField("brandName")
    private String brandName;
    @TableField("brandIsUp")
    private Integer brandIsUp;
    @TableField("brandDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date brandDate;
    @TableField("brandUpdeteDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date  brandUpdeteDate;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getBrandIsUp() {
        return brandIsUp;
    }

    public void setBrandIsUp(Integer brandIsUp) {
        this.brandIsUp = brandIsUp;
    }

    public Date getBrandDate() {
        return brandDate;
    }

    public void setBrandDate(Date brandDate) {
        this.brandDate = brandDate;
    }

    public Date getBrandUpdeteDate() {
        return brandUpdeteDate;
    }

    public void setBrandUpdeteDate(Date brandUpdeteDate) {
        this.brandUpdeteDate = brandUpdeteDate;
    }
}
