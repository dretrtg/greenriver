package com.gr.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.gr.common.Const;
import com.gr.common.ResponseCode;
import com.gr.common.ServerResponse;
import com.gr.dao.CartMapper;
import com.gr.dao.GabageMapper;
import com.gr.pojo.Cart;
import com.gr.pojo.Gabage;
import com.gr.service.ICartService;
import com.gr.util.BigDecimalUtil;
import com.gr.util.PropertiesUtil;
import com.gr.vo.CartGabageVo;
import com.gr.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 18334 on 2019/6/17.
 */

@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private GabageMapper gabageMapper;

    @Override
    public ServerResponse<CartVo> add(Integer userId, Integer gabageId, Integer count){
        if(gabageId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Cart cart = cartMapper.selectCartByUserIdGabageId(userId, gabageId);//注意这里，判断购物车是否有指定商品，就是依据商品ID查询购物车
        if(cart == null){
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setGabageId(gabageId);
            cartItem.setUserId(userId);
            cartMapper.insert(cartItem);
        }else{
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartVo> update(Integer userId, Integer gabageId, Integer count){
        if(gabageId == null || count == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUserIdGabageId(userId, gabageId);
        if(cart != null){
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKey(cart);//cartVo是传向前端的对象
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartVo> deleteGabage(Integer userId, String gabageIds){
        List<String> gabageList = Splitter.on(",").splitToList(gabageIds);
        if(CollectionUtils.isEmpty(gabageList)){
            return  ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdGabageIds(userId, gabageList);
        return this.list(userId);
    }

    @Override
    public ServerResponse<CartVo> list(Integer userId){
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> selectOrUnSelect (Integer userId, Integer gabageId, Integer checked){
        cartMapper.checkedOrUncheckedGabage(userId, gabageId, checked);
        return this.list(userId);
    }

    @Override
    public ServerResponse<Integer> getCartGabageCount(Integer userId){
        if(userId == null){
            return ServerResponse.createBySuccess(0);//为什么，没有登陆，显示的就是0
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartGabageCount(userId));
    }

    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();//cartVo是整个购物车对象，cartGabageVo是购物车中每一条货物对象，是向前端传达的
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartGabageVo> cartGabageVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if(CollectionUtils.isNotEmpty(cartList)){
            for(Cart cartItem : cartList){
                CartGabageVo cartGabageVo = new CartGabageVo();
                cartGabageVo.setId(cartItem.getId());
                cartGabageVo.setUserId(userId);
                cartGabageVo.setGabageId(cartItem.getGabageId());

                Gabage gabage = gabageMapper.selectByPrimaryKey(cartItem.getGabageId());
                if(gabage != null){
                    cartGabageVo.setGabageMainImage(gabage.getMainImage());
                    cartGabageVo.setGabageName(gabage.getName());
                    cartGabageVo.setGabageSubtitle(gabage.getSubtitle());
                    cartGabageVo.setGabageStatus(gabage.getStatus());
                    cartGabageVo.setGabagePrice(gabage.getPrice());
                    cartGabageVo.setGabageStock(gabage.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if(gabage.getStock() >= cartItem.getQuantity()){
                        //库存充足的时候
                        buyLimitCount = cartItem.getQuantity();
                        cartGabageVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else{
                        buyLimitCount = gabage.getStock();
                        cartGabageVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartGabageVo.setQuantity(buyLimitCount);
                    //计算总价
                    cartGabageVo.setGabageTotalPrice(BigDecimalUtil.mul(gabage.getPrice().doubleValue(),cartGabageVo.getQuantity()));
                    cartGabageVo.setGabageChecked(cartItem.getChecked());
                }

                if(cartItem.getChecked() == Const.Cart.CHECKED){
                    //如果已经勾选,增加到整个的购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartGabageVo.getGabageTotalPrice().doubleValue());
                }
                cartGabageVoList.add(cartGabageVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartGabageVoList(cartGabageVoList);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        return cartVo;
    }

    private boolean getAllCheckedStatus(Integer userId){
        if(userId == null){
            return false;
        }
        return cartMapper.selectCartGabageCheckedStatusByUserId(userId) == 0;
    }
}
