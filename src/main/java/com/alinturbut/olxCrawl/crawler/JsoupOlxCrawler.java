package com.alinturbut.olxCrawl.crawler;

import com.alinturbut.olxCrawl.config.UrlsProperties;
import com.alinturbut.olxCrawl.domain.SiteStatus;
import com.alinturbut.olxCrawl.repository.SiteStatusRepository;
import com.alinturbut.olxCrawl.util.NotificationService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JsoupOlxCrawler {
    private Logger log = LoggerFactory.getLogger(JsoupOlxCrawler.class);

    @Autowired
    private SiteStatusRepository siteStatusRepo;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UrlsProperties urlsProperties;

    @Transactional
    public String crawlOlx() {
        String crawlResults = "";
        List<String> urls = urlsProperties.getUrls();
        urlsProperties.getUrls().stream().forEach(x -> log.debug(x));

        for(String url : urls) {
            try {
                Document doc = Jsoup.connect(url).get();
                crawlResults = doc.select(".hasPromoted p").html();
                String firstResults = doc.select("#offers_table tr td.offer:first-child").html();
                String lastCrawlUrl = firstResults.split("<a")[1].split("href")[1].split("\"")[1];

                int newResults = findTotalResults(crawlResults);

                int oldResults = 0;
                String oldCrawlUrl = "";

                Optional<SiteStatus> byRelatedPostUrl = siteStatusRepo.findByRelatedPostUrl(url);
                if (byRelatedPostUrl.isPresent()) {
                    SiteStatus one = byRelatedPostUrl.get();
                    oldResults = one.getNoOfPosts();
                    oldCrawlUrl = one.getLastPostUrl();
                }

                log.debug("Results now: " + newResults);
                log.debug("Results then: " + oldResults);

                if (newResults != oldResults || !oldCrawlUrl.equals(lastCrawlUrl)) {
                    SiteStatus siteStatus = new SiteStatus();
                    siteStatus.setNoOfPosts(newResults);
                    siteStatus.setLastPostUrl(lastCrawlUrl);
                    siteStatus.setRelatedPostUrl(url);

                    if(byRelatedPostUrl.isPresent()) {
                        siteStatusRepo.delete(byRelatedPostUrl.get().getId());
                    }

                    siteStatusRepo.save(siteStatus);

                    notificationService.send(lastCrawlUrl, url);
                }
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        }

        return crawlResults;
    }

    private Integer findTotalResults(String s) {
        int secondValue = 0;

        try {
            secondValue = Integer.valueOf(s.split(" ")[3]);
        } catch(NumberFormatException e) {
            // do nothing
        }

        if(secondValue != 0) {
            return Integer.valueOf(s.split(" ")[2] + s.split(" ")[3]);
        }

        return Integer.valueOf(s.split(" ")[2]);
    }
}
