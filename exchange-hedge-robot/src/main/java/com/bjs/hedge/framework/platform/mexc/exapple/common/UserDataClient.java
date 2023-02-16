package com.bjs.hedge.framework.platform.mexc.exapple.common;

import com.bjs.hedge.util.PropertiesUtil;
import com.bjs.hedge.util.SSLSocketClient;
import com.bjs.hedge.util.spring.SpringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class UserDataClient {
    private static final String REQUEST_HOST = "https://api.mexc.com";

    private static final OkHttpClient OK_HTTP_CLIENT = createOkHttpClient();

//    private static final String accessKey = "mx0GQgskFnvKXfvaDU";
//    private static final String secretKey = "44d9d51b5da345ac97eb13b92a9c9e30";

    /**
     *
     */
//    private static final String accessKey = "mx0VhMytymDNCB8kyu";
//    private static final String secretKey = "e4999c78168048af929939f7e7b719b2";

    public static String accessKey;
    public static String secretKey;


    static {
        initAccessKey();
    }

    private static void initAccessKey() {
        String active = SpringUtils.getActiveProfile();
        String envProperties;
        if (StringUtils.isNotBlank(active) && "prod".equals(active.trim())) {
            envProperties = String.format("application-%s.properties", "prod");
        } else {
            envProperties = String.format("application-%s.properties", "dev");
        }
        try {
            PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();
            propertiesUtil.init(envProperties);
            accessKey = propertiesUtil.getValue("mexc.accessKey");
            secretKey = propertiesUtil.getValue("mexc.secretKey");
            log.info("accessKey:{}", accessKey);
            log.info("secretKey:{}", secretKey);
        } catch (Exception e) {
            log.error("initAccessKey mexc", e);
        }
    }


    private static OkHttpClient createOkHttpClient() {
        OkHttpClient build = null;
        try {
            if (StringUtils.isBlank(secretKey) || StringUtils.isBlank(accessKey)){
                initAccessKey();
            }
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            build = new OkHttpClient.Builder()
                    .connectTimeout(45, TimeUnit.SECONDS)
                    .readTimeout(45, TimeUnit.SECONDS)
                    .writeTimeout(45, TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new SignatureInterceptor(secretKey, accessKey))
                    //                .addInterceptor(new LogInterceptor())
                    //                .addInterceptor(new TokenInterceptor())
                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), (X509TrustManager) SSLSocketClient.getTrustManager()[0])//配置
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return build;
    }


    public static <T> T get(String uri, Map<String, String> params, TypeReference<T> ref) {
        try {
            Response response = OK_HTTP_CLIENT
                    .newCall(new Request.Builder().url(REQUEST_HOST + uri + "?" + SignatureUtil.toQueryString(params)).get().build())
                    .execute();
            return handleResponse(response, ref);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T post(String uri, Map<String, String> params, TypeReference<T> ref) {
        try {
            Response response = OK_HTTP_CLIENT
                    .newCall(new Request.Builder()
                            .url(REQUEST_HOST.concat(uri))
                            .post(RequestBody.create(SignatureUtil.toQueryString(params), MediaType.get("text/plain"))).build()).execute();
            return handleResponse(response, ref);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T delete(String uri, Map<String, String> params, TypeReference<T> ref) {
        try {
            return handleResponse(OK_HTTP_CLIENT
                    .newCall(new Request.Builder()
                            .url(REQUEST_HOST.concat(uri))
                            .delete(RequestBody.create(SignatureUtil.toQueryString(params), MediaType.get("text/plain"))).build()).execute(), ref);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static <T> T handleResponse(Response response, TypeReference<T> ref) throws IOException {
        Gson gson = new Gson();
        assert response.body() != null;
        String content = response.body().string();
        if (200 != response.code()) {
            throw new RuntimeException(content);
        }
        return gson.fromJson(content, ref.getType());
    }


}
