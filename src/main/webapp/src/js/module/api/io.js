import axios from 'axios'
import cookie from 'js/lib/util/cookie'
import _ from 'lodash'
import $ from 'jquery'

const io = {}
const methods = ['get', 'post']

_.forEach(methods, (e) => {
  io[e] = (url, data, success, fail, error, options) => {
    let headers = {}
    let responseType = 'json'

    if (typeof data === 'function') {
      options = error
      error = fail
      fail = success
      success = data
      data = undefined
    }

    if (e === 'get' && data) {
      let _data = _.clone(data)
      url += '?' + $.param(_data)
    }

    if (data) {
      if (typeof (data.headers) !== 'undefined') {
        _.merge(headers, data.headers)
      }
    }

    if (options) {
      if (typeof (options.responseType) !== 'undefined') {
        responseType = options.responseType
      }
    }

    if (cookie.get('token')) {
      let obj = {
        'X-Authorization': cookie.get('token')
      }

      _.merge(headers, obj)
    }

    axios({
      url,
      data,
      method: e,
      responseType: responseType,
      headers: headers || {}
    }).then(res => {
      // if (responseType === 'stream') {
      //   success(res.data)
      //   return
      // }
      if (res.data.code === 200 && success) {
        success(res.data)
      } else {
        if (responseType !== 'json') {
          success(res.data)
        } else {
          fail(res.data)
        }
      }
    }).catch(e => {
      error && error(e)
    })
  }
})

export default io
