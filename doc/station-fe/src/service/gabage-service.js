/*
* @Author: dretrtg
* @Date:   2019-05-27 18:26:52
* @Last Modified by:   dretrtg
* @Last Modified time: 2019-07-29 11:15:38
*/

'use strict';

var _mm = require('util/mm.js');

var _gabage = {
    // 获取商品列表
    getGabageList : function(listParam, resolve, reject){
        _mm.request({
            url     : _mm.getServerUrl('/gabage/list.do'),
            data    : listParam,
            success : resolve,
            error   : reject
        });
    },
    // 获取商品详细信息
    getGabageDetail : function(gabageId, resolve, reject){
        _mm.request({
            url     : _mm.getServerUrl('/gabage/detail.do'),
            data    : {
                gabageId : gabageId
            },
            success : resolve,
            error   : reject
        });
    }
}
module.exports = _gabage;