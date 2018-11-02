const glob = require('globby')
const path = require('path')
const webpack = require('webpack')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const HtmlWebpackExtPlugin = require('html-webpack-ext-plugin')
const FriendlyErrorsWebpackPlugin = require('friendly-errors-webpack-plugin')
const ProgressBarPlugin = require('progress-bar-webpack-plugin')
const isProduction = process.env.NODE_ENV !== 'development'

const assetsDir = resolve('src')
const distDir = resolve('static')
const viewDir = resolve('src/view')

function resolve (dir) {
  return path.join(__dirname, '..', dir)
}

function moduleName (modules) {
  let filename = path.basename(modules)
  let parts = filename.split('.')
  parts.pop()
  filename = parts.join('.')
  return path.dirname(modules) + '/' + filename
}

const jsEntry = (() => {
  const obj = {}
  const files = glob.sync(['js/conf/**/!(_*|source|router)/!(_*).js'], { cwd: assetsDir })
  files.forEach(val => {
    let parts = val.split(/[\\/]/)
    parts.shift()
    parts.shift()
    let modules = parts.join('/')
    let entry = moduleName(modules)
    obj[entry] = val
  })
  return obj
})()

const minifierConfig = isProduction ? {
  removeComments: true,
  removeCommentsFromCDATA: true,
  collapseWhitespace: true,
  collapseBooleanAttributes: true,
  removeRedundantAttributes: true,
  useShortDoctype: true,
  minifyJS: true,
  removeScriptTypeAttributes: true,
  maxLineLength: 1024
} : false

const getPageEntry = view => jsEntry[view] ? view : ''

const pages = glob.sync(['**/!(_*|source)/!(_*).html'], { cwd: viewDir }).map(p => {
  let entry = getPageEntry(p.replace('.html', ''))
  let chunks = ['common']
  if (entry) {
    chunks.push(entry)
  }
  return new HtmlWebpackPlugin({
    filename: path.join('view', p),
    template: `html-loader?min=false!src/view/${p}`,
    cache: true,
    inject: true,
    chunks: chunks,
    minify: minifierConfig
  })
})

const config = {
  entry: jsEntry,
  output: {
    path: distDir,
    publicPath: '/',
    filename: 'js/[name].[chunkhash:7].js'
  },
  devServer: {
    historyApiFallback: true,
    hot: true,
    inline: true,
    progress: true
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        loader: 'babel-loader?cacheDirectory=true&cacheIdentifier=true',
        exclude: /node_modules/
      },
      {
        test: /\.css$/,
        loader: 'style-loader!css-loader!autoprefixer-loader'
      },
      {
        test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
        loader: 'file-loader',
        options: {
          name: 'images/[name].[hash:7].[ext]'
        }
      },
      {
        test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
        loader: 'url-loader',
        options: {
          limit: 10000,
          name: 'fonts/[name].[hash:7].[ext]'
        }
      }
    ]
  },
  resolve: {
    modules: [
      resolve('node_modules'),
      resolve('src'),
      resolve('src/js')
    ],
    extensions: ['.js', '.vue', '.scss']
  },
  externals: {
    'vue': 'Vue',
    'vue-router': 'VueRouter',
    'jquery': '$',
    'lodash': '_',
    'echarts': 'echarts',
    'lightbox': 'lightbox'
  },
  plugins: [
    new ProgressBarPlugin(),
    new webpack.ProvidePlugin({ vue: 'Vue', _: 'lodash' }),
    new FriendlyErrorsWebpackPlugin({ clearConsole: false }),
    new HtmlWebpackExtPlugin({ cache: true, delimiter: '$', locals: {} }),
    ...pages
  ]
}

module.exports = {
  isProduction: isProduction,
  assetsDir: assetsDir,
  distDir: distDir,
  config: config
}
