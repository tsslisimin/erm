import Vue from 'vue'

Vue.filter('truncate', function (value, length, end) {
  var _value = value + ''
  var _length = length || 30
  var _end = end || '...'
  var result = '', newLength = 0, char = '', strLen = 0
  var regxCn = /[^\u4e00-\u9fa5]+/g

  if (!_value) {
    return ''
  }

  strLen = _value.replace(regxCn, '**').length

  if (_length >= strLen) {
    return _value
  }

  for (var i = 0, len = strLen; i < len; i++) {
    char = _value.charAt(i)
    if (char.match(regxCn) != null) {
      newLength += 2
    } else {
      newLength++
    }
    if (newLength > _length) {
      break
    }
    result += char
  }

  result += _end

  return result
})
