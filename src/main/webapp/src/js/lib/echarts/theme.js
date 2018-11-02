let theme = {
  'seriesCnt': '20',
  'backgroundColor': 'rgba(0,0,0,0)',
  'titleColor': '#323e5d',
  'subtitleColor': '#aaaaaa',
  'textColorShow': false,
  'textColor': '#333',
  'markTextColor': '#ffffff',
  'color': [
    '#66a7ea',
    '#f68f70',
    '#f2dc8e',
    '#66c1ed',
    '#64d6e1',
    '#85a5de',
    '#a0c9db',
    '#f3c3b5',
    '#fcaed5',
    '#b2b2b2',
    '#84c6f8',
    '#84e3f8',
    '#9df7f6',
    '#9dd2f7',
    '#9db6f7',
    '#b49df7',
    '#d69df7',
    '#dfb1f9',
    '#8b8b8b',
    '#666666'
  ],
  'borderColor': '#ccc',
  'borderWidth': 0,
  'visualMapColor': [
    '#c2dcf7',
    '#84b8fe',
    '#66a7eb'
  ],
  'legendTextColor': '#333333',
  'kColor': '#66a7ea',
  'kColor0': '#f68f70',
  'kBorderColor': '#66a7ea',
  'kBorderColor0': '#f68f70',
  'kBorderWidth': 1,
  'lineWidth': 2,
  'symbolSize': 4,
  'symbol': 'emptyCircle',
  'symbolBorderWidth': 1,
  'lineSmooth': true,
  'graphLineWidth': 1,
  'graphLineColor': '#aaaaaa',
  'mapLabelColor': '#ffffff',
  'mapLabelColorE': 'rgb(255,255,255)',
  'mapBorderColor': '#f1f7ff',
  'mapBorderColorE': '#f68f70',
  'mapBorderWidth': 0.5,
  'mapBorderWidthE': 1,
  'mapAreaColor': '#d8ecff',
  'mapAreaColorE': 'rgba(246,143,112,0.8)',
  'axes': [
    {
      'type': 'all',
      'axisLineShow': true,
      'axisLineColor': '#333',
      'axisTickShow': true,
      'axisTickColor': '#333',
      'axisLabelShow': true,
      'axisLabelColor': '#333',
      'splitLineShow': true,
      'splitLineColor': [
        '#ccc'
      ],
      'splitAreaShow': false,
      'splitAreaColor': [
        'rgba(250,250,250,0.3)',
        'rgba(200,200,200,0.3)'
      ]
    },
    {
      'type': 'category',
      'axisLineShow': true,
      'axisLineColor': 'rgba(100,150,242,0.6)',
      'axisTickShow': true,
      'axisTickColor': 'rgba(100,150,242,0.6)',
      'axisLabelShow': true,
      'axisLabelColor': '#323e5d',
      'splitLineShow': false,
      'splitLineColor': [
        '#ccc'
      ],
      'splitAreaShow': false,
      'splitAreaColor': [
        'rgba(250,250,250,0.3)',
        'rgba(200,200,200,0.3)'
      ]
    },
    {
      'type': 'value',
      'axisLineShow': false,
      'axisLineColor': 'rgba(100,150,242,0.6)',
      'axisTickShow': false,
      'axisTickColor': 'rgba(100,150,242,0.6)',
      'axisLabelShow': true,
      'axisLabelColor': '#323e5d',
      'splitLineShow': true,
      'splitLineColor': [
        'rgba(100,150,242,0.2)'
      ],
      'splitAreaShow': false,
      'splitAreaColor': [
        'rgba(250,250,250,0.3)',
        'rgba(200,200,200,0.3)'
      ]
    },
    {
      'type': 'log',
      'name': '对数坐标轴',
      'axisLineShow': true,
      'axisLineColor': 'rgba(100,150,242,0.6)',
      'axisTickShow': true,
      'axisTickColor': 'rgba(100,150,242,0.6)',
      'axisLabelShow': true,
      'axisLabelColor': '#323e5d',
      'splitLineShow': true,
      'splitLineColor': [
        'rgba(100,150,242,0.2)'
      ],
      'splitAreaShow': false,
      'splitAreaColor': [
        'rgba(250,250,250,0.3)',
        'rgba(200,200,200,0.3)'
      ]
    },
    {
      'type': 'time',
      'name': '时间坐标轴',
      'axisLineShow': true,
      'axisLineColor': 'rgba(100,150,242,0.6)',
      'axisTickShow': true,
      'axisTickColor': 'rgba(100,150,242,0.6)',
      'axisLabelShow': true,
      'axisLabelColor': '#323e5d',
      'splitLineShow': true,
      'splitLineColor': [
        'rgba(100,150,242,0.2)'
      ],
      'splitAreaShow': false,
      'splitAreaColor': [
        'rgba(250,250,250,0.3)',
        'rgba(200,200,200,0.3)'
      ]
    }
  ],
  'axisSeperateSetting': true,
  'toolboxColor': '#999999',
  'toolboxEmpasisColor': '#6496f2',
  'tooltipAxisColor': '#000000',
  'tooltipAxisWidth': 1,
  'timelineLineColor': '#a3daff',
  'timelineLineWidth': 1,
  'timelineItemColor': '#a3daff',
  'timelineItemColorE': '#a3daff',
  'timelineCheckColor': '#03a6ff',
  'timelineCheckBorderColor': 'rgba(3,166,255,0.3)',
  'timelineItemBorderWidth': 1,
  'timelineControlColor': '#0098e1',
  'timelineControlBorderColor': '#a3daff',
  'timelineControlBorderWidth': 0.5,
  'timelineLabelColor': '#293c55',
  'datazoomBackgroundColor': 'rgba(242,248,255,1)',
  'datazoomDataColor': 'rgba(199,217,255,1)',
  'datazoomFillColor': 'rgba(118,182,255,0.4)',
  'datazoomHandleColor': '#3f99ff',
  'datazoomHandleWidth': '100',
  'datazoomLabelColor': '#333333',
  legend: { itemGap: 8, itemWidth: 10 },
  title: { itemGap: 8, textStyle: { fontWeight: 'normal', color: '#008acd' } },
  grid: { borderColor: '#eee', x: 95 },
  categoryAxis: {
    axisTick: { show: false },
    axisLine: { lineStyle: { color: '#d8d8d8' } },
    splitLine: { lineStyle: { color: [ '#f4f4f4' ] } },
    axisLabel: { textStyle: { color: '#000' }, color: '#666' }
  },
  valueAxis: {
    axisLine: { lineStyle: { color: '#666' }, show: false },
    axisTick: { show: false },
    nameTextStyle: { color: '#333' },
    splitLine: { show: true, lineStyle: { color: [ '#f4f4f4' ] }, color: '#333' }
  },
  barMaxWidth: 30
}

export default theme
