package com.liujun.utils;/*
Author Liujun
Date 2020-02-09
Time 23:05
Minute 05
*/

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Element;

import java.io.FileOutputStream;
import java.io.IOException;

public  class ApiServiceUtils {
    private static PoolingHttpClientConnectionManager cm;

    static {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(20);
        cm.setDefaultMaxPerRoute(200);
    }



    public static String isNull(Element element, int isHtml, String attrParam) {

        if (element == null){
            return null;
        }
        if (isHtml == 0){
            return element.html().trim();
        }else if(attrParam == null) {
            return element.attr("title").trim();
        }
        return element.attr(attrParam).trim();


    }

    public static String isNull(Element element,int isHtml){
        return isNull(element,isHtml,null);
    }

    public static String getHtml(String url) {

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        HttpGet httpGet = new HttpGet(url);

        //设置连接超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).build();//设置请求和传输超时时间
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse response  =  null;
        try{
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200){
                if (response.getEntity() != null){
                    String html = EntityUtils.toString(response.getEntity(), "utf-8");
                    return html;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }



    public static String getImage(String url,String name) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
      /*              String extName = StringUtils.substringAfterLast(url, ".");
                   // String fileName = UUID.randomUUID().toString() + "." + extName;
                    String fileName = name + "." + extName;*/
                    FileOutputStream fos = new FileOutputStream("/Users/mac/Documents/王者荣耀/" + name+".mp4");
                    response.getEntity().writeTo(fos);
                    return "";
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
}
