var ec_left1 = echarts.init(document.querySelector("#l1"), "dark");
var ec_left1_option = {
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
        data: ["累计确诊","现存确诊", "累计治愈", "累计死亡"],
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
    series: [{
        name: "累计确诊",
        type: "line",
        smooth: true,
        data: []
    }, {
        name: "现存确诊",
        type: 'line',
        smooth: true,
        data: []
    },
        {
            name: "累计治愈",
            type: 'line',
            smooth: true,
            data: []
        }, {
            name: "累计死亡",
            type: "line",
            smooth: true,
            data: []

        }
    ]
}

ec_left1.setOption(ec_left1_option);
