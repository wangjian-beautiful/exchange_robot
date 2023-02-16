package com.bjs.hedge.framework.platform.mexc.exapple.common;

import com.bjs.hedge.util.SSLSocketClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MarketDataClient {

    private static final String REQUEST_HOST = "https://api.mexc.com";
    private static final OkHttpClient OK_HTTP_CLIENT = createOkHttpClient();

    private static OkHttpClient createOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
//                .addInterceptor(httpLoggingInterceptor)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(),(X509TrustManager) SSLSocketClient.getTrustManager()[0])//配置
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置
                .build();
    }


    public static <T> T get(String uri, Map<String, String> params, TypeReference<T> ref) {
        try {
            Request.Builder builder = new Request.Builder().url(REQUEST_HOST + uri + "?" + SignatureUtil.toQueryString(params)).get();
            Response response = OK_HTTP_CLIENT.newCall(builder.build()).execute();
            Gson gson = new Gson();
            assert response.body() != null;
            String content = response.body().string();
            return gson.fromJson(content, ref.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
