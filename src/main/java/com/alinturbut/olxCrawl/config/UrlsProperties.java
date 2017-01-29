package com.alinturbut.olxCrawl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alint on 29.01.2017.
 */
@Component
@ConfigurationProperties(value = "olx", locations = {"classpath:urls.yml"})
public class UrlsProperties {
    private List<String> urls = new ArrayList<>();

    private long frequency;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }
}
