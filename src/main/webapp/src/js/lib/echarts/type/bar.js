import create from '../create'
import _ from 'lodash'

var baseOption = {
  title: {
    text: '',
    subtext: ''
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  legend: {
    data: []
  },
  toolbox: {},
  xAxis: [{
    type: 'category',
    boundaryGap: true,
    data: []
  }],
  yAxis: [{
    type: 'value'
  }],
  series: []
}

/**
 * 柱状图相关配置
 * @param {String} _id  容器id
 * @param {Object} _extend  扩展配置
 */
export default function (_id, _extend) {
  var options = {}

  if (!_extend.xAxis && !_extend.yAxis) {
    throw new Error('xAxis or yAxis is empty')
  }

  if (!_extend.series.length) {
    throw new Error('series is empty')
  }

  options = _.merge({}, baseOption, _extend)

  return create(_id, options)
}
