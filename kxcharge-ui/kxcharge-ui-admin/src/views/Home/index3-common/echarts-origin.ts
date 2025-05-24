// import echarts from "echarts/types/dist/echarts"
// import * as echarts from "echarts"
// 生成时间段数据
export function generateData() {
  const data: any = []
  const startTime = new Date('2023-12-12T00:00:00')
  const endTime = new Date('2023-12-12T08:00:00')

  while (startTime <= endTime) {
    const timeLabel = startTime.getHours() + ':' + ('0' + startTime.getMinutes()).slice(-2)
    const chargeCount = Math.floor(Math.random() * 10) // 充电次数随机数（0-9）
    const chargeAmount = chargeCount && Math.floor(Math.random() * 100) // 充电量随机数（0-99）
    // const orderAmount = Math.floor(Math.random() * 1000); // 订单金额随机数（0-999）
    const orderAmount = chargeCount * chargeAmount // 订单金额随机数（0-999）

    data.push({
      time: timeLabel,
      chargeCount: chargeCount,
      chargeAmount: chargeAmount,
      orderAmount: orderAmount
    })

    const nextTime = startTime.getTime() + 60 * 60 * 1000 // 下一个小时
    startTime.setTime(nextTime)
  }

  return data
}

// 生成Echarts数据
const echartsData = generateData()
export const chatOptionsOriginA = {
  tooltip: {
    trigger: 'axis'
  },
  grid: {
    top: 40,
    left: 30,
    right: 30,
    bottom: 5,
    containLabel: true
  },
  legend: {
    data: ['充电次数', '充电量', '订单金额']
  },
  xAxis: {
    type: 'category',
    axisTick: {
      alignWithLabel: true
    },
    data: echartsData.map((item) => item.time)
  },
  yAxis: {},
  series: [
    {
      name: '充电次数',
      type: 'line',
      data: echartsData.map((item) => item.chargeCount)
    },
    {
      name: '充电量',
      type: 'line',
      data: echartsData.map((item) => item.chargeAmount)
    },
    {
      name: '订单金额',
      type: 'line',
      data: echartsData.map((item) => item.orderAmount)
    }
  ]
}

const xData = ['尖', '谷', '平', '峰']
const lastYearData = [88, 59, 32, 15]
const thisYearData = [99.45, 72.69, 532.46, 30.22]
const timeLineData = [1]
// const textColor = '#fff'
const lineColor = 'rgba(255,255,255,0.2)'
const colors = [
  {
    borderColor: 'rgba(2, 191, 232,1)',
    start: 'rgba(2, 191, 232,0.8)',
    end: 'rgba(2, 191, 232,0.1)'
  },
  {
    borderColor: 'rgba(1, 231, 152,1)',
    start: 'rgba(1, 231, 152,0.1)',
    end: 'rgba(1, 231, 152,0.8)'
  }
]
// 指定图表的配置项和数据
export const chatOptionsOriginB:any = {
  baseOption: {
    tooltip: {
      trigger: 'axis'
    },
    // backgroundColor: background,
    timeline: {
      show: false,
      top: 0,
      data: []
    },
    legend: {
      data: [ '充电量(度)', '充电次数(单)']
    },
    grid: [
      {
        show: false,
        left: '5%',
        top: 40,
        bottom: 5,
        containLabel: true,
        width: '45%',
      },
      {
        show: false,
        left: '0%',
        top: 40,
        bottom: '5%',
        width: "5%",
        containLabel: true
      },
      {
        show: false,
        left: '50%',
        top: 40,
        bottom: 5,
        containLabel: true,
        width: '45%'
      }
    ],
    xAxis: [
      {
        type: 'value',
        inverse: true, // 强制设置坐标轴分割间隔
        axisLine: {
          // 坐标轴轴线相关设置
          show: true, // 是否显示坐标轴轴线
          onZero: true, // X 轴或者 Y 轴的轴线是否在另一个轴的 0 刻度上
          lineStyle: {
            color: '#666'
          }
        },
        axisTick: {
          show: true
        },
        position: 'bottom',
        axisLabel: {
          show: true,
          color: '#666',
          fontSize: 12,
          fontFamily: 'DINPro-Regular'
        },
        // splitLine: {
        //   show: false
        // }
      },
      {
        gridIndex: 1,
        show: false
      },
      {
        type: 'value',
        gridIndex: 2,
        axisLine: {
          show: true, // 是否显示坐标轴轴线
          onZero: true, // X 轴或者 Y 轴的轴线是否在另一个轴的 0 刻度上
          lineStyle: {
            color: '666'
          }
        },
        axisTick: {
          show: true
        },
        position: 'bottom',
        axisLabel: {
          show: true,
          color: '#666',
          fontSize: 12,
          fontFamily: 'DINPro-Regular'
        },
        // splitLine: {
        //   show: false
        // }
      }
    ],
    yAxis: [
      {
        type: 'category',
        inverse: true,
        position: 'right',
        axisLine: {
          show: true,
          lineStyle: {
            color: '#666'
          }
        },
        axisTick: {
          show: false
        },
        axisLabel: {
          show: false
        },
        data: xData
      },
      {
        gridIndex: 1,
        type: 'category',
        inverse: true,
        position: 'left',
        axisLine: {
          show: false
        },
        axisTick: {
          show: false
        },
        axisLabel: {
          show: true,
          // padding:[-5,0,20,0],
          textStyle: {
            color: '#666',
            fontSize: 14,
            fontFamily: 'pmzd_title'
          },
          align: 'center'
        },
        data: xData.map(function (value) {
          return {
            value: value,
            textStyle: {
              align: 'center'
            }
          }
        })
      },
      {
        gridIndex: 2,
        type: 'category',
        inverse: true,
        position: 'left',
        axisLine: {
          show: true,
          lineStyle: {
            color: lineColor
          }
        },
        axisTick: {
          show: false
        },
        axisLabel: {
          show: false
        },
        data: xData
      }
    ],
    series: []
  },
  options: []
}
chatOptionsOriginB.baseOption.timeline.data.push(timeLineData[0])
chatOptionsOriginB.options.push({
  series: [
    {
      name: '充电量(度)',
      type: 'bar',
      itemStyle: {
        normal: {
          label: {
            show: true, //开启显示
            position: 'left', //在上方显示
            textStyle: {
              //数值样式
              color: colors[0].borderColor,
              fontSize: 12
            }
          },
          color: "#F2F2F2",
          // color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
          //   {
          //     offset: 0,
          //     color: colors[0].start
          //   },
          //   {
          //     offset: 1,
          //     color: colors[0].end
          //   }
          // ]),
          // borderColor: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
          //   {
          //     offset: 0,
          //     color: colors[0].borderColor
          //   },
          //   {
          //     offset: 1,
          //     color: colors[0].end
          //   }
          // ]),
          borderWidth: 1
        }
      },
      data: lastYearData,
      animationEasing: 'elasticOut'
    },
    {
      name: '充电次数(单)',
      type: 'bar',
      // stack: "2",
      // barWidth: 25,
      xAxisIndex: 2,
      yAxisIndex: 2,
      itemStyle: {
        normal: {
          label: {
            show: true, //开启显示
            position: 'right', //在上方显示
            textStyle: {
              //数值样式
              color: colors[1].borderColor,
              fontSize: 12
            }
          },
          color: "#D7D7D7",
          // color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
          //   {
          //     offset: 0,
          //     color: colors[1].start
          //   },
          //   {
          //     offset: 1,
          //     color: colors[1].end
          //   }
          // ]),
          // borderColor: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
          //   {
          //     offset: 0,
          //     color: colors[1].start
          //   },
          //   {
          //     offset: 1,
          //     color: colors[1].borderColor
          //   }
          // ]),
          borderWidth: 1
        }
      },
      data: thisYearData,
      animationEasing: 'elasticOut'
    }
  ]
})

export const chatOptionsOriginBa = {
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'shadow'
    }
  },
  legend: {
    data: ['充电量', '充电次数(单)']
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: [
    {
      type: 'value'
    }
  ],
  yAxis: [
    {
      type: 'category',
      axisTick: {
        show: false
      },
      data: ['尖', '谷', '平', '峰']
    }
  ],
  series: [
    {
      name: '充电量',
      type: 'bar',
      stack: 'Total',
      label: {
        show: true
      },
      emphasis: {
        focus: 'series'
      },
      data: [320, 302, 341, 3274]
    },
    {
      name: '充电次数(单)',
      type: 'bar',
      stack: 'Total',
      label: {
        show: true,
        position: 'left'
      },
      emphasis: {
        focus: 'series'
      },
      data: [-120, -132, -101, -134, -190, -230, -210]
    }
  ]
}
