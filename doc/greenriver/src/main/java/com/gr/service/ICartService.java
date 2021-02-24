package com.gr.service;

import com.gr.common.ServerResponse;
import com.gr.vo.CartVo;

/**
 * Created by 18334 on 2019/6/17.
 */
public interface ICartService {

    ServerResponse<CartVo> add(Integer userId, Integer gabageId, Integer count);

    ServerResponse<CartVo> update(Integer userId, Integer gabageId, Integer count);

    ServerResponse<CartVo> deleteGabage(Integer userId, String gabageIds);

    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> selectOrUnSelect(Integer userId, Integer gabageId, Integer checked);

    ServerResponse<Integer> getCartGabageCount(Integer userId);
}
