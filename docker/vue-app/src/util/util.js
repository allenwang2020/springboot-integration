// 获取cookie、
export function getCookie (name) {//eslint-disable-line
  var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)")//eslint-disable-line
  if (arr = document.cookie.match(reg))//eslint-disable-line
    return (arr[2])//eslint-disable-line
  else//eslint-disable-line
    return null//eslint-disable-line
}

// 设置cookie,增加到vue实例方便全局调用
export function setCookie (c_name, value, expiredays) {//eslint-disable-line
  var exdate = new Date()//eslint-disable-line
  exdate.setDate(exdate.getDate() + expiredays)//eslint-disable-line
  document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())//eslint-disable-line
}

// 删除cookie
export function delCookie (name) {//eslint-disable-line
  var exp = new Date()//eslint-disable-line
  exp.setTime(exp.getTime() - 1)//eslint-disable-line
  var cval = getCookie(name)//eslint-disable-line
  if (cval != null)//eslint-disable-line
   document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString()//eslint-disable-line
}
