const webpack = require('webpack')
const WebpackDevServer = require('webpack-dev-server')
const merge = require('webpack-merge')
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const commonConfig = require('./webpack.config')
const SERVER_PORT = 9090

const config = merge.smart(commonConfig.config, {
  output: {
    filename: 'js/[name].js'
  },
  // devtool: 'source-map',
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
              use: 'css-loader?sourceMap!sass-loader?&outputStyle=expanded&sourceMap=false&sourceMapContents=true!autoprefixer-loader'
            }),
            scss: ExtractTextPlugin.extract({
              fallback: 'vue-style-loader',
              use: 'css-loader?sourceMap!sass-loader?&outputStyle=expanded&sourceMap=false&sourceMapContents=true!autoprefixer-loader'
            })
          }
        }
      }
    ]
  },
  plugins: [
    new webpack.optimize.CommonsChunkPlugin({ name: 'common', filename: 'js/[name].js' }),
    new webpack.HotModuleReplacementPlugin(),
    new ExtractTextPlugin({ filename: 'css/[name].css', allChunks: true })
  ]
})

if (require.main === module) {
  server(config)
} else {
  module.exports = config
}

function server (config) {
  const compiler = webpack(config)
  const server = new WebpackDevServer(compiler, {
    hot: true,
    contentBase: commonConfig.assetsDir,
    publicPath: config.output.publicPath,
    port: SERVER_PORT,
    disableHostCheck: true,
    proxy: {
      '/erm': {
        target: 'http://erm.comezx.com/',
        changeOrigin: true // 代理为域名时必须指定为true
      }
    },
    quiet: true,
    stats: {
      colors: true
    }
  })

  server.listen(SERVER_PORT, err => {
    console.log()
    console.log('listening at port %d', SERVER_PORT)
    if (err) {
      console.log(err)
    }
  })
}
