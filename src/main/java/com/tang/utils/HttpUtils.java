package com.tang.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
