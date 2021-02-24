package com.gr.service;

import com.github.pagehelper.PageInfo;
import com.gr.common.ServerResponse;
import com.gr.pojo.Shipping;

/**
 * Created by 18334 on 2019/6/16.
 */
public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse<String> del(Integer userId, Integer shippingId);

    ServerResponse update(Integer userId, Shipping shipping);

    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
