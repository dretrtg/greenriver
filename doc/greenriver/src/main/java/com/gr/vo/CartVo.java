package com.gr.vo;

import java.util.List;
import java.math.BigDecimal;

/**
 * Created by 18334 on 2019/6/17.
 */
public class CartVo {

    private List<CartGabageVo> cartGabageVoList;
    private BigDecimal cartTotalPrice;
    private Boolean allChecked;//是否已经都勾选
    private String imageHost;

    public List<CartGabageVo> getCartGabageVoList() {
        return cartGabageVoList;
    }

    public void setCartGabageVoList(List<CartGabageVo> cartGabageVoList) {
        this.cartGabageVoList = cartGabageVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
