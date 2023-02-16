package com.jeesuite.admin.util;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Domon.Xie on 2017/8/1.
 */
public class HttpMessageConverterUtil {

    public static List<HttpMessageConverter<?>> createHttpMessageConverters() {
        List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<>(2);
        // Fastjson 转换器
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter4 = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter4.setCharset(Charset.defaultCharset());

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        fastJsonHttpMessageConverter4.setSupportedMediaTypes(supportedMediaTypes);

        fastJsonHttpMessageConverter4.setFeatures(
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteSlashAsSpecial,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullListAsEmpty);
        httpMessageConverters.add(fastJsonHttpMessageConverter4);
        // String 转换器
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
//        stringHttpMessageConverter.(Constants.CHARSET_UTF8);

        supportedMediaTypes.clear();
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.ALL);
        stringHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);

        httpMessageConverters.add(stringHttpMessageConverter);

        return httpMessageConverters;
    }
}
