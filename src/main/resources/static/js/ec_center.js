//地图
var ec_center2 = echarts.init(document.querySelector("#c2"), "dark");
var ec_center2_option = {
    title:{
        text: "疫 情 地 图",
        textStyle:{
          color:'#000',
          fontSize: 30
        },
        left: "center"
    },
    tooltip: {
        triggerOn: "click",
        formatter: function(e, t, n) {
            return e.data.name + "<br/>"+ e.seriesName+ ":"+ e.data.value +"<br/>" + "累计确诊人数:"+ e.data.count + "<br/>"
                + "累计治愈:"+ e.data.curedCount + "<br/>"+ "累计死亡:" + e.data.deadCount;
        }
    },
    visualMap: {
        min: 0,
        max: 1000,
        left: 26,
        bottom: 40,
        showLabel: !0,
        text: ["高", "低"],
        pieces: [{
            gte: 10000,
            label: ">= 10000",
            color: "#7f1100"
        }, {
            gte: 1000,
            lte: 9999,
            label: "1000 - 9999",
            color: "#d20000"
        },{
            gte: 100,
            lte: 999,
            label: "100 - 999",
            color: "#ff7700"
        },
        {
            gte: 10,
            lte: 99,
            label: "10 - 99",
            color: "#f6b100"
        }, {
            gte: 1,
            lte: 9,
            label: "1 - 9",
            color: "#f8e6a3"
        }, {
            lte: 0,
            label: "0 人",
            color: "#01bf1d"
        }],
        show: !0
    },
    geo: {
        map: "china",
        roam: !1,
        scaleLimit: {
            min: 1,
            max: 2
        },
        zoom: 1.1,
        label: {
            normal: {
                show: !0,
                fontSize: "10",
                color: "rgba(0,0,0,0.7)"
            }
        },
        itemStyle: {
            normal: {
                borderWidth: .5,
                areaColor: "#009fe8",
                borderColor: "#ffefd5"
            },
            emphasis: {
                areaColor: "#fff",
                borderColor: "#4b0082",
                borderWidth: .5
            }
        }
    },
    series: [{
        name: "当前确诊人数",
        count: "累计确诊人数",
        type: "map",
        geoIndex: 0,
        data: []
    }]

};

ec_center2.setOption(ec_center2_option);
