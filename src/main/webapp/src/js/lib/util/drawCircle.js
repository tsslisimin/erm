/**
 * author axin
 * @param {*string id名称} id
 * @param {*number 0-1之间，圆的百分比} percent
 * @param {*string 16进制的颜色值，占比的圆的颜色} color
 * @param {*string 16进制的颜色值，底部圆的颜色} bgColor
 * @param {*number 圆的线条宽度} linewidth
 * @param {*number 圆占整个canvas宽度的百分比} r
 */

let drawCircle = function (id, percent, color, bgColor, linewidth, r) {
  var elm = document.getElementById(id);
  var ctx;
  var _x = elm.clientWidth * 0.5;
  var _y = elm.clientHeight * 0.5;
  var _r = r ? _y * r : _y * 0.8;
  var _count = 0;
  var _percent = percent || 0;

  var rander = function () {
    ctx.globalCompositeOperation = 'source-atop';
    ctx.beginPath();
    ctx.lineCap = 'round';
    ctx.strokeStyle = color;
    ctx.lineWidth = linewidth;
    if (_count <= _percent) {
      _count += 0.01;
      ctx.arc(_x, _y, _r, Math.PI * 1.5, (Math.PI * 1.5 - Math.PI * 2 * _count), true);
      ctx.stroke();
      ctx.closePath();
    } else {
      clearInterval(timer)
    }
  }
  if (elm.getContext) {
    ctx = elm.getContext('2d');
    ctx.clearRect(0, 0, elm.clientWidth, elm.clientHeight);
  }
  ctx.globalCompositeOperation = 'destination-over';
  ctx.beginPath();
  ctx.strokeStyle = bgColor;
  ctx.lineWidth = linewidth;
  ctx.arc(_x, _y, _r, 0, Math.PI * 2);
  ctx.stroke();
  var timer = setInterval(rander, 10);
}
// drawCircle('circle',0.6,'#ffff00','#cccccc',5,0.8);
export { drawCircle }
