# 疫情数据爬取
- 部署到了服务器上：http://120.24.229.94:8888/
7月7号前应该都能访问到, 服务器7月7号过期>_<!

  
  
  

# 一、环境部署
- SpringBoot 2.6.7
- Mybatis-plus 3.5.1
- mysql
- jsoup 
- HttpClient
- ajax
- echarts
## 1、数据库搭建
```sql
CREATE TABLE `details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` varchar(20) NOT NULL COMMENT '收集日期',
  `province_short_name` varchar(20) NOT NULL COMMENT '省份名',
  `current_confirmed_count` int(10) DEFAULT '0' COMMENT '现有确诊',
  `confirmed_count` int(10) DEFAULT NULL COMMENT '累计确诊',
  `dead_count` int(10) DEFAULT NULL COMMENT '累计死亡',
  `cured_count` int(10) DEFAULT NULL COMMENT '累计自愈',
  `statistics_data` varchar(255) DEFAULT NULL COMMENT '以往数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=246 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `history_bean` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `time` varchar(20) DEFAULT NULL COMMENT '日期',
    `current_confirmed_count` int(11) DEFAULT NULL COMMENT '累计确诊',
    `confirmed_count` int(11) DEFAULT NULL COMMENT '现存确诊',
    `cured_count` int(11) DEFAULT NULL COMMENT '累计治愈',
    `dead_count` int(11) DEFAULT NULL COMMENT '累计死亡',
    `province_name` varchar(10) DEFAULT NULL COMMENT '国家',
    `statistics_data` varchar(100) DEFAULT NULL COMMENT '数据增长链接',
    `incr_vo` varchar(200) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16652935 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `most_serious` (
    `date_id` int(11) NOT NULL COMMENT '日期',
    `confirmed_incr` int(10) DEFAULT NULL COMMENT '确诊',
    `dead_incr` int(10) DEFAULT NULL COMMENT '死亡',
    `provinces` varchar(20) DEFAULT NULL COMMENT '省份',
    PRIMARY KEY (`date_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `trend` (
     `date_id` int(15) NOT NULL DEFAULT '0' COMMENT '时间值',
     `confirmed_count` int(10) DEFAULT '0' COMMENT '累计确诊',
     `cured_count` int(10) DEFAULT '0' COMMENT '累计治愈',
     `current_confirmed_count` int(10) DEFAULT '0' COMMENT '现存确诊',
     `dead_count` int(10) DEFAULT '0' COMMENT '累计死亡',
     `cured_incr` int(10) DEFAULT NULL COMMENT '新增治愈',
     `dead_incr` int(10) DEFAULT NULL COMMENT '新增死亡',
     `confirmed_incr` int(10) DEFAULT NULL COMMENT '新增确诊',
     `current_confirmed_incr` int(10) DEFAULT NULL COMMENT '病例变化',
     `time` varchar(20) CHARACTER SET latin1 DEFAULT NULL COMMENT '数据插入时间',
     PRIMARY KEY (`date_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
## 2、pom依赖
```xml
 <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
        <!-- 代码生成器-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <version>3.5.2</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.velocity/velocity -->
        <dependency>
            <groupId>org.apache.velocity</groupId>
            <artifactId>velocity</artifactId>
            <version>1.7</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.75</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient -->
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.13</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.jsoup/jsoup -->
        <dependency>
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>1.14.3</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.commons/commons-lang3 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.12.0</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <!-- https://mvnrepository.com/artifact/commons-io/commons-io -->
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.11.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12 -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.30</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>3.9.1</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>com.huaban</groupId>
            <artifactId>jieba-analysis</artifactId>
            <version>1.0.2</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.5.1</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>us.codecraft</groupId>
            <artifactId>webmagic-core</artifactId>
            <version>0.7.3</version>
        </dependency>
        <dependency>
            <groupId>us.codecraft</groupId>
            <artifactId>webmagic-extension</artifactId>
            <version>0.7.3</version>
        </dependency>
    </dependencies>
```
## 3、application.yml 配置文件
```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/epidemic?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8
    username: root
    password: 123456
  mvc:
    hiddenmethod:
      filter:
        enabled: true
  thymeleaf:
    cache: false

mybatis-plus:
  type-aliases-package: com.tang.entity
  mapper-locations: classpath:/mapper/*.xml
  global-config:
    db-config:
      id-type: auto
server:
  port: 8888
```
## 4、创建实体类
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Details {
    private Integer id;
    @TableField(value = "province_short_name")
    private String provinceShortName; // 省份短名称
    @TableField(value = "current_confirmed_count")
    private Integer currentConfirmedCount; // 现有确诊
    private Integer confirmedCount; // 累计确诊
    private Integer deadCount; // 累计死亡
    private Integer curedCount; // 累计治愈
    private String statisticsData; // 以往数据
    private String time; // 数据获取时间
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("history_bean")
public class History {
    private Integer id;
    private String time; // 数据获取时间
    @TableField("current_confirmed_count")
    private Integer currentConfirmedCount;// 确诊人数
    private Integer confirmedCount; // 现存确诊
    private Integer curedCount; // 已治愈
    private Integer deadCount; // 死亡人数
    private String provinceName; // 国家
    private String incrVo; // 昨天数据
    private String statisticsData;// 链接
}
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("most_serious")
@ApiModel(value = "MostSerious对象", description = "")
public class MostSerious implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("日期")
    @TableId
    private Integer dateId;
    @ApiModelProperty("确诊")
    private Integer confirmedIncr;
    @ApiModelProperty("死亡")
    private Integer deadIncr;
    @ApiModelProperty("省份")
    private String provinces;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trend {
    @TableId  // 主键
    private Integer dateId; // 时间
    private Integer confirmedCount; // 累计确诊
    private Integer curedCount; // 累计治愈
    @TableField("current_confirmed_count")
    private Integer currentConfirmedCount; // 现存确诊
    private Integer deadCount; // 死亡
    private Integer confirmedIncr; // 新增确诊
    private Integer curedIncr; // 新增治愈
    @TableField("current_confirmed_incr")
    private Integer currentConfirmedIncr; // 确诊变化
    private Integer deadIncr; // 新增死亡
    private String time;
}
```

# 二、将常用方法封装
## 1、HttpUtils
```java
public abstract class HttpUtils {
    private static RequestConfig config = null;
    private static List<String> userAgentList = null;
    private static PoolingHttpClientConnectionManager cm = null;

    static {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
        config = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .build();
        userAgentList = new ArrayList<>();
        userAgentList.add("Mozilla/5.0 (Macintosh; Intel Mac OS x 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132");
        userAgentList.add("Mozilla/5.0 (Macintosh; Intel Mac OS x 10_15; rv:73.0) Gecko/20100101 Firefox/73.0");
        userAgentList.add("Mozilla/5.0 (Macintosh; Intel Mac OS x 10_15_3) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.5 Safari/605.1.15");
        userAgentList.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.329.110 Safari/537.36 Edge/16.16299");
        userAgentList.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        userAgentList.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0 ");
    }

    public static String getHtml(String url){
        // 1、从连接池中获取参数对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        // 2、创建Httpget 连接
        HttpGet httpGet = new HttpGet(url);
        // 3、设置请求对象和请求头
        httpGet.setConfig(config);
        httpGet.setHeader("User-Agent",userAgentList.get(new Random().nextInt(userAgentList.size())));
        // 4、发起请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            // 5、获取响应内容
            if(response.getStatusLine().getStatusCode() == 200){
               String html = "";
                if(response.getEntity()!=null){
                    html = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
                return html;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
//                httpClient.close(); 从连接池中获取不要关闭
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
```
## 2、TimeUtils
```java
/**
 * 时间工具类
 */
public abstract class TimeUtils {
    public static String format(Long timestamp,String pattern){
        return FastDateFormat.getInstance(pattern).format(timestamp);
    }
}
```
## 3、代码生成器
```java
public class GeneratorUtils {
    public static void main(String[] args) {
            generator();
    }
    public static void generator(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/epidemic?useSSL=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8",
                "root", "123456")
                .globalConfig(builder -> {
                    builder.author("tang") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\covid19\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.tang") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapper.xml, "C:\\covid19\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.controllerBuilder().enableHyphenStyle()
                            .enableRestStyle(); // 开启生成@RestController 控制器
                    builder.addInclude("most_serious") // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
```
# 三、Mybatis-plus的使用
- 以Details实体类为例
```java
@Mapper
public interface DetailsMapper extends BaseMapper<Details> {
}


public interface DetailsService extends IService<Details> {
}
@Service
public class DetailsServiceImpl extends ServiceImpl<DetailsMapper, Details> implements DetailsService {
}
```
## Mybatis-plus page 配置文件 
```java
public class PageConfig {
    /**
     * 分页拦截器
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
```
# 四、数据爬取
- 在启动类添加 @EnableScheduling
- @Scheduled(cron = "0 0 12/23 * * ?") // 每天中午12点执行
```java
@Slf4j
@Component
public class Covid19Crawler {
    @Resource
    private DetailsService detailsService;
    @Resource
    private HistoryService historyService;
    @Resource
    private TrendService trendService;
    @Resource
    private IMostSeriousService mostSeriousService;

    @Bean
//    @Scheduled(cron = "0 0 12/23 * * ?") // 每天12点执行
    @Scheduled(cron = "0 0/1 * * * ? ")  // 每分钟执行一次
    public void detailsCrawler(){
        String time = TimeUtils.format(System.currentTimeMillis(),"yy-MM-dd HH:mm:ss");
        Document doc = Jsoup.parse(HttpUtils.getHtml("https://ncov.dxy.cn/ncovh5/view/pneumonia"));
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
        log.info("detailsCrawler执行完毕");
        this.historyBean();
    }

    public void historyBean(){
        String time = TimeUtils.format(System.currentTimeMillis(),"yy-MM-dd HH:mm:ss");
        Document doc = Jsoup.parse(HttpUtils.getHtml("https://ncov.dxy.cn/ncovh5/view/pneumonia"));
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
        log.info("historyBean执行完毕");
        this.trendCrawler();
    }

    public void trendCrawler() {
        String time = TimeUtils.format(System.currentTimeMillis(),"yy-MM-dd HH:mm:ss");
        Document doc = Jsoup.parse(HttpUtils.getHtml("https://ncov.dxy.cn/ncovh5/view/pneumonia"));
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
        log.info("trendCrawler执行完毕");
    }

}
```
# 五、数据传输到前端
```java
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
```
# 六、前端用ajax 调用接口
- controller.js
```js
function getTime() {
    $.ajax({
        type: "post",
        url:"/time",
        timeout: 10000,
        success:function(data){
            $("#time").html(data)
        },
        error:function (xhr,type,errorThrown) {
        }
    });

}

function get_c1_data(){
    $.ajax({
        type: "post",
        url: "/c1",
        success:function (data) {
            $(".num h1").eq(0).text(data.confirm);
            $(".num h1").eq(1).text(data.nowConfirm);
            $(".num h1").eq(2).text(data.heal);
            $(".num h1").eq(3).text(data.dead);

            $(".count h4").eq(0).text("较昨日:"+data.currentConfirmedIncr);
            $(".count h4").eq(1).text("较昨日:"+data.confirmedIncr);
            $(".count h4").eq(2).text("较昨日:"+data.curedIncr);
            $(".count h4").eq(3).text("较昨日:"+data.deadIncr);
        }
    });
}

function get_c2_data() {
    $.ajax({
        type: "post",
        url: "/c2",
        success: function (response) {
            ec_center2_option.series[0].data=response;
            // console.log(response);
           // console.log(ec_center2_option.series[0].data);
            ec_center2.setOption(ec_center2_option);
        }
    });
}

function get_r1_data() {
    $.ajax({
        type: "post",
        url: "/r1",
        success: function (response) {
            ec_right1_option.series[0].data = response.confirmedIncr
            ec_right1_option.series[1].data = response.deadIncr
            ec_right1_option.xAxis[0].data = response.dateId
            ec_right1_option.title.text = '疫情最严重省份:'+response.provinces
            ec_right1.setOption(ec_right1_option);
        }
    });
}

function get_r2_data() {
    $.ajax({
        type: "post",
        url: "/r2",
        success: function (response) {
            ec_right2_option.yAxis[0].data=response.provinceName;
            ec_right2_option.series[0].data=response.confirmedCount;
            ec_right2.setOption(ec_right2_option);
        }
    });
}
function get_l1_data() {
    $.ajax({
        type: "post",
        url: "/l1",
        success: function (response) {
            // console.log(response);
            ec_left1_option.xAxis[0].data=response.dateId;
            ec_left1_option.series[0].data=response.confirmedCount;
            ec_left1_option.series[1].data=response.currentConfirmedCount;
            ec_left1_option.series[2].data=response.curedCount;
            ec_left1_option.series[3].data=response.deadCount;
            ec_left1.setOption(ec_left1_option);
        }
    });
}
function get_l2_data() {
    $.ajax({
        type: "post",
        url: "/l2",
        success: function (response) {
            //  console.log(response);
            ec_left2_option.xAxis[0].data=response.dateId;
            ec_left2_option.series[0].data=response.confirmedIncr;
            ec_left2_option.series[1].data=response.currentConfirmedIncr;
            ec_left2_option.series[2].data=response.curedIncr;
            ec_left2_option.series[3].data=response.deadIncr;
            ec_left2.setOption(ec_left2_option);
        }
    });
}
setInterval(getTime,1000);
setInterval(get_c1_data,1000);
get_l2_data();
get_l1_data();
get_c2_data();
get_r1_data();
get_r2_data();
```
# 七、中国地图的绘制
- echarts
> echarts.apache.org/zh/index.html 官网地址
1. 先下载中国地图脚本 china.js
2. 导入echarts.min.j
3. 使用echarts绘制点击动作颜色等等，ec_center.js
```js
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
```
