package com.cloud.notification.utils;

import com.cloud.notification.castlers.message.entity.YesStyleRemote;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

public class HttpClientRemoteUtil {

    private static String api_key = "3ac19edc-1e70-45ce-86dd-63aa80db4de5";
    private final String post = "POST";
    private final String get = "GET";

    public static YesStyleRemote SendRemotePost(Map<String, String> header, Map<String, String> form, String URL) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(URL);

        if (!CollectionUtils.isEmpty(header)) {
            Set<String> keySet = header.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = header.get(key);
                httpPost.addHeader(key, value);
            }
        }


        List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
        if (!CollectionUtils.isEmpty(form)) {
            Set<String> keySet = form.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = form.get(key);
                parameters.add(new BasicNameValuePair(key, value));
            }
        }

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);


        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            YesStyleRemote yesStyleRemote = new YesStyleRemote();
            // 判断返回状态是否为200
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            yesStyleRemote.setUrl(URL);
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应体
                System.out.println(content);
                yesStyleRemote.setMessage(content.length()>5000?content.substring(0,4999):content);
                yesStyleRemote.setStatusCode(response.getStatusLine().getStatusCode());
                yesStyleRemote.setType(0);
                yesStyleRemote.setDescription("调用 YesStyle  成功");

            }else {
                yesStyleRemote.setMessage(content.length()>5000?content.substring(0,4999):content);
                yesStyleRemote.setStatusCode(response.getStatusLine().getStatusCode());
                yesStyleRemote.setType(1);
                yesStyleRemote.setDescription("调用 YesStyle  失败");
            }
            return yesStyleRemote;
        } finally {
            if (response != null) {
                response.close();
            }
            // 关闭浏览器
            httpClient.close();
        }
    }


    public static void main(String[] args) throws IOException {
        String URL = "https://t7le55wjdc.execute-api.ap-east-1.amazonaws.com/1688-api-callback";

        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/x-www-form-urlencoded");

        Map<String, String> form = new HashMap<String, String>();
        form.put("'message"," {\n" +
                " \"data\": {\n" +
                " \"currentStatus\": \"FINISH\",\n" +
                " \"lastStatus\": \"FUND_PROCESSING\",\n" +
                " \"orderChangeTime\": \"2015-06-24 19:33:26\",\n" +
                " \"orderId\": \"60020931694988\"\n" +
                " },\n" +
                " \"userInfo\": \"cn1803950\"，\n" +
                " \"type\": \" ORDER_STATUS_CHANGE \", }");
        form.put("api-key","3ac19edc-1e70-45ce-86dd-63aa80db4de5");
        SendRemotePost(header, form,URL);
    }

}
