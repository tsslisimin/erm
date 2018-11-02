/**
 * @author axin
 * @param {*排序的字段} name
 * @param {*默认不填为降序，asc则为升序} method
 */

let sortBy = function (name, method) {
  return function (o, p) {
    var a, b
    if (typeof o === 'object' && typeof p === 'object' && o && p) {
      a = o[name]
      b = p[name]
      if (a === b) {
        return 0
      }
      if (typeof a === typeof b && method == 'asc') {
        return a < b ? -1 : 1
      }
      return a > b ? -1 : 1
    }
  }
}

export default sortBy
