package com.alinturbut.olxCrawl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * Created by alint on 29.01.2017.
 */
@ConfigurationProperties("urls")
@PropertySource("classpath:/urls.yml")
public class UrlsProperties {
    List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
