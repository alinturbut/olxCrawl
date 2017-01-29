package com.alinturbut.olxCrawl.crawler;

import com.alinturbut.olxCrawl.domain.SiteStatus;
import com.alinturbut.olxCrawl.repository.SiteStatusRepository;
import com.alinturbut.olxCrawl.util.NotificationService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JsoupOlxCrawler {
    @Autowired
    private SiteStatusRepository siteStatusRepo;

    @Autowired
    private NotificationService notificationService;

    private final String AUDI_A5_URL = "http://olx.ro/auto-masini-moto-ambarcatiuni/autoturisme/q-audi-a5/";

    @Transactional
    public String crawlOlx() {
        String crawlResults = "";

        try {
            Document doc = Jsoup.connect(AUDI_A5_URL).get();
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

            System.out.println("Results now: " + newResults);
            System.out.println("Results then: " + oldResults);

            if(newResults != oldResults || !oldCrawlUrl.equals(lastCrawlUrl)) {
                SiteStatus siteStatus = new SiteStatus();
                siteStatus.setId(Long.valueOf(1));
                siteStatus.setNoOfPosts(newResults);
                siteStatus.setLastPostUrl(lastCrawlUrl);
                siteStatusRepo.save(siteStatus);

                notificationService.send(lastCrawlUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return crawlResults;
    }
}
