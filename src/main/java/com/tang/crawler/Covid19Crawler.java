package com.tang.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tang.entity.Details;
import com.tang.entity.History;
import com.tang.entity.MostSerious;
import com.tang.entity.Trend;
import com.tang.service.DetailsService;
import com.tang.service.HistoryService;
import com.tang.service.IMostSeriousService;
import com.tang.service.TrendService;
import com.tang.utils.HttpUtils;
import com.tang.utils.TimeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Covid19Crawler {
    @Autowired
    private DetailsService detailsService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TrendService trendService;
    @Autowired
    private IMostSeriousService mostSeriousService;

    // 获取时间
    private static String time = TimeUtils.format(System.currentTimeMillis(),"yy-MM-dd HH:mm:ss");
    // 爬取疫情数据页面
    private static Document doc = Jsoup.parse(HttpUtils.getHtml("https://ncov.dxy.cn/ncovh5/view/pneumonia"));

    @Bean
    @Scheduled(cron = "0 0 12 * * ?")
    public void detailsCrawler(){
        // 1、解析页面中省份的JSON  id = getAreaStat
        String crawler = doc.select("script[id=getAreaStat]").toString();
        // 2、用正则表达式去掉 json前后数据
        String pattern = "\\[(.*)\\]"; // 定义正则规则
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(crawler);
        String json = "";
        if (matcher.find()) json = matcher.group(0);
        // 3、将json数据放入 实体类
        List<Details> details = JSON.parseArray(json, Details.class);
        // 因为网页没有南海诸岛的数据所以需要自己虚拟一个
        Details dt = new Details(null,"南海诸岛",
                0,0,0,0,null,time);
        details.add(dt);
        for (Details detail : details) {
            detail.setTime(time);
            QueryWrapper<Details> Name = new QueryWrapper<>();
            Name.eq("province_short_name",detail.getProvinceShortName());
            detailsService.saveOrUpdate(detail,Name);
        }

        // 获取疫情最严重的省份数据
        String statisticsData = details.get(0).getStatisticsData();
        String mostHtml = HttpUtils.getHtml(statisticsData);
        JSONObject mostJson = JSON.parseObject(mostHtml);
        String data = mostJson.getString("data");
        // 将data放入实体类
        List<MostSerious> mostSerious = JSON.parseArray(data, MostSerious.class);
        QueryWrapper<MostSerious> wrapper = new QueryWrapper<>();
        wrapper.eq("provinces",details.get(0).getProvinceShortName());
        if(mostSeriousService.list(wrapper).size()==0) {
            mostSeriousService.deleteUser(); // 清空数据
        }
        if(mostSerious.size()>mostSeriousService.list().size()){
            for (MostSerious serious : mostSerious) {
                serious.setProvinces(details.get(0).getProvinceShortName());
                QueryWrapper<MostSerious> mostWrapper = new QueryWrapper<>();
                mostWrapper.eq("date_id", serious.getDateId());
                mostSeriousService.saveOrUpdate(serious, mostWrapper);
            }
        }
        System.out.println("DetailsCrawler==>已经修改地图数据以及获取最严重省份数据1");
        this.historyBean();
    }

    public void historyBean(){
        // 1、解析网页中的较昨日json //getListByCountryTypeService2true
        Elements element = doc.select("script[id=getListByCountryTypeService2true]");
        String crawler = element.toString();
        // 2、去掉多余的 用正则表达式
        Matcher matcher = Pattern.compile("\\[(.*)\\]").matcher(crawler);
        String json = "";
        if(matcher.find())
            json = matcher.group(0);
        // 3、将json数据放入 实体类中
        List<History> list = JSON.parseArray(json, History.class);
        for (History bean : list) {
            bean.setTime(time);
            QueryWrapper<History> update = new QueryWrapper<>();
            update.eq("province_name",bean.getProvinceName());
            historyService.saveOrUpdate(bean,update) ;
        }
        System.out.println("HistoryCrawler == >获取世界疫情数据2");
        this.trendCrawler();
    }

    public void trendCrawler() {
        // 1、 爬取json
        QueryWrapper<History> wrapper = new QueryWrapper<>();
        wrapper.eq("province_name", "中国");
        History one = historyService.getOne(wrapper);
        String statisticsData = one.getStatisticsData();
        String html = HttpUtils.getHtml(statisticsData);
        // 2、取出data
        JSONObject json = JSON.parseObject(html);
        String data = json.getString("data");
        // 3、取出data
        List<Trend> list = JSON.parseArray(data, Trend.class);
        // 4、判断数据库是否存在数据
        for (Trend trend : list) {
            if(trendService.getOne(new QueryWrapper<Trend>().eq("date_id",trend.getDateId()))==null){
                trend.setTime(time);
                trendService.save(trend);
            }
        }
        System.out.println("TrendCrawler==》新增数据变化3");
    }

}
