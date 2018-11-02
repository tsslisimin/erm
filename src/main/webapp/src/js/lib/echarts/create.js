import echarts from 'echarts'
import theme from './theme'

let creatEcharts = function (id, options) {
  let myChart = echarts.init(document.getElementById(id), theme)

  myChart.setOption(options)

  return myChart
}

export default creatEcharts
