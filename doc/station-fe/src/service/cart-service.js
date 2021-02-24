/*
* @Author: dretrtg
* @Date:   2019-05-17 18:55:04
* @Last Modified by:   dretrtg
* @Last Modified time: 2019-07-29 11:11:25
*/

'use strict';

var _mm = require('util/mm.js');

var _cart = {
    // 获取购物车数量
    getCartCount : function(resolve, reject){
        _mm.request({
            url     : _mm.getServerUrl('/cart/get_cart_gabage_count.do'),
            success : resolve,
            error   : reject
        });
    },
    // 添加到购物车
    addToCart : function(gabageInfo, resolve, reject){
        _mm.request({
            url     : _mm.getServerUrl('/cart/add.do'),
            data    : gabageInfo,
            success : resolve,
            error   : reject
        });
    },
    // 获取购物车列表
    getCartList : function(resolve, reject){
        _mm.request({
            url     : _mm.getServerUrl('/cart/list.do'),
            success : resolve,
            error   : reject
        });
    },
    // 选择购物车商品
    selectGabage : function(gabageId, resolve, reject){
        _mm.request({
            url     : _mm.getServerUrl('/cart/select.do'),
            data    : {
                gabageId : gabageId
            },
            success : resolve,
            error   : reject
        });
    },
    // 取消选择购物车商品
    unselectGabage : function(gabageId, resolve, reject){
        _mm.request({
            url     : _mm.getServerUrl('/cart/un_select.do'),
            data    : {
                gabageId : gabageId
            },
            success : resolve,
            error   : reject
        });
    },
    // 选中全部商品
    selectAllGabage : function(resolve, reject){
        _mm.request({
            url     : _mm.getServerUrl('/cart/select_all.do'),
            success : resolve,
            error   : reject
        });
    },
    // 取消选中全部商品
    unselectAllGabage : function(resolve, reject){
        _mm.request({
            url     : _mm.getServerUrl('/cart/un_select_all.do'),
            success : resolve,
            error   : reject
        });
    },
    // 更新购物车商品数量
    updateGabage : function(gabageInfo, resolve, reject){
        _mm.request({
            url     : _mm.getServerUrl('/cart/update.do'),
            data    : gabageInfo,
            success : resolve,
            error   : reject
        });
    },
    // 删除指定商品
    deleteGabage : function(gabageIds, resolve, reject){
        _mm.request({
            url     : _mm.getServerUrl('/cart/delete_gabage.do'),
            data    : {
                gabageIds : gabageIds
            },
            success : resolve,
            error   : reject
        });
    },
}
module.exports = _cart;