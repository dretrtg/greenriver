/*
* @Author: dretrtg
* @Date:   2019-05-08 15:19:12
* @Last Modified by:   dretrtg
* @Last Modified time: 2019-07-28 20:10:18
*/

'use strict';
require('./index.css');
require('page/common/nav/index.js');
require('page/common/header/index.js');
require('util/slider/index.js');
var navSide         = require('page/common/nav-side/index.js');
var templateBanner  = require('./banner.string');

var _mm             = require('util/mm.js');

$(function() {
    // 渲染banner的html
    var bannerHtml  = _mm.renderHtml(templateBanner);
    $('.banner-con').html(bannerHtml);
    // 初始化banner
    var $slider     = $('.banner').unslider({
        dots: true
    });
    // 前一张和后一张操作的事件绑定
    $('.banner-con .banner-arrow').click(function(){
        var forward = $(this).hasClass('prev') ? 'prev' : 'next';
        $slider.data('unslider')[forward]();
    });
});

var swiper = new Swiper('.swiper-container', {
        speed: 700,
        slidesPerView: 'auto',
        centeredSlides: true,
        on: {
            init: function() {
                this.slides.removeClass('init');
            },
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },

    });
    swiper.$el.parent('.apple-retail')[0].onmouseover = function() {
        swiper.$el.addClass('mouse-hover');
    };
    swiper.$el.parent('.apple-retail')[0].onmouseout = function() {
        swiper.$el.removeClass('mouse-hover');
    };



