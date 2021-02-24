package com.gr.service;

import com.github.pagehelper.PageInfo;
import com.gr.common.ServerResponse;
import com.gr.vo.OrderVo;

/**
 * Created by 18334 on 2019/6/18.
 */
public interface IOrderService {

    ServerResponse pay(Long orderNo, Integer userId, String path);
//    ServerResponse aliCallback(Map<String,String> params);
    ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);
    ServerResponse createOrder(Integer userId,Integer shippingId);
    ServerResponse<String> cancel(Integer userId,Long orderNo);
    ServerResponse getOrderCartGabage(Integer userId);
    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);
    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);

    //backend
    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);
    ServerResponse<OrderVo> manageDetail(Long orderNo);
    ServerResponse<PageInfo> manageSearch(Long orderNo,int pageNum,int pageSize);
    ServerResponse updateOrder(Long orderNo);
}
