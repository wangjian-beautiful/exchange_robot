package com.jeesuite.admin.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.system.SystemProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    private static PoolingHttpClientConnectionManager sPoolingConnectionManager = null;


    private static RequestConfig sRequestConfig = null;
    private static RequestConfig sLANRequestConfig = null;

    static {
        sPoolingConnectionManager = new PoolingHttpClientConnectionManager();

        sPoolingConnectionManager.setMaxTotal(800);
        sPoolingConnectionManager.setDefaultMaxPerRoute(400);


        sRequestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(60000)
                .setConnectTimeout(40000)
                .setSocketTimeout(40000)
                .build();

        sLANRequestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(30000)
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();

    }

    /**
     * POST请求
     *
     * @param requestUrl    请求地址
     * @param requestParams 请求参数
     * @return 应答字符串
     * @throws IOException
     */
    public static String post(String requestUrl, Map<String, String> requestParams) throws IOException {
        HttpEntity httpEntity = null;
        if (requestParams != null) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
                NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                nameValuePairs.add(nameValuePair);
            }

            httpEntity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
        }

        HttpPost httpPost = new HttpPost(requestUrl);

        if (httpEntity != null) {
            httpPost.setEntity(httpEntity);
        }

        CloseableHttpClient httpClient = HttpClients.custom().build();

        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpResponse = httpClient.execute(httpPost);

            HttpEntity responseHttpEntity = closeableHttpResponse.getEntity();

            return EntityUtils.toString(responseHttpEntity, "UTF-8");
        } finally {
            if (closeableHttpResponse != null) {
                closeableHttpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
    }



    /**
     * Send get request.
     *
     * @param uri   the uri
     * @param isLAN the is lAN
     * @return the string
     */
    public static String sendGetRequest(String uri, boolean isLAN) {
        CloseableHttpClient httpClient = getHttpClient();
        return sendGetRequest(httpClient, uri, isLAN);
    }


    /**
     * Gets http client.
     *
     * @return the http client
     */
    private static CloseableHttpClient getHttpClient() {
        //实例化CloseableHttpClient
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(sPoolingConnectionManager)
                .build();

        return httpClient;
    }




    /**
     * Gets request config.
     *
     * @param isLAN the is lAN
     * @param scheme
     * @return the request config
     */
    private static RequestConfig getsRequestConfig(boolean isLAN, String scheme) {
        boolean enableProxy = checkProxy(scheme );
        if(! enableProxy) {
            if (isLAN) {
                return sLANRequestConfig;
            } else {
                return sRequestConfig;
            }
        }

        HttpHost proxy = getProxy(scheme);

        if (isLAN) {
            return RequestConfig.custom()
                    .setConnectionRequestTimeout(30000)
                    .setConnectTimeout(10000)
                    .setSocketTimeout(10000)
                    .setProxy(proxy)
                    .build();
        } else {
            return RequestConfig.custom()
                    .setConnectionRequestTimeout(60000)
                    .setConnectTimeout(40000)
                    .setSocketTimeout(40000)
                    .setProxy(proxy)
                    .build();
        }
    }

    private static HttpHost getProxy(String scheme) {
        String host = "http".equals(scheme) ? SystemProperties.get("cus.http.proxyHost"): SystemProperties.get("cus.https.proxyHost");
        String portStr = "http".equals(scheme) ? SystemProperties.get("cus.http.proxyPort"): SystemProperties.get("cus.https.proxyPort");
        return new HttpHost(host, Integer.valueOf(portStr));
    }

    private static boolean checkProxy(String scheme) {
        if("http".equals(scheme) ){
            return StringUtils.isNotBlank(SystemProperties.get("cus.http.proxyHost") );
        }

        //https

        return StringUtils.isNotBlank(SystemProperties.get("cus.https.proxyHost") );
    }

    /**
     * Send get request.
     *
     * @param httpClient the http client
     * @param uri        the uri
     * @param isLAN      the is lAN
     * @return the string
     */
    public static String sendGetRequest(CloseableHttpClient httpClient, String uri, boolean isLAN) {

        String respStr = null;

        CloseableHttpResponse response = null;
        InputStream inStream = null;

        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(getsRequestConfig(isLAN,httpGet.getURI().getScheme()));

        //本地测试，需要开启代理
//        httpGet.setConfig(getProxyRequestConfig());

        try {
            response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                inStream = entity.getContent();
                respStr = IOUtils.toString(inStream, "utf-8");
            }

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                }
            }

            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }

        return respStr;
    }


}