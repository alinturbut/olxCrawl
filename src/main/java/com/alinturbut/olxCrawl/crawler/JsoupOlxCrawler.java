package com.alinturbut.olxCrawl.crawler;

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

@Service
public class JsoupOlxCrawler {
    private Logger log = LoggerFactory.getLogger(JsoupOlxCrawler.class);

    @Autowired
    private SiteStatusRepository siteStatusRepo;

    @Autowired
    private NotificationService notificationService;

    private final String CRAWL_URL = "http://olx.ro/oferte/q-smartphone/";

    @Transactional
    public String crawlOlx() {
        String crawlResults = "";

        try {
            Document doc = Jsoup.connect(CRAWL_URL).get();
            crawlResults = doc.select(".hasPromoted p").html();
            String firstResults = doc.select("#offers_table tr td.offer:first-child").html();

            String lastCrawlUrl = firstResults.split("<a")[1].split("href")[1].split("\"")[1];

            int newResults = Integer.valueOf(crawlResults.split(" ")[2]);

            int oldResults = 0;
            String oldCrawlUrl = "";

            if(siteStatusRepo.findAll().size() > 0) {
                SiteStatus one = siteStatusRepo.getOne(Long.valueOf(1));
                oldResults = one.getNoOfPosts();
                oldCrawlUrl = one.getLastPostUrl();
            }

            log.debug("Results now: " + newResults);
            log.debug("Results then: " + oldResults);

            if(newResults != oldResults || !oldCrawlUrl.equals(lastCrawlUrl)) {
                SiteStatus siteStatus = new SiteStatus();
                siteStatus.setId(Long.valueOf(1));
                siteStatus.setNoOfPosts(newResults);
                siteStatus.setLastPostUrl(lastCrawlUrl);
                siteStatusRepo.save(siteStatus);

                notificationService.send(lastCrawlUrl);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }

        return crawlResults;
    }
}
