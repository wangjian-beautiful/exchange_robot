package com.bjs.hedge.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bjs.hedge.util.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * @author yolo
 */
public class HttpClientRequest {

    public static String get(String url, Map<String, Object> param, Map<String, String> headers) throws IOException {
        return successRespToString(request(RequestBuilder.get(url), param, headers));
    }

    public static String post(String url, Map<String, Object> param, Map<String, String> headers) throws IOException {
        return successRespToString(request(RequestBuilder.post(url), param, headers));
    }

    public static String put(String url, Map<String, Object> param, Map<String, String> headers) throws IOException {
        return successRespToString(request(RequestBuilder.put(url), param, headers));
    }

    public static String delete(String url, Map<String, Object> param, Map<String, String> headers) throws IOException {
        return successRespToString(request(RequestBuilder.delete(url), param, headers));
    }

    public static String postApplicationJSON(String url, Map<String, Object> param, Map<String, String> headers) throws IOException {
        RequestBuilder post = RequestBuilder.post(url).setHeader("Content-Type", "application/json;charset=utf-8");
        if (MapUtils.isNotEmpty(param)) {
            post.setEntity(new StringEntity(JSON.toJSONString(param), Charset.defaultCharset()));
        }
        return successRespToString(request(post, null, headers));
    }

    public static String postTripProxy(String url, Map<String, Object> param, Map<String, String> headers) throws IOException {
        return successRespToString(request(tripProxy(RequestBuilder.post(url)), param, headers));
    }

    public static String getTripProxy(String url, Map<String, Object> param, Map<String, String> headers) throws IOException {
        return successRespToString(request(tripProxy(RequestBuilder.get(url)), param, headers));
    }

    public static HttpResponse request(RequestBuilder requestBuilder, Map<String, Object> params, Map<String, String> headers) throws IOException {
        if (MapUtils.isNotEmpty(params)) {
            List<NameValuePair> parameters = new ArrayList<>();
            params.forEach((k, v) -> parameters.add(new BasicNameValuePair(k, String.valueOf(v))));
            HttpEntity paramEntity = new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8);
            requestBuilder.setEntity(paramEntity);
        }
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach(requestBuilder::setHeader);
        }
        return request(requestBuilder, HttpClientManage.defaultHttpClient());
    }

    public static byte[] successRespToByteArray(HttpResponse httpResponse) {
        try {
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toByteArray(httpResponse.getEntity());
            }
        } catch (IOException e) {
        }
        return null;
    }

    public static String successRespToString(HttpResponse httpResponse) {
        try {
            if (null != httpResponse && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (IOException e) {
        }
        return null;
    }

    public static HttpResponse request(RequestBuilder requestBuilder, HttpClient httpClient) throws IOException {
        return httpClient.execute(requestBuilder.build());
    }

    /**
     * 添加猎析代理
     *
     * @param rb
     * @return
     */
    public static RequestBuilder tripProxy(RequestBuilder rb) {
        if (null != rb.getConfig()) {
            RequestConfig config = RequestConfig.copy(rb.getConfig())
                    .setProxy(new HttpHost("adsl.alitrip.com", 80))
                    .build();
            rb.setConfig(config);
        } else {
            rb.setConfig(RequestConfig.custom()
                    .setProxy(new HttpHost("adsl.alitrip.com", 80))
                    .build());
        }
        rb.setHeader("trpr-client-name", "cms-spider");
        return rb;
    }

//    public static String uploadFile(String url, String fileParamName, File file) throws IOException {
//        return uploadFile(url, fileParamName, file, null, null);
//    }

//    public static String uploadFile(String url, String fileParamName, File file, Map<String, Object> params, Map<String, String> headers) throws IOException {
//        RequestBuilder post = RequestBuilder.post(url);
//        HttpEntity fileEntity = MultipartEntityBuilder
//                .create()
//                .addBinaryBody(fileParamName, file, ContentType.TEXT_PLAIN, file.getName())
//                .build();
//        post.setEntity(fileEntity);
//        return successRespToString(request(post, params, headers));
//    }

    public static void downloadFile(String filePath, String url) {
        try {
            HttpResponse resp = HttpClientManage.execute(RequestBuilder.get(url).build());
            if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                File file = new File(filePath);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                byte[] bytes = EntityUtils.toByteArray(resp.getEntity());
                FileOutputStream fos = new FileOutputStream(file);
                try {
                    fos.write(bytes);
                } finally {
                    fos.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Custom custom() {
        return new Custom();
    }

    public static class Custom {
        private HttpClient httpClient = HttpClientManage.defaultHttpClient();
        private RequestConfig requestConfig;
        private RequestBuilder requestBuilder;
        private HttpContext httpContext;
        private String cookieSpec;
        private CookieStore cookieStore;
        private HttpResponse httpResponse;

        public Custom(RequestBuilder builder) {
            this.requestBuilder = builder;
        }

        public Custom() {
        }

        public Custom header(String name, String value) {
            if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(value)) {
                this.requestBuilder.setHeader(name, value);
            }
            return this;
        }

        public Custom headers(Map<String, String> headers) {
            if (MapUtils.isNotEmpty(headers)) {
                headers.forEach(requestBuilder::setHeader);
            }
            return this;
        }

        public Custom params(Map<String, String> params) {
            if (MapUtils.isNotEmpty(params)) {
                List<NameValuePair> parameters = new ArrayList<>();
                params.forEach((k, v) -> parameters.add(new BasicNameValuePair(k, v)));
                HttpEntity paramEntity = new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8);
                requestBuilder.setEntity(paramEntity);
            }
            return this;
        }

        public Custom jsonParam(String params) {
            try {
                requestBuilder.setEntity(new StringEntity(params));
                this.header("Content-Type", "application/json");
            } catch (UnsupportedEncodingException e) {
            }
            return this;
        }

        public Custom cookieStore(CookieStore cookieStore) {
            StringJoiner sb = new StringJoiner(";");
            cookieStore.getCookies().forEach(cookie -> sb.add(cookie.getName() + "=" + cookie.getValue()));
            requestBuilder.setHeader("Cookie", sb.toString());
            return this;
        }

        public Custom addCookie(CookieStore cookieStore) {
            StringJoiner sb = new StringJoiner(";");
            cookieStore.getCookies().forEach(cookie -> sb.add(cookie.getName() + "=" + cookie.getValue()));
            requestBuilder.setHeader("Cookie", sb.toString());
            return this;
        }

        public Custom httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Custom get(String url) {
            this.requestBuilder = RequestBuilder.get(url);
            return this;
        }

        public Custom post(String url) {
            this.requestBuilder = RequestBuilder.post(url);
            return this;
        }

        public Custom request() {
            if (null != this.httpResponse) {
                return this;
            }
            try {
                this.httpResponse = this.httpClient.execute(this.requestBuilder.build(), this.httpContext);
            } catch (IOException e) {
            }
            return this;
        }

        public boolean download(String filePath) {
            try {
                this.request();
                if (this.httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    File file = new File(filePath);
                    if (!file.exists()) {
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                        file.createNewFile();
                    }
                    byte[] bytes = EntityUtils.toByteArray(this.httpResponse.getEntity());
                    FileOutputStream fos = new FileOutputStream(file);
                    try {
                        fos.write(bytes);
                    } finally {
                        fos.close();
                    }
                    return true;
                }
            } catch (Exception e) {
            }
            return false;
        }


        public byte[] toByteArray() {
            try {
                this.request();
                if (this.httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return EntityUtils.toByteArray(this.httpResponse.getEntity());
                }
            } catch (IOException e) {
            }
            return null;
        }

        public String toRespString() {
            try {
                this.request();
                if (this.httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return EntityUtils.toString(this.httpResponse.getEntity());
                }
            } catch (IOException e) {
            }
            return null;
        }

        public JSONObject toJSONObject() {
            try {
                this.request();
                if (this.httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return JSON.parseObject(EntityUtils.toString(this.httpResponse.getEntity()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void main(String[] args)throws Exception {
     final String accessKey = "mx0GQgskFnvKXfvaDU";
        String secretKey = "44d9d51b5da345ac97eb13b92a9c9e30";
        Map<String, Object> params = new HashMap<>();
        //symbol=AEUSDT&side=SELL&type=LIMIT&timeInForce=GTC&quantity=1&price=20
        params.put("symbol", "BTCUSDT");
        params.put("side", "SELL");
        params.put("type", "LIMIT");
        params.put("quantity", "1");
        params.put("price", "100000");
        params.put("recvWindow", "60000");
        String url = "https://api.mexc.com/api/v3/order/test";
        String HEADER_ACCESS_KEY = "X-MEXC-APIKEY";
        HashMap<String, String> headers = new HashMap<>();
//        String signature = SignatureUtil.actualSignature(params, secretKey);
//        params += "&signature=" + signature;
//        String post = post(url, params, headers);
        String s = get("https://api.mexc.com/api/v3/ping", null, null);
        System.out.println(s);

    }
}






