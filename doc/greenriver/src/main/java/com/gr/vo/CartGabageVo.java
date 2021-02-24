package com.gr.vo;

import java.math.BigDecimal;
/**
 * Created by 18334 on 2019/6/17.
 */
public class CartGabageVo {

    private Integer id;
    private Integer userId;
    private Integer gabageId;
    private Integer quantity;//购物车中此商品的数量
    private String gabageName;
    private String gabageSubtitle;
    private String gabageMainImage;
    private BigDecimal gabagePrice;
    private Integer gabageStatus;
    private BigDecimal gabageTotalPrice;
    private Integer gabageStock;
    private Integer gabageChecked;//此商品是否勾选

    private String limitQuantity;//限制数量的一个返回结果

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGabageId() {
        return gabageId;
    }

    public void setGabageId(Integer gabageId) {
        this.gabageId = gabageId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getGabageName() {
        return gabageName;
    }

    public void setGabageName(String gabageName) {
        this.gabageName = gabageName;
    }

    public String getGabageSubtitle() {
        return gabageSubtitle;
    }

    public void setGabageSubtitle(String gabageSubtitle) {
        this.gabageSubtitle = gabageSubtitle;
    }

    public String getGabageMainImage() {
        return gabageMainImage;
    }

    public void setGabageMainImage(String gabageMainImage) {
        this.gabageMainImage = gabageMainImage;
    }

    public BigDecimal getGabagePrice() {
        return gabagePrice;
    }

    public void setGabagePrice(BigDecimal gabagePrice) {
        this.gabagePrice = gabagePrice;
    }

    public Integer getGabageStatus() {
        return gabageStatus;
    }

    public void setGabageStatus(Integer gabageStatus) {
        this.gabageStatus = gabageStatus;
    }

    public BigDecimal getGabageTotalPrice() {
        return gabageTotalPrice;
    }

    public void setGabageTotalPrice(BigDecimal gabageTotalPrice) {
        this.gabageTotalPrice = gabageTotalPrice;
    }

    public Integer getGabageStock() {
        return gabageStock;
    }

    public void setGabageStock(Integer gabageStock) {
        this.gabageStock = gabageStock;
    }

    public Integer getGabageChecked() {
        return gabageChecked;
    }

    public void setGabageChecked(Integer gabageChecked) {
        this.gabageChecked = gabageChecked;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }
}
