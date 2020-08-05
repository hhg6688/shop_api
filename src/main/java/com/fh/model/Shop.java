package com.fh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@TableName("shop")
public class Shop {
    @TableId(value = "shopId",type = IdType.AUTO)
    private Integer shopId;
    @TableField("shopName")
    private String shopName;
    @TableField("shopImage")
    private String shopImage;
    @TableField("shopPrice")
    private Integer shopPrice;
    @TableField("shopIsUp")
    private Integer shopIsUp;
    @TableField("shopDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date shopDate;
    @TableField("shopSale")
    private Integer shopSale;
    @TableField("shopArea")
    private String shopArea;
    @TableField("shopType")
    private String shopType;
    @TableField("shopCount")
    private Integer shopCount;

    public Integer getShopCount() {
        return shopCount;
    }

    public void setShopCount(Integer shopCount) {
        this.shopCount = shopCount;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public Integer getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(Integer shopPrice) {
        this.shopPrice = shopPrice;
    }

    public Integer getShopIsUp() {
        return shopIsUp;
    }

    public void setShopIsUp(Integer shopIsUp) {
        this.shopIsUp = shopIsUp;
    }

    public Date getShopDate() {
        return shopDate;
    }

    public void setShopDate(Date shopDate) {
        this.shopDate = shopDate;
    }

    public Integer getShopSale() {
        return shopSale;
    }

    public void setShopSale(Integer shopSale) {
        this.shopSale = shopSale;
    }

    public String getShopArea() {
        return shopArea;
    }

    public void setShopArea(String shopArea) {
        this.shopArea = shopArea;
    }
}
