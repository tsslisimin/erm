import Vue from 'vue'

let numberFormat = function (value, digits, isPercent) {
  var _value, numStr, leftNum, leftlen, rightNum, result = ''

  if (value == null || value === '' || isNaN(value) || value === undefined) {
    return '--'
  }

  _value = +value

  if (isPercent) {
    _value *= 100
  }

  numStr = _value.toFixed(digits) + ''

  if (numStr.indexOf('.') >= 0) {
    leftNum = numStr.split('.')[0]
    rightNum = '.' + numStr.split('.')[1]
  } else {
    leftNum = numStr
    rightNum = ''
  }

  leftlen = leftNum.length

  if (leftlen <= 3) {
    result = leftNum
  } else {
    while (leftlen > 3) {
      result = ',' + leftNum.slice(-3) + result
      leftlen -= 3
      leftNum = leftNum.slice(0, leftlen)
    }

    if (leftNum) {
      result = leftNum + result
    }
  }

  return result + rightNum + (isPercent ? '%' : '')
}

Vue.filter('number', numberFormat)

export { numberFormat }
