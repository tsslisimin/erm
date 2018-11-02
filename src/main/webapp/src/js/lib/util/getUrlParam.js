/**
 * 获得浏览器参数值
 *
 */
let URL = {
  getVal: function (key) {
    var reg = new RegExp('(^|&)' + key + '=([^&]*)(&|$)')
    var result = window.location.search.substr(1).match(reg)
    return result ? decodeURIComponent(result[2]) : null
  }
}

export { URL }

// self.activityId = URL.getVal('activityId');
