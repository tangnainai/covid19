var ec_right1 = echarts.init(document.querySelector("#r1"), "dark");
var ec_right1_option = {
    title: {
        text: "全国数据变化",
        textStyle: {
            color: "#000",
        },
        left: "left",
    },
    tooltip: {
        textStyle: {
            color: "#fdfdfd",
        },
        trigger: "axis",
        //指示器
        axisPointer: {
            type: "line",
            lineStyle: {
                color: "#7171C6"
            }
        },
    },
    legend: {
        data: ["确诊变化","死亡变化"],
        left: "right"
    },
    //图形位置
    grid: {
        left: '4%',
        right: '6%',
        bottom: '4%',
        top: 50,
        containLabel: true
    },
    xAxis: [{
        type: "category",
        //x轴坐标点开始与结束点位置都不在最边缘
        data: [] //时间
    }],
    yAxis: [{
        type: 'value',
        //y轴字体设置
        axisLable: {
            show: true,
            color: "white"
        },
        //与x轴平行的线样式
        splitLine: {
            show: true,
            lineStyle: {
                color: '#17273B',
                width: 1,
                type: 'solid',
            }
        }
    }],
    series: [
        {
            data: [],
            name: '确诊变化',
            smooth: true,
            type: 'line',
            itemStyle: {
                color: "#ff02b1"
            }

        },
        {
            data: [],
            name: '死亡变化',
            smooth: true,
            type: 'line',
            symbol: 'none',
            sampling: 'lttb',
            itemStyle: {
                color: 'rgb(205,1,1)'
            },
            areaStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {
                        offset: 0,
                        color: 'rgb(127,0,0)'
                    },
                    {
                        offset: 1,
                        color: 'rgb(59,0,0)'
                    }
                ])
            },
        }
    ]
};
ec_right1.setOption(ec_right1_option);
