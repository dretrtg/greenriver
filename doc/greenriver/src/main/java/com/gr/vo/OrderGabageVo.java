package com.gr.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 18334 on 2019/6/18.
 */
public class OrderGabageVo {

    private List<OrderItemVo> orderItemVoList;
    private BigDecimal gabageTotalPrice;
    private String imageHost;

    public List<OrderItemVo> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public BigDecimal getGabageTotalPrice() {
        return gabageTotalPrice;
    }

    public void setGabageTotalPrice(BigDecimal gabageTotalPrice) {
        this.gabageTotalPrice = gabageTotalPrice;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
