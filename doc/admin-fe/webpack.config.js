/*
* @Author: Rosen
* @Date:   2016-11-20 13:19:28
* @Last Modified by:   dretrtg
* @Last Modified time: 2019-07-01 09:32:43
* 知识点：css单独打包、全局jquery引用、各种loader
*/

const webpack             = require('webpack');
const path                = require('path');
const ExtractTextPlugin   = require('extract-text-webpack-plugin');
const HtmlWebpackPlugin   = require('html-webpack-plugin');

// 环境变量, dev, (test), online
const WEBPACK_ENV            = process.env.WEBPACK_ENV || 'dev'; 

// webpack config
const config = {
    entry:{
        'app'       : ['./src/index.jsx']
    },
    externals:{
        '$'         :'window.jQuery',
        'jquery'    :'window.jQuery'
    },
    // path && publickPath
    output: {
        // path        : __dirname + '/dist/',
        // publicPath  : WEBPACK_ENV === 'online' ? '//s.happymmall.com/mmall_admin_fe/dist/' : '/dist/',
        path: path.resolve(__dirname, 'dist'),
        publicPath: '/dist/',
        filename    : 'js/[name].js'
    },
    resolve: {
        alias: {
            node_modules    : path.join(__dirname, '/node_modules'),
            lib             : path.join(__dirname, '/src/lib'),
            util            : path.join(__dirname, '/src/util'),
            component       : path.join(__dirname, '/src/component'),
            service         : path.join(__dirname, '/src/service'),
            page            : path.join(__dirname, '/src/page'),
        }
    },
    module: {
        // noParse: [],
        rules: [
            {test: /\.css$/, loader: ExtractTextPlugin.extract({
                use: 'css-loader',
                fallback : 'style-loader'
            })},
            {test: /\.scss$/, loader: ExtractTextPlugin.extract({
                use: 'css-loader!sass-loader',
                fallback : 'style-loader'
            })},
            {test: /\.(gif|jpg|png|woff|svg|eot|ttf)\??.*$/, loader: 'url-loader?limit=20000&name=resource/[name].[ext]'},
            {test: /\.(string)$/, loader: 'html-loader' },
            {
                test: /\.js?$/,
                exclude: /(node_modules)/,
                loader: 'babel-loader',
                query: {
                    presets: ['@babel/env']
                }
            },
            {
                test: /\.jsx?$/,
                exclude: /(node_modules)/,
                loader: 'babel-loader',
                query: {
                    presets: ['@babel/react', '@babel/env']
                }
            },
        ]
    },
    optimization: {
        runtimeChunk: false,
            splitChunks: {
            cacheGroups: {
                common: {
                    name: "vendors",
                    filename : 'js/base.js',
                    chunks: "all",
                    minChunks: 2
                }
            }
        }
    },
    plugins: [
        // 提出公共部分 
        // new webpack.optimize.CommonsChunkPlugin({
        //     name        : 'vendors',
        //     filename    : 'js/base.js'
        // }),
        // 单独处理css
        new ExtractTextPlugin('css/[name].css'),
        // html 加载
        new HtmlWebpackPlugin({
            filename        : 'view/index.html',
            title           : 'MMall 后台管理系统',
            template        : './src/index.html',
            favicon         : './favicon.ico',
            inject          : true,
            hash            : true,
            chunks          : ['vendors', 'app'],
            chunksSortMode  : 'dependency',
            minify          : {
                removeComments: true,
                collapseWhitespace: false
            }
        }),
    ]
};

// 开发环境下，使用devServer热加载
if(WEBPACK_ENV === 'dev'){
    config.entry.app.push('webpack-dev-server/client?http://localhost:8086');
}

module.exports = config;
