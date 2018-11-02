const webpack = require('webpack')
const merge = require('webpack-merge')
const commonConfig = require('./webpack.config')
const CopyWebpackPlugin = require('copy-webpack-plugin')
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const OptimizeCssAssetsPlugin = require('optimize-css-assets-webpack-plugin')

const config = merge.smart(commonConfig.config, {
  output: {
    publicPath: '/static/',
    filename: 'js/[name].[chunkhash:7].js'
  },
  module: {
    rules: [
      {
        test: /\.scss$/,
        use: ExtractTextPlugin.extract({
          fallback: 'style-loader',
          use: 'css-loader!sass-loader!autoprefixer-loader'
        })
      },
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: {
          loaders: {
            sass: ExtractTextPlugin.extract({
              fallback: 'vue-style-loader',
              use: 'css-loader!sass-loader!autoprefixer-loader'
            }),
            scss: ExtractTextPlugin.extract({
              fallback: 'vue-style-loader',
              use: 'css-loader!sass-loader!autoprefixer-loader'
            })
          }
        }
      }
    ]
  },
  plugins: [
    new webpack.optimize.CommonsChunkPlugin({ name: 'common', filename: 'js/[name].[hash:7].js' }),
    new webpack.optimize.UglifyJsPlugin({ compress: { warnings: false }, comments: false }),
    new ExtractTextPlugin({ filename: 'css/[name].[contenthash:7].css', allChunks: true }),
    new OptimizeCssAssetsPlugin({
      assetNameRegExp: /\.css$/g,
      cssProcessor: require('cssnano'),
      cssProcessorOptions: { discardComments: { removeAll: true } },
      canPrint: true
    }),
    new CopyWebpackPlugin([
      {
        from: commonConfig.assetsDir + '/static/',
        to: commonConfig.distDir + '/'
      }
    ])
  ]
})

module.exports = config
