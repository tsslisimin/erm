import create from '../create'
import _ from 'lodash'

var baseOption = {
  title: {
    text: '',
    subtext: ''
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    data: []
  },
  series: []
}

/**
 * 圆形图相关配置
 * @param {String} _id  容器id
 * @param {Object} _extend  扩展配置
 */
export default function (_id, _extend) {
  var options = {}

  if (!_extend.series.length) {
    throw new Error('series is empty')
  }

  options = _.merge({}, baseOption, _extend)

  return create(_id, options)
}
