package com.tang.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tang.entity.Details;
import com.tang.entity.History;
import com.tang.entity.MostSerious;
import com.tang.entity.Trend;
import com.tang.service.DetailsService;
import com.tang.service.HistoryService;
import com.tang.service.IMostSeriousService;
import com.tang.service.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.WrappedPlainView;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 疫情数据
 * @author tang
 * @date 2022/6/4 14:02
 * @desc
 */
@RestController
public class Covid19Controller {
    @Autowired
    private DetailsService detailsService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TrendService trendService;
    @Autowired
    private IMostSeriousService mostSeriousService;

    /**
     * 右上角时间
     * @return
     */
    @PostMapping("/time")
    public String time(){
        String date = DateFormat.getDateTimeInstance().format(new Date());
        return date;
    }
    /**
     * 左上边折线图
     * @return
     */
    @PostMapping("/l1")
    public JSONObject left1(){
        JSONObject json = new JSONObject();
        IPage<Trend> page = new Page<>(1, 30);
        QueryWrapper<Trend> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("date_id");
        IPage<Trend> list = trendService.page(page, wrapper);

        List<Integer> dateId = new ArrayList<>();
        List<Integer> deadCount = new ArrayList<>();
        List<Integer> currentConfirmedCount = new ArrayList<>();
        List<Integer> confirmedCount = new ArrayList<>();
        List<Integer> curedCount = new ArrayList<>();

        for (int i = list.getRecords().size() - 1; i >= list.getRecords().size()-30; i--) {
            dateId.add(list.getRecords().get(i).getDateId());
            deadCount.add(list.getRecords().get(i).getDeadCount());
            currentConfirmedCount.add(list.getRecords().get(i).getCurrentConfirmedCount());
            confirmedCount.add(list.getRecords().get(i).getConfirmedCount());
            curedCount.add(list.getRecords().get(i).getCuredCount());
        }

        json.put("dateId",dateId);
        json.put("deadCount",deadCount);
        json.put("currentConfirmedCount",currentConfirmedCount);
        json.put("confirmedCount",confirmedCount);
        json.put("curedCount",curedCount);

//        System.err.println("================"+dateId.size());
//        System.out.println(json);
        return json;
    }

    /**
     * 左下折线图
     * @return
     */
    @PostMapping("/l2")
    public JSONObject left2(){
        JSONObject json = new JSONObject();
        IPage<Trend> page = new Page<>(1,30);
        QueryWrapper<Trend> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("date_id");
        IPage<Trend> list = trendService.page(page, wrapper);

        List<Integer> dateId =  new ArrayList<>();
        List<Integer> confirmedIncr = new ArrayList<>();
        List<Integer> curedIncr =  new ArrayList<>();
        List<Integer> currentConfirmedIncr =new ArrayList<>();
        List<Integer> deadIncr =new ArrayList<>();

        for (int i = list.getRecords().size() - 1; i >= list.getRecords().size()-30; i--) {
            dateId.add(list.getRecords().get(i).getDateId());
            confirmedIncr.add(list.getRecords().get(i).getConfirmedIncr());
            curedIncr.add(list.getRecords().get(i).getCuredIncr());
            currentConfirmedIncr.add(list.getRecords().get(i).getCurrentConfirmedIncr());
            deadIncr.add(list.getRecords().get(i).getDeadIncr());
        }

        json.put("dateId",dateId);
        json.put("confirmedIncr",confirmedIncr);
        json.put("curedIncr",curedIncr);
        json.put("currentConfirmedIncr",currentConfirmedIncr);
        json.put("deadIncr",deadIncr);
        return json;
    }

    /**
     * 中间数据
     * @return
     */
    @PostMapping("/c1")
    public JSONObject history(){
        QueryWrapper<History> wrapper = new QueryWrapper<>();
        wrapper.eq("province_name","中国");
        History today = historyService.getOne(wrapper);
        String incrVo = today.getIncrVo();
        // 将字符串转换为json数据
        JSONObject ivJson = JSON.parseObject(incrVo);
        Integer currentConfirmedIncr = (Integer) ivJson.get("currentConfirmedIncr");
        Integer confirmedIncr = (Integer) ivJson.get("confirmedIncr");
        Integer curedIncr = (Integer) ivJson.get("curedIncr");
        Integer deadIncr = (Integer) ivJson.get("deadIncr");

        JSONObject jsonObject = new JSONObject();
        // 实时数据
        jsonObject.put("dead",today.getDeadCount());
        jsonObject.put("confirm",today.getCurrentConfirmedCount());
        jsonObject.put("heal",today.getCuredCount());
        jsonObject.put("nowConfirm",today.getConfirmedCount());
        // 较昨日数据
        jsonObject.put("currentConfirmedIncr",currentConfirmedIncr);
        jsonObject.put("confirmedIncr",confirmedIncr);
        jsonObject.put("curedIncr",curedIncr);
        jsonObject.put("deadIncr",deadIncr);
        return jsonObject;
    }

    /**
     * 中国地图
     * @return
     */
    @PostMapping("/c2")
    public JSONArray detailsJson(){
        JSONArray json = new JSONArray();
        List<Details> list = detailsService.list();
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",list.get(i).getProvinceShortName());
            jsonObject.put("value",list.get(i).getCurrentConfirmedCount());
            jsonObject.put("count",list.get(i).getConfirmedCount());
            jsonObject.put("deadCount",list.get(i).getDeadCount());
            jsonObject.put("curedCount",list.get(i).getCuredCount());
            json.add(jsonObject);
        }
        return json;
    }

    /**
     * 右上图
     * @return
     */
    @PostMapping("/r1")
    public JSONObject ecRight1(){
        JSONObject json = new JSONObject();
        IPage<MostSerious> page = new Page<>(1,30);
        QueryWrapper<MostSerious> wrapper = new QueryWrapper<MostSerious>().orderByDesc("date_id");
        page = mostSeriousService.page(page,wrapper);

        List<Integer> confirmedIncr = new ArrayList<>();
        List<Integer> deadIncr = new ArrayList<>();
        List<Integer> dateId = new ArrayList<>();
        String provinces = detailsService.list().get(0).getProvinceShortName();

        for (int i = page.getRecords().size()-1; i >= 0; i--) {
            confirmedIncr.add(page.getRecords().get(i).getConfirmedIncr());
            deadIncr.add(page.getRecords().get(i).getDeadIncr());
            dateId.add(page.getRecords().get(i).getDateId());
        }

        json.put("confirmedIncr",confirmedIncr);
        json.put("deadIncr",deadIncr);
        json.put("provinces",provinces);
        json.put("dateId",dateId);

        return json;
    }

    /**
     * 右下
     * @return
     */
    @PostMapping("/r2")
    public JSONObject world(){
        QueryWrapper<History> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("confirmed_count");
        IPage<History> page = new Page<>(1, 10);
        IPage<History> list = historyService.page(page, wrapper);

        JSONObject json = new JSONObject();
        List<Integer> confirmedCount = new ArrayList<>();
        List<String> provinceName = new ArrayList<>();
        for (int i = list.getRecords().size() - 1; i >= 0; i--) {
            confirmedCount.add(list.getRecords().get(i).getConfirmedCount());
            provinceName.add(list.getRecords().get(i).getProvinceName());
        }

        json.put("confirmedCount",confirmedCount);
        json.put("provinceName",provinceName);
        return json;
    }
}
