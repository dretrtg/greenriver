/*
* @Author: dretrtg
* @Date:   2019-05-19 21:52:46
* @Last Modified by:   dretrtg
* @Last Modified time: 2019-07-29 11:15:48
*/

'use strict';
require('./index.css');
require('page/common/nav-simple/index.js');
var _mm = require('util/mm.js');

$(function(){
    var type        = _mm.getUrlParam('type') || 'default',
        $element    = $('.' + type + '-success');
    // 显示对应的提示元素
    $element.show();
})