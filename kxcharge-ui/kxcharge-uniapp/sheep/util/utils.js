// import jsrsasign from 'jsrsasign';
import moment from "dayjs";
import {
  TerminalEnum
} from "./dict";
import routine from "./routine";

// export const decodeToken = (token) => {
// 	console.log(token)
//   let obj = null
//   if (token !== '') {
//     const payload = jsrsasign.KJUR.jws.JWS.parse(token)
//     if (payload.hasOwnProperty('payloadObj')) {
//       obj = payload.payloadObj
//     }
//   }
//   return obj
// }

export function handleIntervalMoney(data) {
  const obj = {};
  const timeArr = data.split(";").forEach(item => {
    if (item) {
      const t = item.split(":");
      obj[`${t[0]}:${t[1]}:${t[2]}`] = Number(t[3]);
    }
  });
  return obj;
}

export function getNowIntervalFee(electricityFee, serviceFee) {
  const obj = JSON.parse(JSON.stringify(electricityFee));
  const now = {
    time: '',
    money: 0
  }
  for (const key in serviceFee) {
    if (obj[key]) {
      obj[key] += serviceFee[key];
    } else {
      obj[key] = serviceFee[key];
    }
  }
  for (const k in obj) {
    const nowTime = +new Date();
    const timeArr = k.split('-').map(t => moment(`${moment().format("YYYY-MM-DD")} ${t}`).valueOf());
    const tmArr = [...timeArr, obj[k]]
    if (nowTime > tmArr[0] && nowTime <= tmArr[1]) {
      now.time = k;
      now.money = now.money + (tmArr[2] || 0)
    }
  }
  now.money = formatValue(now.money, 4, null, false);
  return now;
}

export function isDefinedAndNotNull(value) {
  return typeof value !== 'undefined' && value !== null;
}

export function isNumeric(value) {
  return (value - parseFloat(value) + 1) >= 0;
}

export function formatValue(value, dec, units, showZeroDecimals) {
  if (isDefinedAndNotNull(value) && isNumeric(value) &&
    (isDefinedAndNotNull(dec) || isDefinedAndNotNull(units) || Number(value).toString() === value)) {
    let formatted = Number(value);
    if (isDefinedAndNotNull(dec)) {
      formatted = formatted.toFixed(dec);
    }
    if (!showZeroDecimals) {
      formatted = (Number(formatted));
    }
    formatted = formatted.toString();
    if (isDefinedAndNotNull(units) && units.length > 0) {
      formatted += ' ' + units;
    }
    return formatted;
  } else {
    return value !== null ? value : '';
  }
}

export function convertSecondsToTime(stratTs, endTs) {
  if (!stratTs || !endTs) {
    return '0分钟'
  }
  const seconds = (moment(endTs).valueOf() - moment(stratTs).valueOf()) / 1000 < 0 ? 0 : (moment(endTs).valueOf() -
    moment(stratTs).valueOf()) / 1000;
  const hours = Math.floor(seconds / 3600);
  const minutes = Math.floor((seconds - (hours * 3600)) / 60);
  let timeString = '';
  if (hours > 0) {
    timeString += hours + '小时';
  }
  if (minutes > 0 || (hours === 0 && minutes === 0)) {
    timeString += minutes + '分钟';
  }

  return timeString.trim();
}

export function showToast(iconType, title) {
  uni.showToast({
    icon: iconType,
    title
  })
}

export function throttle(func, wait = 50) {
  // 上一次执行该函数的时间
  let lastTime = 0;
  return function(...args) {
    // 当前时间
    let now = +new Date();
    // 将当前时间和上一次执行函数时间对比
    // 如果差值大于设置的等待时间就执行函数
    if (now - lastTime > wait) {
      lastTime = now;
      func.apply(this, args);
    }
  };
};

export function handleURL(url) {
  const reg = /^(http(s)?:\/\/)?([^/]+)\/(.*)$/;
  const matches = url.match(reg);
  const protocol = matches[0];
  const domain = matches[3];
  const filePath = matches[4];

  return {
    protocol,
    domain,
    filePath
  };
};

/**
 * 将分转成元
 *
 * @param price 分，例如说 100 分
 * @returns {string} 元，例如说 1.00 元
 */
export function fen2yuan(price) {
  return (price / 100.0).toFixed(2)
}

/**
 * 获取终端类型
 * https://uniapp.dcloud.net.cn/tutorial/platform.html#preprocessor
 *
 * @return {number | null} 终端类型
 */
export async function getTerminal() {
  let terminal = null;
  const provior = await routine.getProvider();
  if (provior.includes('weixin')) {
    terminal = TerminalEnum.WECHAT_MINI_PROGRAM.terminal
  } else {
    terminal = TerminalEnum.ALIPAY_APP.terminal
  }
  return terminal;
}


/**
 * opt  object | string
 * to_url object | string
 * 例:
 * this.Tips('/pages/test/test'); 跳转不提示
 * this.Tips({title:'提示'},'/pages/test/test'); 提示并跳转
 * this.Tips({title:'提示'},{tab:1,url:'/pages/index/index'}); 提示并跳转值table上
 * tab=1 一定时间后跳转至 table上
 * tab=2 一定时间后跳转至非 table上
 * tab=3 一定时间后返回上页面
 * tab=4 关闭所有页面跳转至非table上
 * tab=5 关闭当前页面跳转至table上
 */
export function Tips(opt, to_url) {
  if (typeof opt == 'string') {
    to_url = opt;
    opt = {};
  }
  let title = opt.title || '',
    icon = opt.icon || 'none',
    endtime = opt.endtime || 2000,
    success = opt.success;
  if (title) uni.showToast({
    title: title,
    icon: icon,
    duration: endtime,
    success
  })
  if (to_url != undefined) {
    if (typeof to_url == 'object') {
      let tab = to_url.tab || 1,
        url = to_url.url || '';
      switch (tab) {
        case 1:
          //一定时间后跳转至 table
          setTimeout(function() {
            uni.switchTab({
              url: url
            })
          }, endtime);
          break;
        case 2:
          //跳转至非table页面
          setTimeout(function() {
            uni.navigateTo({
              url: url,
            })
          }, endtime);
          break;
        case 3:
          //返回上页面
          setTimeout(function() {
            // #ifndef H5
            uni.navigateBack({
              delta: parseInt(url),
            })
            if (to_url.cb) {
              to_url.cb();
            }
            // #endif
            // #ifdef H5
            history.back();
            // #endif
          }, endtime);
          break;
        case 4:
          //关闭当前所有页面跳转至非table页面
          setTimeout(function() {
            uni.reLaunch({
              url: url,
            })
          }, endtime);
          break;
        case 5:
          //关闭当前页面跳转至非table页面
          setTimeout(function() {
            uni.redirectTo({
              url: url,
            })
          }, endtime);
          break;
      }

    } else if (typeof to_url == 'function') {
      setTimeout(function() {
        to_url && to_url();
      }, endtime);
    } else {
      //没有提示时跳转不延迟
      setTimeout(function() {
        console.log('to_url222: ', to_url);
        uni.navigateTo({
          url: to_url,
        })
      }, title ? endtime : 0);
    }
  }
}

export async function handleSubmitOrderResultForWxLite(displayContent, type, navigate) {
  const provior = await routine.getProvider()
  const parmas = {}
  if (!provior.includes('alipay')) {
    const content = type === 'tonglian_app' ? JSON.parse(displayContent || '{}').payinfo : displayContent
    const payConfig = JSON.parse(content || '{}');
    parmas['timeStamp'] = payConfig.timeStamp
    parmas['nonceStr'] = payConfig.nonceStr
    parmas['package'] = payConfig.packageValue || payConfig.package
    parmas['signType'] = payConfig.signType
    parmas['paySign'] = payConfig.paySign
  } else {
    parmas['orderInfo'] = displayContent
  }
  uni.requestPayment({
    ...parmas,
    success: res => {
      // this.showModal = false;
      // uni.hideLoading();
      return Tips({
        title: '支付成功',
        icon: 'success'
      }, navigate);
    },
    fail: e => {
      // this.showModal = false;
      // uni.hideLoading();
      // 关闭支付的情况
      if (e.errMsg === 'requestPayment:cancel' ||
        e.errMsg === 'requestPayment:fail cancel') {
        return Tips({
          title: '取消支付'
        });
      }
      return Tips({
        title: e.errMsg,
        icon: 'error'
      });
    }
  })
}