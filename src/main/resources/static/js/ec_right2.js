var ec_right2 = echarts.init(document.querySelector("#r2"), "dark");
var ec_right2_option = {
    title: {
        text: "严重国家确诊情况",
        textStyle: {
            color: "#000000",
        },
        left: "left"
    },
    //#ff0000
    color: ["#ff0000"],
    tooltip: {
        trigger: "axis",
        axisPointer: {
            type: "shadow"
        }
    },
    xAxis: {
        type: "value"
    },
    yAxis: [{
        data: [],
        type: 'category'
    }],
    series: [{
        data: [],
        type: 'bar',
        barMaxWidth: "50%",
        itemStyle:{
            normal:{
                // 没根柱子的颜色
                color:function (params){
                    let colorList =[
                        "#9a4d4d",
                        "#89ff02",
                        "#021bff",
                        "#c800ff",
                        "#ffb702",
                        "#e57602",
                        "#de0101",
                        "#bd0000",
                        "#840000",
                        "#000000"
                    ];
                    return colorList[params.dataIndex];
                }
            }
        }
    }]
};
ec_right2.setOption(ec_right2_option);
