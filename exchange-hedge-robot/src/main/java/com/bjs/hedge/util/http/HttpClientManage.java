package com.bjs.hedge.util.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * HttpClient 管理
 *
 * @author yolo
 */
public class HttpClientManage {
    private Map<String, HttpClient> cacheHttpClientMap = new ConcurrentHashMap<>();

    private HttpClientManage() {
    }

    public static boolean cache(String name, HttpClient httpClient) {
        return SingleInstance.INSTANCE.cacheHttpClientMap.put(name, httpClient) == httpClient;
    }

    public static HttpClient getHttpClient(String name) {
        return SingleInstance.INSTANCE.cacheHttpClientMap.get(name);
    }

    public static HttpClient getHttpClientAndCache(String name) {
        if (SingleInstance.INSTANCE.cacheHttpClientMap.containsKey(name)) {
            return SingleInstance.INSTANCE.cacheHttpClientMap.get(name);
        }
        HttpClient httpClient = createHttpClient(httpClientBuilder -> httpClientBuilder.build());
        cache(name, httpClient);
        return httpClient;
    }

    public static HttpResponse execute(HttpUriRequest request) throws IOException {
        return SingleInstance.defaultHttpClient.execute(request);
    }

    public static CloseableHttpClient defaultHttpClient() {
        return (CloseableHttpClient) SingleInstance.defaultHttpClient;
    }

    public static HttpClient newHttpClient() {
        return createHttpClient((httpClientBuilder -> httpClientBuilder.build()));
    }

    public static HttpClient createHttpClient(Function<HttpClientBuilder, HttpClient> func) {
        return func.apply(HttpClientBuilder.create());
    }

    private static class SingleInstance {
        private static final String HTTP = "http";
        private static final String HTTPS = "https";
        private static HttpClientManage INSTANCE = new HttpClientManage();
        private static HttpClient defaultHttpClient = createHttpClient((httpClientBuilder -> {
            SSLConnectionSocketFactory sslsf = null;
            try {
                sslsf = new SSLConnectionSocketFactory(
                        // 全部信任 不做身份鉴定
                        new SSLContextBuilder().loadTrustMaterial(null, (a, b) -> true).build(),
                        new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"},
                        null,
                        NoopHostnameVerifier.INSTANCE);
            } catch (Exception e) {
            }
            RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
            Registry<ConnectionSocketFactory> registry = registryBuilder
                    .register(HTTP, new PlainConnectionSocketFactory())
                    .register(HTTPS, sslsf)
                    .build();
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                    registry, null, null, null, 10, TimeUnit.MINUTES);
            connManager.setDefaultMaxPerRoute(24);
            connManager.setMaxTotal(2048);
            HttpClient httpClient = httpClientBuilder
                    .disableCookieManagement()
                    .disableRedirectHandling()
                    .setConnectionManager(connManager)
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setConnectionRequestTimeout(3000000)
                            .setConnectTimeout(3000000)
                            .setSocketTimeout(3000000).build())
                    .setSSLSocketFactory(sslsf)
                    .setConnectionManagerShared(true)
                    .build();
            return httpClient;
        }));
    }
}