const path                = require("path");
const ExtractTextPlugin   = require('extract-text-webpack-plugin');
const HtmlWebpackPlugin   = require('html-webpack-plugin');
const webpack             = require("webpack");
const UglifyJSPlugin      = require("uglifyjs-webpack-plugin");

// 环境变量配置，dev / online
const WEBPACK_ENV         = process.env.WEBPACK_ENV || 'dev';

const getHtmlConfig = function(name, title){
    return {
        template    : './src/view/' + name + '.html',
        filename    : 'view/' + name + '.html',
        title       : title,
        inject      : true,
        hash        : true,
        chunks      : ['common', name]
    };
};
// webpack config
const config = {
    entry: {
        'common'            : ['./src/page/common/index.js'],
        'index'             : ['./src/page/index/index.js'],
        'list'              : ['./src/page/list/index.js'],
        'detail'            : ['./src/page/detail/index.js'],
        'gabage'            : ['./src/page/gabage/index.js'],
        'cart'              : ['./src/page/cart/index.js'],
        'user-login'        : ['./src/page/user-login/index.js'],
        'user-register'     : ['./src/page/user-register/index.js'],
        'user-pass-reset'   : ['./src/page/user-pass-reset/index.js'],
        'user-center'       : ['./src/page/user-center/index.js'],
        'user-center-update': ['./src/page/user-center-update/index.js'],
        'user-pass-update'  : ['./src/page/user-pass-update/index.js'],
        'result'            : ['./src/page/result/index.js'],
    },
    output: {
        path: path.resolve(__dirname, 'dist'),
        publicPath : '/dist/',
        filename: 'js/[name].js'
    },
    externals : {
        'jquery' : 'window.jQuery'
    },
    module: {
        rules: [
            { test: /\.css$/, 
                use:ExtractTextPlugin.extract({
                    use: 'css-loader'
                    // use: [
                    //             {
                    //             loader: 'css-loader',
                    //             options: {
                    //               modules: true
                    //             }
                    //           },
                    //         ] 
                })
            },
            { test: /\.(gif|png|jpg|woff|svg|eot|ttf)\??.*$/, use: 'url-loader?limit=100&name=resource/[name].[ext]' },
            { test: /\.string$/, use: 'html-loader'}
        ]
    },
    resolve : {
        alias : {
            node_modules    : __dirname + '/node_modules',
            util            : __dirname + '/src/util',
            page            : __dirname + '/src/page',
            service         : __dirname + '/src/service',
            image           : __dirname + '/src/image'
        }
    },
    optimization: {
        runtimeChunk: false,
            splitChunks: {
            cacheGroups: {
                common: {
                    name: "common",
                    filename : 'js/base.js',
                    chunks: "all",
                    minChunks: 2
                }
            }
        }
    },
    plugins: [
        // 独立通用模块到js/base.js
        // new webpack.optimize.UglifyJsPlugin({
        //     name : 'common',
        //     filename : 'js/base.js'
        // }),
        // 把css单独打包到文件里
        new ExtractTextPlugin("css/[name].css"),
        // html模板的处理
        new HtmlWebpackPlugin(getHtmlConfig('index', '首页')),
        new HtmlWebpackPlugin(getHtmlConfig('list', '商品列表页')),
        new HtmlWebpackPlugin(getHtmlConfig('detail', '商品详情页')),
        new HtmlWebpackPlugin(getHtmlConfig('cart', '购物车')),
        new HtmlWebpackPlugin(getHtmlConfig('user-login', '用户登录')),
        new HtmlWebpackPlugin(getHtmlConfig('user-register', '用户注册')),
        new HtmlWebpackPlugin(getHtmlConfig('user-pass-reset', '找回密码')),
        new HtmlWebpackPlugin(getHtmlConfig('user-center', '个人中心')),
        new HtmlWebpackPlugin(getHtmlConfig('user-center-update', '修改个人信息')),
        new HtmlWebpackPlugin(getHtmlConfig('user-pass-update', '修改密码')),
        new HtmlWebpackPlugin(getHtmlConfig('result', '操作结果')),
        new HtmlWebpackPlugin(getHtmlConfig('gabage', '选取页')),
    ],
};

if('dev' === WEBPACK_ENV){
    config.entry.common.push('webpack-dev-server/client?http://localhost:8080/');
}

module.exports = config;